package com.yetanotherx.mapnode;

import java.util.HashMap;
import junit.framework.TestCase;

public class EasyHashMapTest extends TestCase {
    
    public EasyHashMapTest(String testName) {
        super(testName);
    }

    public void testHashMap() {
        assertEquals(new HashMap<String, String>(), new EasyHashMap<String, String>());
        
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("foo", "bar");
        map.put("fix", "baz");
        assertEquals(map, new EasyHashMap<String, String>("foo", "bar", "fix", "baz"));
        
        try {
            HashMap<String, String> mapy = new EasyHashMap<String, String>("foo", "baz", "fix");
            fail("Exception not thrown.");
        } catch( Exception e ) {
            assertTrue(true);
        }
    }
}
