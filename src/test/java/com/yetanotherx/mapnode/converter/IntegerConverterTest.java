package com.yetanotherx.mapnode.converter;

import junit.framework.TestCase;

public class IntegerConverterTest extends TestCase {
    
    public IntegerConverterTest(String testName) {
        super(testName);
    }

    public void testTransform() {
        IntegerConverter conv = new IntegerConverter();
        
        assertEquals(0, conv.transform("0.3").intValue());
        assertEquals(1, conv.transform("1").intValue());
        assertEquals(null, conv.transform("text"));
        assertEquals(null, conv.transform(""));
    }
}
