package com.yetanotherx.mapnode;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Simple ArrayList utility. Initialize the arraylist
 * with a varargs set of variables. 
 * 
 * new EasyArrayList<String>("foo", "bar", "baz")
 * 
 * @author yetanotherx
 */
public class EasyArrayList<T> extends ArrayList<T> {
    private static final long serialVersionUID = 5638276586L;

    /**
     * Initializes an ArrayList with the given values.
     * @param values 
     */
    public EasyArrayList(T ... values) {
        this.addAll(Arrays.asList(values));
    }

}
