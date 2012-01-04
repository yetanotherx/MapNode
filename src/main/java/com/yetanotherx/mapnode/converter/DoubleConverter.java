package com.yetanotherx.mapnode.converter;

/**
 * Converts Objects to Doubles.
 * 
 * @author yetanotherx
 */
public class DoubleConverter implements BaseConverter<Double> {

    /**
     * Converts an Object into a Double. If it cannot
     * be converted, it returns null;
     * 
     * @param oldObject
     * @return 
     */
    public Double transform(Object oldObject) {
        if( oldObject == null ) {
            return null;
        }
        
        try {
            return Double.parseDouble(oldObject.toString());
        } catch( NumberFormatException e ) {
            return null;
        }
    }
    
}
