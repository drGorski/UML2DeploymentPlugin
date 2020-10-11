package pl.gdynia.amw.corda.vp.plugin.node;

import com.vp.plugin.action.VPAction;
import com.vp.plugin.action.VPContext;
import com.vp.plugin.action.VPContextActionController;
import com.vp.plugin.model.INode;
import pl.gdynia.amw.corda.vp.plugin.UIHelper;
import pl.gdynia.amw.corda.vp.plugin.config.PluginConfiguration;
import pl.gdynia.amw.corda.vp.plugin.node.config.DefaultActionController;

import java.awt.event.ActionEvent;
import java.util.Collections;

public class NodeGeneratorContextActionController extends DefaultActionController implements VPContextActionController {
    
    @Override
    public void performAction(VPAction vpAction, VPContext vpContext, ActionEvent actionEvent) {
        UIHelper.logMessage("Starting nodes config generation for Corda platform");
        PluginConfiguration pluginConfiguration = readPluginConfig(vpContext);
        NodeConfigGenerator.getInstance().generateNodesConfiguration(pluginConfiguration.getDestination(), Collections.singleton((INode) vpContext.getModelElement()));
    }

    @Override
    public void update(VPAction vpAction, VPContext vpContext) {

    }
}
