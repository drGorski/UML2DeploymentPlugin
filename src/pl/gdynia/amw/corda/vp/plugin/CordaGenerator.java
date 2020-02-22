package pl.gdynia.amw.corda.vp.plugin;

import com.vp.plugin.VPPlugin;
import com.vp.plugin.VPPluginInfo;
import pl.gdynia.amw.corda.vp.plugin.logging.VPLogger;
import pl.gdynia.amw.logger.Logger;
import pl.gdynia.amw.logger.LoggerFactory;

public class CordaGenerator implements VPPlugin {

    private static Logger VP_LOGGER;

    @Override
    public void loaded(VPPluginInfo vpPluginInfo) {
        VP_LOGGER = new VPLogger();
        LoggerFactory.getInstance().setLogger(VP_LOGGER);

        UIHelper.logMessage("Corda Generator loaded");
    }

    @Override
    public void unloaded() {
        UIHelper.logMessage("Corda Generator unloaded");
    }

    public static Logger getLogger() {
        return VP_LOGGER;
    }
}
