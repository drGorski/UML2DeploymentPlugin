package pl.gdynia.amw.corda.vp.plugin.node;

import com.vp.plugin.ApplicationManager;
import com.vp.plugin.model.INode;
import com.vp.plugin.model.factory.IModelElementFactory;
import pl.gdynia.amw.dictionary.StereotypesEnum;
import pl.gdynia.amw.model.node.Node;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NodeRetriver {

    private static NodeRetriver INSTANCE;

    private NodeRetriver() {

    }

    public Collection<Node> getNodes() {
        Collection<INode> nodes = Stream.of(ApplicationManager.instance()
                .getProjectManager()
                .getProject()
                .toModelElementArray(IModelElementFactory.MODEL_TYPE_NODE))
                .map(model -> (INode) model)
                .collect(Collectors.toList());

        Collection<Node> result = new ArrayList<>();

        Stream.of(StereotypesEnum.values()).forEach(nodeType -> {
            result.addAll(nodes
                    .stream()
                    .filter(node -> node.hasStereotype(nodeType.name()))
                    .map(node -> NodeAssembler.getInstance().buildNode(node, nodeType.getInstance()))
                    .collect(Collectors.toList()));
        });

        return result;
    }

    public static NodeRetriver getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NodeRetriver();
        }

        return INSTANCE;
    }

}
