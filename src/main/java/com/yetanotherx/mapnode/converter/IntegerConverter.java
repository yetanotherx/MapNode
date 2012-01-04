package com.yetanotherx.mapnode.converter;

/**
 * Converts Objects to Integers.
 * 
 * @author yetanotherx
 */
public class IntegerConverter implements BaseConverter<Integer> {

    /**
     * Converts an Object into an Integer. If it
     * cannot be converted to an Integer, it will then
     * try to parse it as a Double. If it cannot be parsed
     * as a Double, then it will return null;
     * 
     * @param oldObject
     * @return 
     */
    public Integer transform(Object oldObject) {
        if (oldObject == null) {
            return null;
        }

        try {
            return Integer.parseInt(oldObject.toString());
        } catch (NumberFormatException e) {
            try {
                return (int) Double.parseDouble(oldObject.toString());
            } catch (NumberFormatException e2) {
                return null;
            }
        }
    }
}
