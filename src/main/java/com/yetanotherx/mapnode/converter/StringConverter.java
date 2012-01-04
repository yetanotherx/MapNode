package com.yetanotherx.mapnode.converter;

/**
 * Converts Objects to Strings.
 * 
 * @author yetanotherx
 */
public class StringConverter implements BaseConverter<String> {

    /**
     * Gets the String value of the object. If the object
     * is null, it will return null. 
     * 
     * @param oldObject
     * @return 
     */
    public String transform(Object oldObject) {
        if( oldObject == null ) {
            return null;
        }
        
        return oldObject.toString();
    }
    
}
