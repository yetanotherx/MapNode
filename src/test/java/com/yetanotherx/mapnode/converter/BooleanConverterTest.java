package com.yetanotherx.mapnode.converter;

import junit.framework.TestCase;

public class BooleanConverterTest extends TestCase {
    
    public BooleanConverterTest(String testName) {
        super(testName);
    }

    public void testTransform() {
        BooleanConverter conv = new BooleanConverter();
        
        assertEquals(true, conv.transform("true").booleanValue());
        assertEquals(false, conv.transform("false").booleanValue());
        assertEquals(false, conv.transform("t").booleanValue());
        assertEquals(false, conv.transform("f").booleanValue());
        assertEquals(false, conv.transform("tue").booleanValue());
        assertEquals(false, conv.transform("").booleanValue());
    }
}
