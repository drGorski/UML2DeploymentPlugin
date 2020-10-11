package pl.gdynia.amw.corda.vp.plugin.config;

import com.vp.plugin.ApplicationManager;
import pl.gdynia.amw.corda.vp.plugin.UIHelper;
import pl.gdynia.amw.model.Destination;
import pl.gdynia.amw.storage.GitDestination;
import pl.gdynia.amw.storage.LocalfileDestination;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PluginConfiguration {

    private static final String CONFIG_FILE_NAME = File.separator + "plugin-config.properties";

    private Workmode workmode;

    private String gitUrl;
    private String gitPath;
    private String gitBranch;
    private String gitLogin;
    private String gitPassword;

    public PluginConfiguration(String environment) {
        String projectPath = ApplicationManager.instance().getProjectManager().getProject().getProjectFile().getAbsolutePath();
        String configFilePath = projectPath.substring(0, projectPath.lastIndexOf(File.separator)) + CONFIG_FILE_NAME;

        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(new File(configFilePath))) {
            properties.load(fis);
        } catch (IOException e) {
            UIHelper.logMessage(e.getMessage());
        }

        workmode = Workmode.valueOf(properties.getProperty(environment+".workmode", Workmode.LOCAL.name()));

        switch (workmode) {
            case GIT:
                gitUrl = properties.getProperty(environment+".url");
                gitBranch = properties.getProperty(environment+".branch");
                gitPath = properties.getProperty(environment+".path");
                gitLogin = properties.getProperty(environment+".login");
                gitPassword = properties.getProperty(environment+".password");
                break;
        }

        UIHelper.logMessage("Configuration prepared for " + workmode);
    }

    public Destination getDestination() {
        switch (workmode) {
            case LOCAL:
                String destination = UIHelper.selectFile(JFileChooser.DIRECTORIES_ONLY, "Select place where Corda configuration will be generated.");
                UIHelper.logMessage("Configuration will be generated in location: " + destination);
                return new LocalfileDestination(destination);
            case GIT:
                return GitDestination.builder()
                        .gitUrl(gitUrl)
                        .gitBranch(gitBranch)
                        .gitPath(gitPath)
                        .gitLogin(gitLogin)
                        .gitPassword(gitPassword)
                        .build();
        }

        return null;
    }


}
