package pl.gdynia.amw.corda.vp.plugin.node;

import com.vp.plugin.action.VPAction;
import com.vp.plugin.action.VPContext;
import com.vp.plugin.action.VPContextActionController;
import com.vp.plugin.model.INode;
import com.vp.plugin.model.IPackage;
import org.apache.commons.lang.StringUtils;
import pl.gdynia.amw.corda.vp.plugin.UIHelper;

import java.awt.event.ActionEvent;
import java.util.Collection;

public class NodesGeneratorContextActionController implements VPContextActionController {

    @Override
    public void performAction(VPAction vpAction, VPContext vpContext, ActionEvent actionEvent) {
        UIHelper.logMessage("Starting nodes config generation for Corda platform");
        String destination = UIHelper.selectDirectory();
        if (StringUtils.isNotBlank(destination)) {
            UIHelper.logMessage("Configuration will be generated in location: " + destination);
            Collection<INode> nodes = NodeRetriver.getInstance().getNodes((IPackage) vpContext.getModelElement());
            NodeConfigGenerator.getInstance().generateNodesConfiguration(destination, nodes);
        }
    }

    @Override
    public void update(VPAction vpAction, VPContext vpContext) {

    }
}
