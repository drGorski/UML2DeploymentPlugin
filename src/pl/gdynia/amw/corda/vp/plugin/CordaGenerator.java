package pl.gdynia.amw.corda.vp.plugin;

import com.vp.plugin.VPPlugin;
import com.vp.plugin.VPPluginInfo;

public class CordaGenerator implements VPPlugin {

    @Override
    public void loaded(VPPluginInfo vpPluginInfo) {
        UIHelper.logMessage("Corda Generator loaded");
    }

    @Override
    public void unloaded() {
        UIHelper.logMessage("Corda Generator unloaded");
    }
}
