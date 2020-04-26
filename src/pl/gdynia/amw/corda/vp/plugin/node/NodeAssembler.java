package pl.gdynia.amw.corda.vp.plugin.node;

import com.vp.plugin.ApplicationManager;
import com.vp.plugin.ProjectManager;
import com.vp.plugin.model.IModelElement;
import com.vp.plugin.model.INode;
import com.vp.plugin.model.IStereotype;
import com.vp.plugin.model.ITaggedValue;
import com.vp.plugin.model.factory.IModelElementFactory;
import pl.gdynia.amw.model.node.CordaNode;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class NodeAssembler {

    private static final String UML_PROFILE_NAME = "UML Profile for Distributed Ledger Deployment";

    private static NodeAssembler INSTANCE = new NodeAssembler();

    private NodeAssembler() {

    }

    public CordaNode buildNode(INode vpNode, CordaNode cordaNode) {
        ProjectManager projectManager = ApplicationManager.instance().getProjectManager();
        Set<String> taggedValues = new HashSet<>();

        IModelElement[] stereotypes = Stream.of(projectManager.getProject().getLinkedProjects()).filter(lp -> lp.getName().equals(UML_PROFILE_NAME)).findFirst().get().toModelElementArray(IModelElementFactory.MODEL_TYPE_STEREOTYPE);
        Stream.of(stereotypes)
                .map(modelElement -> (IStereotype) modelElement)
                .map(stereotype -> stereotype.getTaggedValueDefinitions())
                .map(taggedValueDefinitionContainer -> taggedValueDefinitionContainer.toTaggedValueDefinitionArray())
                .forEach(taggedValuesDefinitions -> Stream.of(taggedValuesDefinitions).forEach(taggedValue -> taggedValues.add(taggedValue.getName())));

        taggedValues.forEach(tv -> cordaNode.getProperties().put(tv, readTaggedValue(tv, vpNode)));

        cordaNode.setName(vpNode.getName());

        return cordaNode;
    }

    private Object readTaggedValue(String key, INode vpNode) {
        ITaggedValue vpTaggedValue = vpNode.getTaggedValues().getTaggedValueByName(key);
        if (vpTaggedValue == null) {
            return null;
        }

        return vpTaggedValue.getValue();
    }

    public static NodeAssembler getInstance() {
        return INSTANCE;
    }
}
