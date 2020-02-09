package pl.gdynia.amw.corda.vp.plugin.node;

import com.vp.plugin.model.INode;
import com.vp.plugin.model.ITaggedValue;
import org.apache.commons.lang.StringUtils;
import pl.gdynia.amw.dictionary.TaggedValuesDict;
import pl.gdynia.amw.model.TaggedValues;


public class NodeAssembler {

    public static TaggedValues buildNode(INode vpNode) {
        TaggedValues node = new TaggedValues();

        TaggedValuesDict.getInstance().getValues().forEach(tv -> {
            ITaggedValue vpTaggedValue = vpNode.getTaggedValues().getTaggedValueByName(tv.getValue());
            if (vpTaggedValue != null && StringUtils.isNotBlank(vpTaggedValue.getValueAsText())) {
                node.getProperties().put(tv.getValue(), vpTaggedValue.getValueAsText());
            }
        });

        return node;
    }
}
