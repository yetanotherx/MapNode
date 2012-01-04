package com.yetanotherx.mapnode.converter;

import com.yetanotherx.mapnode.MapNode;
import java.util.Map;

/**
 * Converts Objects to MapNodes.
 * 
 * @author yetanotherx
 */
public class MapNodeConverter implements BaseConverter<MapNode> {

    /**
     * Converts an Object to MapNode. If the object is
     * not an instance of Map, then it will return null.
     * 
     * @param oldObject
     * @return 
     */
    @SuppressWarnings("unchecked")
    public MapNode transform(Object oldObject) {
        if( oldObject == null ) {
            return null;
        }
        
        if( oldObject instanceof Map ) {
            return new MapNode((Map<String, Object>) oldObject);
        }
        else {
            return null;
        }
    }
    
}
