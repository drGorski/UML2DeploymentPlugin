package pl.gdynia.amw.corda.vp.plugin.node;

import com.vp.plugin.ApplicationManager;
import com.vp.plugin.ProjectManager;
import com.vp.plugin.model.IModelElement;
import com.vp.plugin.model.IStereotype;
import com.vp.plugin.model.factory.IModelElementFactory;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class UmlProfileReader {

    private static final String UML_PROFILE_NAME = "UML Profile for Distributed Ledger Deployment";

    private static UmlProfileReader INSTANCE;

    private UmlProfileReader() {

    }

    public Set<String> taggedValuesDefinedInProfile() {
        ProjectManager projectManager = ApplicationManager.instance().getProjectManager();
        Set<String> taggedValues = new HashSet<>();
        IModelElement[] stereotypes = Stream.of(projectManager
                .getProject()
                .getLinkedProjects())
                .filter(lp -> lp.getName().equals(UML_PROFILE_NAME))
                .findFirst()
                .get()
                .toModelElementArray(IModelElementFactory.MODEL_TYPE_STEREOTYPE);

        Stream.of(stereotypes)
                .map(modelElement -> (IStereotype) modelElement)
                .filter(stereotype -> stereotype.getTaggedValueDefinitions() != null)
                .map(stereotype -> stereotype.getTaggedValueDefinitions())
                .map(taggedValueDefinitionContainer -> taggedValueDefinitionContainer.toTaggedValueDefinitionArray())
                .forEach(taggedValuesDefinitions -> Stream.of(taggedValuesDefinitions)
                        .forEach(taggedValue -> taggedValues.add(taggedValue.getName())));

        return taggedValues;
    }

    public static UmlProfileReader getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UmlProfileReader();
        }

        return INSTANCE;
    }

}
