package com.yetanotherx.mapnode.converter;

import junit.framework.TestCase;

public class DoubleConverterTest extends TestCase {
    
    public DoubleConverterTest(String testName) {
        super(testName);
    }

    public void testTransform() {
        DoubleConverter conv = new DoubleConverter();
        
        assertEquals(0.3D, conv.transform("0.3"));
        assertEquals(1.0D, conv.transform("1"));
        assertEquals(null, conv.transform("text"));
        assertEquals(null, conv.transform(""));
        assertEquals(123.45687587687688D, conv.transform("123.4568758768768756648548654868675875876586758765"));
    }
}
