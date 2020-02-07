package pl.gdynia.amw.corda.vp.plugin;

import com.vp.plugin.VPPlugin;
import com.vp.plugin.VPPluginInfo;

public class CordaGenerator implements VPPlugin {

    @Override
    public void loaded(VPPluginInfo vpPluginInfo) {
        System.out.println("Corda Generator loaded");
    }

    @Override
    public void unloaded() {
        System.out.println("Corda Generator unloaded");
    }
}
