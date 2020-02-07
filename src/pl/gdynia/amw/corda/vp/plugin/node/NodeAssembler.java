package pl.gdynia.amw.corda.vp.plugin.node;

import com.vp.plugin.model.INode;
import com.vp.plugin.model.ITaggedValue;
import pl.gdynia.amw.dictionary.TaggedValues;
import pl.gdynia.amw.model.Node;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NodeAssembler {

    public static Node buildNode(INode vpNode) {
        Map<String, ITaggedValue> nodeTaggedValues = Stream.of(vpNode
                .getTaggedValues()
                .toTaggedValueArray())
                .collect(Collectors.toMap(ITaggedValue::getName, Function.identity()));

        Node node = new Node();

        Stream.of(node.getClass().getMethods())
                .filter(method -> method.getName().startsWith("set"))
                .forEach(method -> {
                    String tvName = method.getName().substring(3);
                    tvName = tvName.replaceFirst(String.valueOf(tvName.charAt(0)), String.valueOf(tvName.charAt(0)).toLowerCase());
                    TaggedValues taggedValue = TaggedValues.valueOf(tvName);
                    if (taggedValue != null) {
                        try {
                            ITaggedValue tv = nodeTaggedValues.get(taggedValue.getTaggedValueKey());
                            if (tv != null) {
                                String value = Optional.ofNullable(tv.getValueAsText()).orElse("");
                                method.invoke(node, value);
                            }
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                });

        return node;
    }
}
