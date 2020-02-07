package pl.gdynia.amw.corda.vp.plugin.node;

import pl.gdynia.amw.model.Node;
import pl.gdynia.amw.service.Generator;

import java.util.stream.Collectors;

public class NodeConfigGenerator {

    private static NodeConfigGenerator INSTANCE;

    private NodeConfigGenerator() {

    }

    public void generateNodesConfiguration() {
        Generator.getInstance().generate("/Users/jakub.bednarski/Desktop/uml2Corda/upload",
                NodeRetriver.getInstance().getNodes());

    }

    public static NodeConfigGenerator getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NodeConfigGenerator();
        }

        return INSTANCE;
    }
}
