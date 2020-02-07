package pl.gdynia.amw.corda.vp.plugin.node;

import com.vp.plugin.ApplicationManager;
import com.vp.plugin.ProjectManager;
import com.vp.plugin.model.IModelElement;
import com.vp.plugin.model.INode;
import com.vp.plugin.model.factory.IModelElementFactory;
import pl.gdynia.amw.dictionary.Stereotypes;
import pl.gdynia.amw.model.CordaObject;
import pl.gdynia.amw.model.Node;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NodeRetriver {

    private static NodeRetriver INSTANCE;

    private NodeRetriver() {

    }

    public Collection<CordaObject> getNodes() {
        return Stream.of(
                ApplicationManager.instance()
                .getProjectManager()
                .getProject()
                .toModelElementArray(IModelElementFactory.MODEL_TYPE_NODE))
                .map(model -> (INode) model)
                .filter(node -> node.hasStereotype(Stereotypes.CordaNode.name()) ||
                        node.hasStereotype(Stereotypes.DLTNode.name()) ||
                        node.hasStereotype(Stereotypes.NetworkMapNode.name()) ||
                        node.hasStereotype(Stereotypes.NotaryNode.name()) ||
                        node.hasStereotype(Stereotypes.OracleNode.name()))
                .map(node -> NodeAssembler.buildNode(node))
                .collect(Collectors.toList());

    }

    public static NodeRetriver getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NodeRetriver();
        }

        return INSTANCE;
    }

}
