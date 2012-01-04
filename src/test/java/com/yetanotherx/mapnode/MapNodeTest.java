package com.yetanotherx.mapnode;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import junit.framework.TestCase;
import org.yaml.snakeyaml.Yaml;

public class MapNodeTest extends TestCase {

    protected MapNode baseNode;

    public MapNodeTest(String testName) {
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
            baseNode.setOptions(new MapNodeOptions().setReturnEmpty(false));
        }
        input.close();
    }

    public void testGetObjectFromPath() {
        assertEquals("foo", baseNode.getObject("core"));
        assertEquals("value", baseNode.getObject("list.key"));
        assertEquals("{key=value}", baseNode.getObject("list").toString());
        assertEquals("[foo, bar]", baseNode.getObject("children").toString());
        assertEquals("[{foo=bar, baz=bat}, {bar=foo, bat=baz}]", baseNode.getObject("keyldren").toString());
        assertEquals("{foo=bar, baz=bat}", baseNode.getObject("keyldren.0").toString());
        assertEquals(null, baseNode.getObject(null));
        assertEquals(baseNode.getBase().toString(), baseNode.getObject("").toString());
    }

    public void testSetObjectWithPath() throws CloneNotSupportedException {
        MapNode newNode = (MapNode) this.baseNode.clone();

        newNode.setObject("core", "foo2");
        assertEquals("foo2", newNode.getObject("core"));

        newNode.setObject("list.key", "val2");
        assertEquals("val2", newNode.getObject("list.key"));
        assertEquals("{key=val2}", newNode.getObject("list").toString());

        newNode.setObject("list", new EasyHashMap<String, Object>("kiy", "vil"));
        assertEquals("vil", newNode.getObject("list.kiy"));
        assertEquals("{kiy=vil}", newNode.getObject("list").toString());

        newNode.setObject("children", new EasyArrayList<Object>("fio", "bir"));
        assertEquals("[fio, bir]", newNode.getObject("children").toString());

        newNode.setObject("keyldren",
                new EasyArrayList<Object>(
                new EasyHashMap<String, Object>("fiz", "bur", "biz", "bit"),
                new EasyHashMap<String, Object>("bir", "fio", "bit", "biz")));
        assertEquals("[{fiz=bur, biz=bit}, {bit=biz, bir=fio}]", newNode.getObject("keyldren").toString());

        newNode.setObject("keyldren.0", new EasyHashMap<String, Object>("fio", "bir", "biz", "bit"));
        //assertEquals("{fio=bir, biz=bit}", newNode.getObject("keyldren.0").toString());
        //TODO: Setting list values doesn't work yet.

        newNode.setObject("", new EasyHashMap<String, Object>("fiz", "faz"));
        newNode.setObject(null, new EasyHashMap<String, Object>("fiz", "faz"));
        assertEquals(newNode.getBase().toString(), newNode.getObject("").toString());

    }

    public void testRemoveObjectWithPath() throws CloneNotSupportedException {
        MapNode newNode = (MapNode) this.baseNode.clone();

        newNode.removeObject("core");
        assertEquals(null, newNode.getObject("core"));

        newNode.removeObject("list.key");
        assertEquals(null, newNode.getObject("list.key"));
        assertEquals(new LinkedHashMap<String, Object>(), newNode.getObject("list"));

        //TODO: removeObject doesn't work on lists
        newNode.removeObject("children");
        assertEquals(null, newNode.getObject("children"));

        newNode.removeObject(null);
        assertNotSame(null, newNode.getObject("keyldren"));

        newNode.removeObject("");
        assertEquals(new HashMap<String, Object>(), newNode.getBase());
    }

    public void testGetString() {
        assertNull(this.baseNode.getString("notExist"));
        assertEquals("foo", this.baseNode.getString("core"));
        assertEquals("4", this.baseNode.getString("inty"));
        assertEquals("4.5", this.baseNode.getString("doubley"));
        assertEquals(this.baseNode.base.toString(), this.baseNode.getString(""));
        assertEquals(null, this.baseNode.getString(null));
    }

    public void testGetInteger() {
        assertNull(this.baseNode.getInteger("notExist"));
        assertEquals(null, this.baseNode.getInteger("core"));
        assertEquals(4, this.baseNode.getInteger("inty").intValue());
        assertEquals(4, this.baseNode.getInteger("doubley").intValue());
        assertEquals(null, this.baseNode.getInteger(""));
        assertEquals(null, this.baseNode.getInteger(null));
    }

    public void testGetDouble() {
        assertNull(this.baseNode.getDouble("notExist"));
        assertEquals(null, this.baseNode.getDouble("core"));
        assertEquals(4.0D, this.baseNode.getDouble("inty").doubleValue());
        assertEquals(4.5D, this.baseNode.getDouble("doubley").doubleValue());
        assertEquals(null, this.baseNode.getDouble(""));
        assertEquals(null, this.baseNode.getDouble(null));
    }

    public void testGetBoolean() {
        assertEquals(null, this.baseNode.getBoolean("notExist"));
        assertEquals(false, this.baseNode.getBoolean("core").booleanValue());
        assertEquals(false, this.baseNode.getBoolean("inty").booleanValue());
        assertEquals(false, this.baseNode.getBoolean("doubley").booleanValue());
        assertEquals(true, this.baseNode.getBoolean("booley").booleanValue());
        assertEquals(false, this.baseNode.getBoolean("").booleanValue());
        assertEquals(null, this.baseNode.getBoolean(null));
    }

    public void testGetMapNode() {
        assertEquals(null, this.baseNode.getMapNode("notExist"));
        assertEquals(new MapNode(new EasyHashMap<String, Object>("key", "value")).toString(), this.baseNode.getMapNode("list").toString());
        assertEquals(null, this.baseNode.getMapNode("core"));
        assertEquals(this.baseNode, this.baseNode.getMapNode(""));
        assertEquals(null, this.baseNode.getMapNode(null));
    }

    public void testGetList() {
        assertEquals(null, this.baseNode.getList("notExist"));
        assertEquals(new EasyArrayList<String>("foo", "bar"), this.baseNode.getList("children"));
        assertEquals(null, this.baseNode.getList("core"));
        assertEquals(null, this.baseNode.getList(""));
        assertEquals(null, this.baseNode.getList(null));
    }

    public void testGetIntegerList() {
        assertEquals(null, this.baseNode.getIntegerList("notExist"));
        assertEquals(new EasyArrayList<Integer>(1, 3), this.baseNode.getIntegerList("intList"));
        assertEquals(null, this.baseNode.getIntegerList("core"));
        assertEquals(null, this.baseNode.getIntegerList(""));
        assertEquals(null, this.baseNode.getIntegerList(null));
    }

    public void testGetDoubleList() {
        assertEquals(null, this.baseNode.getDoubleList("notExist"));
        assertEquals(new EasyArrayList<Double>(1.3D, 3.5D), this.baseNode.getDoubleList("doubleList"));
        assertEquals(null, this.baseNode.getDoubleList("core"));
        assertEquals(null, this.baseNode.getDoubleList(""));
        assertEquals(null, this.baseNode.getDoubleList(null));
    }

    public void testGetBooleanList() {
        assertEquals(null, this.baseNode.getBooleanList("notExist"));
        assertEquals(new EasyArrayList<Boolean>(false, true), this.baseNode.getBooleanList("boolList"));
        assertEquals(null, this.baseNode.getBooleanList("core"));
        assertEquals(null, this.baseNode.getBooleanList(""));
        assertEquals(null, this.baseNode.getBooleanList(null));
    }

    public void testGetStringList() {
        assertEquals(null, this.baseNode.getStringList("notExist"));
        assertEquals(new EasyArrayList<String>("foo", "bar"), this.baseNode.getStringList("children"));
        assertEquals(null, this.baseNode.getStringList("core"));
        assertEquals(null, this.baseNode.getStringList(""));
        assertEquals(null, this.baseNode.getStringList(null));
    }

    public void testGetMapNodeList() {
        assertEquals(null, this.baseNode.getMapNodeList("notExist"));
        assertEquals(new EasyArrayList<MapNode>(new MapNode(new EasyHashMap<String, Object>("key", "value"))), this.baseNode.getMapNodeList("mapNodeList"));
        assertEquals(null, this.baseNode.getMapNodeList("core"));
        assertEquals(null, this.baseNode.getMapNodeList(""));
        assertEquals(null, this.baseNode.getMapNodeList(null));
    }

    public void testDebug() throws IOException {
        InputStream input = MapNode.class.getResourceAsStream("/testYaml.yml");

        BufferedInputStream bis = new BufferedInputStream(input);
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        int result = bis.read();
        while (result != -1) {
            byte b = (byte) result;
            buf.write(b);
            result = bis.read();
        }
        String out = buf.toString();

        assertEquals(baseNode.toString(), out.trim());
    }
    
    public void testClone() throws CloneNotSupportedException {
        MapNode newNode = (MapNode) baseNode.clone();
        
        assertNotSame(newNode, baseNode);
        assertEquals(newNode.base, baseNode.base);
        assertEquals(newNode.options, baseNode.options);
        assertFalse(newNode.base == baseNode.base);
        assertFalse(newNode.options == baseNode.options);
    }
    
    public void testEquals() {
        MapNode map1 = new MapNode(baseNode.base);
        MapNode map2 = new MapNode(baseNode.base);
        
        assertTrue(map1.equals(map2));
        
        map2.setOptions(map2.options.setReturnEmpty(false));
        assertFalse(map1.equals(map2));
    }
    
    public void testHashCode() {
        assertEquals(-1649389256, baseNode.hashCode());
        assertEquals(4588040, new MapNode().hashCode());
    }
}
