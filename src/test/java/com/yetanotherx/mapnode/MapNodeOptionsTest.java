package com.yetanotherx.mapnode;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import junit.framework.TestCase;
import org.yaml.snakeyaml.Yaml;

public class MapNodeOptionsTest extends TestCase {

    protected MapNode baseNode;

    public MapNodeOptionsTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        loadYaml();
    }

    @SuppressWarnings("unchecked")
    protected void loadYaml() throws IOException {
        InputStream input = MapNode.class.getResourceAsStream("/testYaml.yml");
        if (input != null) {
            Yaml yaml = new Yaml();
            Object result = yaml.load(input);

            try {
                baseNode = new MapNode((Map<String, Object>) result);
            } catch (Exception e) {
                baseNode = new MapNode();
            }
        }
        input.close();
    }

    public void testShouldReturnNull() {
        baseNode.setOptions(new MapNodeOptions().setReturnNull(true));
        String result = baseNode.getString("thisShouldNeverExist");
        assertNull(result);

        baseNode.setOptions(new MapNodeOptions().setReturnNull(false));
        result = baseNode.getString("thisShouldNeverExist");
        assertEquals(result, "");
    }

    public void testShouldReturnEmpty() {
        baseNode.setOptions(new MapNodeOptions().setReturnEmpty(false));
        List<String> result = baseNode.getStringList("thisShouldNeverExist");
        assertNull(result);

        baseNode.setOptions(new MapNodeOptions().setReturnEmpty(true));
        result = baseNode.getStringList("thisShouldNeverExist");
        assertEquals(result, new ArrayList<String>());
    }

    public void testGetSeparatorChar() {
        baseNode.setOptions(new MapNodeOptions().setSeparatorChar("."));
        String result = baseNode.getString("list.key");
        assertEquals(result, "value");

        baseNode.setOptions(new MapNodeOptions().setSeparatorChar("*"));
        result = baseNode.getString("list*key");
        assertEquals(result, "value");
    }
}
