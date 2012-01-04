package com.yetanotherx.mapnode.converter;

/**
 * Converts Objects to Booleans.
 * 
 * @author yetanotherx
 */
public class BooleanConverter implements BaseConverter<Boolean> {

    /**
     * Converts an Object into a Boolean. If it cannot
     * be converted, it will return false.
     * 
     * @param oldObject
     * @return 
     */
    public Boolean transform(Object oldObject) {
        if( oldObject == null ) {
            return null;
        }
        
        return Boolean.parseBoolean(oldObject.toString());
    }
    
}
