package pl.gdynia.amw.corda.vp.plugin.node;

import com.vp.plugin.model.INode;
import com.vp.plugin.model.ITaggedValue;
import pl.gdynia.amw.model.node.CordaNode;

import java.util.Set;

public class NodeAssembler {


    private static NodeAssembler INSTANCE = new NodeAssembler();

    private NodeAssembler() {

    }

    public CordaNode buildNode(INode vpNode, CordaNode cordaNode) {
        Set<String> taggedValues = UmlProfileReader.getInstance().taggedValuesDefinedInProfile();
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
