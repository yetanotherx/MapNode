package com.yetanotherx.mapnode.converter;

import com.yetanotherx.mapnode.MapNode;
import java.util.HashMap;
import java.util.Map;
import junit.framework.TestCase;

public class StringConverterTest extends TestCase {
    
    public StringConverterTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testTransform() {
        StringConverter conv = new StringConverter();
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("key", "value");
        
        assertEquals("0.3", conv.transform("0.3"));
        assertEquals("1", conv.transform("1"));
        assertEquals("text", conv.transform("text"));
        assertEquals("", conv.transform(""));
        assertEquals(null, conv.transform(null));
        assertEquals("key: value", conv.transform(new MapNode(map)));
        
    }
}
