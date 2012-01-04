package com.yetanotherx.mapnode;

/**
 * Runtime MapNode exception class.
 * 
 * @author yetanotherx
 */
public class MapNodeException extends RuntimeException {
    private static final long serialVersionUID = 123456678987654L;

    public MapNodeException(Throwable thrwbl) {
        super(thrwbl);
    }

    public MapNodeException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }

    public MapNodeException(String string) {
        super(string);
    }

    public MapNodeException() {
    }
    
}
