package pl.gdynia.amw.corda.vp.plugin.node;

import com.vp.plugin.ApplicationManager;
import com.vp.plugin.model.INode;
import pl.gdynia.amw.dictionary.StereotypesEnum;
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

        ApplicationManager.instance().getViewManager().showMessage("Generating config files...");
        CordaGenerator.getInstance().generate(destination, nodes);
    }

    public void generateNodesConfiguration(String destination) {
        Collection<INode> nodes = NodeRetriver.getInstance().getNodes();
        generateNodesConfiguration(destination, nodes);
    }

    public static NodeConfigGenerator getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NodeConfigGenerator();
        }

        return INSTANCE;
    }
}
