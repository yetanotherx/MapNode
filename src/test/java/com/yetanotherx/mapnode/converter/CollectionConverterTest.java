package com.yetanotherx.mapnode.converter;

import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;

public class CollectionConverterTest extends TestCase {

    public CollectionConverterTest(String testName) {
        super(testName);
    }

    public void testTransform() {
        CollectionConverter<Double> cc = new CollectionConverter<Double>();

        List<Object> oldMap = new ArrayList<Object>();
        oldMap.add("0.3");
        oldMap.add("1");
        oldMap.add("text");
        oldMap.add("");
        oldMap.add("123.4568758768768756648548654868675875876586758765");
        oldMap.add(null);

        List<Double> newMap = new ArrayList<Double>();
        newMap.add(0.3D);
        newMap.add(1.0D);
        newMap.add(null);
        newMap.add(null);
        newMap.add(123.45687587687688D);
        newMap.add(null);

        List<Double> newMapTemp = new ArrayList<Double>();
        cc.transform(new DoubleConverter(), oldMap, newMapTemp);
        assertEquals(newMap, newMapTemp);
    }
}
