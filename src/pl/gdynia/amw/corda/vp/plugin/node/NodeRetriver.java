package pl.gdynia.amw.corda.vp.plugin.node;

import com.vp.plugin.ApplicationManager;
import com.vp.plugin.model.INode;
import com.vp.plugin.model.factory.IModelElementFactory;
import pl.gdynia.amw.corda.vp.plugin.UIHelper;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NodeRetriver {

    private static NodeRetriver INSTANCE;

    private NodeRetriver() {

    }

    public Collection<INode> getNodes() {
        UIHelper.logMessage("Collecting nodes details...");
        Collection<INode> nodes = Stream.of(ApplicationManager.instance()
                .getProjectManager()
                .getProject()
                .toModelElementArray(IModelElementFactory.MODEL_TYPE_NODE))
                .map(model -> (INode) model)
                .collect(Collectors.toList());

        UIHelper.logMessage("Nodes search finished (" + nodes.size() + " nodes found)");

        return nodes;
    }

    public static NodeRetriver getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NodeRetriver();
        }

        return INSTANCE;
    }

}
