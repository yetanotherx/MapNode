package com.yetanotherx.mapnode;

import java.util.ArrayList;
import junit.framework.TestCase;

public class EasyArrayListTest extends TestCase {

    public EasyArrayListTest(String testName) {
        super(testName);
    }

    public void testArrayList() {
        assertEquals(new ArrayList<String>(), new EasyArrayList<String>());

        ArrayList<String> map = new ArrayList<String>();
        map.add("foo");
        map.add("bar");
        map.add("fix");
        map.add("baz");
        assertEquals(map, new EasyArrayList<String>("foo", "bar", "fix", "baz"));

    }
}
