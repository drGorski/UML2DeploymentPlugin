package pl.gdynia.amw.corda.vp.plugin.report;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import pl.gdynia.amw.check.dto.CheckResult;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicReference;

public class HtmlCheckReportBuilder {

    private String reportDestination;
    private CheckResult checkResult;

    public HtmlCheckReportBuilder(String reportDestination, CheckResult checkResult) {
        this.reportDestination = FileUtils.getFile(reportDestination).getParent();
        this.checkResult = checkResult;
    }

    public void storeReport() throws IOException {
        StringBuilder html = new StringBuilder();
        html.append("<html>" +
                "<body>" +
                "<table border=\"1\">" +
                "   <thead>" +
                "       <tr>" +
                "           <td align=\"center\"><b>Legend</b></td>" +
                "       </tr>" +
                "   </thead>" +
                "   <tbody>" +
                "       <tr><td>The same value in UML model and in config file</td></tr>" +
                "       <tr bgcolor=\"red\"><td>Found in UML model and config file but with different values</td></tr>" +
                "       <tr bgcolor=\"yellow\"><td>Found in config file but no entry in UML model</td></tr>" +
                "       <tr bgcolor=\"orange\"><td>Found in UML model but no entry in config file</td></tr>" +
                "   </tbody>" +
                "</table><br/><br/>"  +
                "<table border=\"1\">" +
                "   <thead>" +
                "       <tr>" +
                "           <td align=\"center\"><b>Number</b></td>" +
                "           <td align=\"center\"><b>Key</b></td>" +
                "           <td align=\"center\"><b>UML value</b></td>" +
                "           <td align=\"center\"><b>Config value</b></td>" +
                "       </tr>" +
                "   </thead>" +
                "   <tbody>");

        AtomicReference<Integer> i = new AtomicReference<>(1);
        checkResult.getResult().forEach(entry -> {
            html.append("<tr");
            html.append(entry.areTheSame() ? ">" : " bgcolor=\"" + entry.getDifferenceType().getColor() + "\">");

            html.append("<td align=\"center\">");
            html.append(i.get());
            html.append("</td>");

            html.append("<td>");
            html.append(entry.getField());
            html.append("</td>");

            html.append("<td>");
            html.append(asHtmlText(entry.getUmlValue()));
            html.append("</td>");

            html.append("<td>");
            html.append(asHtmlText(entry.getConfigValue()));
            html.append("</td>");

            html.append("</tr>");
            i.updateAndGet(v -> v + 1);
        });

        html.append("</tbody></table></body></html>");
        FileUtils.writeStringToFile(new File(reportDestination+"/report.html"), html.toString(), Charset.forName("UTF-8"));
    }

    private String asHtmlText(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }

        return value.replace(" ", "&nbsp;");
    }
}
