package pl.gdynia.amw.corda.vp.plugin.node;

import com.vp.plugin.model.INode;
import pl.gdynia.amw.check.corda.CordaValidator;
import pl.gdynia.amw.check.dto.CheckResult;
import pl.gdynia.amw.corda.vp.plugin.UIHelper;
import pl.gdynia.amw.corda.vp.plugin.report.HtmlCheckReportBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class NodeValidator {

    private static NodeValidator INSTANCE;

    private NodeValidator() {

    }

    public void checkNode(INode iNode, String sourceFile) {
        Map<String, String> umlValues = new HashMap<>();
        Stream.of(iNode.getTaggedValues().toTaggedValueArray()).forEach(tv -> umlValues.put(tv.getName(), tv.getValueAsString()));

        Set<String> taggedValues = UmlProfileReader.getInstance().taggedValuesDefinedInProfile();

        try {
            CheckResult result = CordaValidator.getInstance().validateModel(umlValues, sourceFile, taggedValues);
            new HtmlCheckReportBuilder(sourceFile, result).storeReport();
        } catch (IOException e) {
            UIHelper.showPopupMessage(e.getMessage());
        }

    }

    public static NodeValidator getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NodeValidator();
        }

        return INSTANCE;
    }
}
