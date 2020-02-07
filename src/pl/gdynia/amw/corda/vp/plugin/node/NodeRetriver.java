package pl.gdynia.amw.corda.vp.plugin.node;

import com.vp.plugin.ApplicationManager;
import com.vp.plugin.ProjectManager;
import com.vp.plugin.model.IModelElement;
import com.vp.plugin.model.INode;
import com.vp.plugin.model.factory.IModelElementFactory;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NodeRetriver {

    private static final String NODE_ANNOTATION = "node";

    private static NodeRetriver INSTANCE;

    private NodeRetriver() {

    }

    public Collection<INode> getNodes() {
        ProjectManager projectManager = ApplicationManager.instance().getProjectManager();
        IModelElement[] models = projectManager.getProject().toModelElementArray(IModelElementFactory.MODEL_TYPE_NODE);

        return Stream.of(models)
                .map(model -> (INode) model)
                .filter(node -> node.hasStereotype(NODE_ANNOTATION))
                .collect(Collectors.toSet());
    }

    public static NodeRetriver getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NodeRetriver();
        }

        return INSTANCE;
    }

}
