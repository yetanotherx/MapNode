package com.yetanotherx.mapnode;

import java.util.HashMap;

/**
 * Simple HashMap utility. Initialize with an even number
 * of arguments, and it fills the hashmap!
 * 
 * new EasyHashMap<String, Integer>("foo", 1, "bar", 2)
 * 
 * @author yetanotherx
 */
public class EasyHashMap<T, U> extends HashMap<T, U> {

    private static final long serialVersionUID = 98268764L;

    /**
     * Fill in a hashmap with a list of values.
     * If the number of arguments is not even, then
     * it throws a MapNodeException (runtime).
     * 
     * @throws MapNodeException
     * @param values 
     */
    @SuppressWarnings("unchecked")
    public EasyHashMap(Object... values) {
        if (values.length % 2 != 0) {
            throw new MapNodeException("Value count must be even");
        }
        boolean first = true;
        T saved = null;
        for (Object value : values) {
            if (first) {
                first = false;
                saved = (T) value;
            } else {
                first = true;
                this.put(saved, (U) value);
                saved = null;
            }
        }
    }
}
