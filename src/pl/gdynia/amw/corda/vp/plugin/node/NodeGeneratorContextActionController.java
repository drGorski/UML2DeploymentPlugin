package pl.gdynia.amw.corda.vp.plugin.node;

import com.vp.plugin.ApplicationManager;
import com.vp.plugin.action.VPAction;
import com.vp.plugin.action.VPContext;
import com.vp.plugin.action.VPContextActionController;
import com.vp.plugin.model.INode;
import org.apache.commons.lang.StringUtils;
import pl.gdynia.amw.corda.vp.plugin.UIHelper;

import java.awt.event.ActionEvent;
import java.util.Collections;

public class NodeGeneratorContextActionController implements VPContextActionController {

    public NodeGeneratorContextActionController() {
    }

    @Override
    public void performAction(VPAction vpAction, VPContext vpContext, ActionEvent actionEvent) {
        ApplicationManager.instance().getViewManager().showMessage("Starting nodes config generation for Corda platform");
        String destination = UIHelper.getInstance().selectDirectory();
        if (StringUtils.isNotBlank(destination)) {
            ApplicationManager.instance().getViewManager().showMessage("Configuration will be generated in location: " + destination);
            NodeConfigGenerator.getInstance().generateNodesConfiguration(destination, Collections.singleton((INode) vpContext.getModelElement()));
        }
    }

    @Override
    public void update(VPAction vpAction, VPContext vpContext) {

    }
}
