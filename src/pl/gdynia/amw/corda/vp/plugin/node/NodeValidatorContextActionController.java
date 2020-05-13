package pl.gdynia.amw.corda.vp.plugin.node;

import com.vp.plugin.action.VPAction;
import com.vp.plugin.action.VPContext;
import com.vp.plugin.action.VPContextActionController;
import com.vp.plugin.model.INode;
import org.apache.commons.lang.StringUtils;
import pl.gdynia.amw.corda.vp.plugin.UIHelper;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class NodeValidatorContextActionController implements VPContextActionController {
    
    @Override
    public void performAction(VPAction vpAction, VPContext vpContext, ActionEvent actionEvent) {
        UIHelper.logMessage("Starting node validation for Corda platform");
        String source = UIHelper.selectFile(JFileChooser.FILES_ONLY, "Select config file to validate it against UML model.");
        if (StringUtils.isNotBlank(source)) {
            NodeValidator.getInstance().checkNode((INode) vpContext.getModelElement(), source);
        }
    }

    @Override
    public void update(VPAction vpAction, VPContext vpContext) {

    }
}
