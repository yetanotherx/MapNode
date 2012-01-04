package com.yetanotherx.mapnode.converter;

import java.util.Collection;

/**
 * Converts a Collection<Object> of values into a Collection<T> instance.
 * 
 * @author yetanotherx
 * @param <T> 
 */
public class CollectionConverter<T> {

    /**
     * Converts each value of the collection using the given converter.
     * 
     * @param converter
     * @param oldColl
     * @param newColl 
     */
    public void transform(BaseConverter<T> converter, Collection<Object> oldColl, Collection<T> newColl) {
        for( Object old : oldColl ) {
            newColl.add(converter.transform(old));
        }
    }
}
