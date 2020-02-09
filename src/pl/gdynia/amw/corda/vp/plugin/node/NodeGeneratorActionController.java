package pl.gdynia.amw.corda.vp.plugin.node;

import com.vp.plugin.action.VPAction;
import com.vp.plugin.action.VPActionController;
import org.apache.commons.lang.StringUtils;
import pl.gdynia.amw.corda.vp.plugin.UIHelper;

public class NodeGeneratorActionController implements VPActionController {

    @Override
    public void performAction(VPAction vpAction) {
        UIHelper.logMessage("Starting nodes config generation for Corda platform");
        String destination = UIHelper.selectDirectory();
        if (StringUtils.isNotBlank(destination)) {
            UIHelper.logMessage("Configuration will be generated in location: " + destination);
            NodeConfigGenerator.getInstance().generateNodesConfiguration(destination);
        }
    }

    @Override
    public void update(VPAction vpAction) {

    }

}
