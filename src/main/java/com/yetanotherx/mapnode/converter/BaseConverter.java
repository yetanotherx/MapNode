package com.yetanotherx.mapnode.converter;

/**
 * Interface for all converter instances.
 * 
 * @author yetanotherx
 * @param <T> 
 */
public interface BaseConverter<T> {

    /**
     * Converts an Object into an instance of <T>.
     * 
     * @param oldObject
     * @return 
     */
    public T transform(Object oldObject);
}
