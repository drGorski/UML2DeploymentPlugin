package pl.gdynia.amw.corda.vp.plugin;

import com.vp.plugin.ApplicationManager;
import com.vp.plugin.ViewManager;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class UIHelper {

    private UIHelper() {

    }

    public static void showPopupMessage(String message) {
        ViewManager viewManager = ApplicationManager.instance().getViewManager();
        Component parentFrame = viewManager.getRootFrame();

        viewManager.showMessageDialog(parentFrame, message);
    }

    public static void logMessage(String message) {
        ApplicationManager.instance().getViewManager().showMessage(message);
    }

    public static String selectDirectory() {
        ViewManager viewManager = ApplicationManager.instance().getViewManager();
        Component parentFrame = viewManager.getRootFrame();

        JFileChooser fileChooser = ApplicationManager.instance().getViewManager().createJFileChooser();
        fileChooser.setDialogTitle("Select place where Corda configuration will be generated.");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        File selectedDirectory ;
        if (fileChooser.showSaveDialog(parentFrame) == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getAbsolutePath();
        }

        return null;
    }

}
