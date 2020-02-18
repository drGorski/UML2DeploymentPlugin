package pl.gdynia.amw.corda.vp.plugin.node;

import com.vp.plugin.model.INode;
import com.vp.plugin.model.IPackage;
import pl.gdynia.amw.corda.vp.plugin.UIHelper;
import pl.gdynia.amw.dictionary.StereotypesEnum;
import pl.gdynia.amw.dto.GenerationResult;
import pl.gdynia.amw.model.node.Node;
import pl.gdynia.amw.service.CordaGenerator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NodeConfigGenerator {

    private static NodeConfigGenerator INSTANCE;

    private NodeConfigGenerator() {

    }

    public void generateNodesConfiguration(String destination, Collection<INode> vpNodes) {
        Collection<Node> nodes = new ArrayList<>();

        Stream.of(StereotypesEnum.values()).forEach(nodeType -> {
            nodes.addAll(vpNodes
                    .stream()
                    .filter(node -> node.hasStereotype(nodeType.name()))
                    .map(node -> NodeAssembler.getInstance().buildNode(node, nodeType.getInstance()))
                    .collect(Collectors.toList()));
        });

        UIHelper.logMessage("Generating config files...");
        GenerationResult result = CordaGenerator.getInstance().generate(destination, nodes);
        switch (result.getStatus()) {
            case SUCCESS:
                UIHelper.showPopupMessage("Corda configuration generated successfully.");
                break;
            case ERROR:
                UIHelper.showPopupMessage("Error occured during Corda configuration generation");
                UIHelper.logMessage(result.getMsg());
                break;
        }
    }

    public void generateNodesConfiguration(String destination, IPackage vpPackage) {
        Collection<INode> nodes = NodeRetriver.getInstance().getNodes(vpPackage);
        generateNodesConfiguration(destination, nodes);
    }

    public static NodeConfigGenerator getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NodeConfigGenerator();
        }

        return INSTANCE;
    }
}
