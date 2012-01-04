package com.yetanotherx.mapnode.converter;

import com.yetanotherx.mapnode.MapNode;
import java.util.HashMap;
import java.util.Map;
import junit.framework.TestCase;

public class MapNodeConverterTest extends TestCase {
    
    public MapNodeConverterTest(String testName) {
        super(testName);
    }
    
    public void testTransform() {
        MapNodeConverter conv = new MapNodeConverter();
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("key", "value");
        
        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("d", map);
        
        assertEquals(new MapNode(map), conv.transform(map));
        assertEquals(new MapNode(map2), conv.transform(map2));
        assertEquals("value", conv.transform(map).getString("key"));
    }
}
