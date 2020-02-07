package pl.gdynia.amw.corda.vp.plugin.node;

import com.vp.plugin.action.VPAction;
import com.vp.plugin.action.VPActionController;

public class NodeGeneratorActionController implements VPActionController {

    @Override
    public void performAction(VPAction vpAction) {
        NodeConfigGenerator.getInstance().generateNodesConfiguration();
    }

    @Override
    public void update(VPAction vpAction) {

    }

}
