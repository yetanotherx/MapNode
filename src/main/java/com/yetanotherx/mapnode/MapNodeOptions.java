package com.yetanotherx.mapnode;

/**
 * Options container class. This provides getters/setters for
 * various options that configure MapNode. The setters return
 * the options object itself, so one can create a settings object
 * as such: new MapNodeOptions().setReturnNull(false), for example.
 * 
 * @author yetanotherx
 */
public class MapNodeOptions implements Cloneable {

    protected boolean returnNull = true;
    protected boolean returnEmpty = true;
    protected String separatorChar = ".";

    /**
     * Whether or not getString()/getInteger()/etc should
     * return null or an empty string/0/etc when a value
     * is not found.
     */
    public boolean shouldReturnNull() {
        return returnNull;
    }

    /**
     * Sets whether or not getString()/getInteger()/etc should
     * return null or an empty string/0/etc when a value
     * is not found.
     */
    public MapNodeOptions setReturnNull(boolean doReturnNull) {
        this.returnNull = doReturnNull;
        return this;
    }

    /**
     * Whether or not getStringList()/getIntegerList()/etc
     * should return an empty list or null when a value
     * is not found.
     */
    public boolean shouldReturnEmpty() {
        return returnEmpty;
    }

    /**
     * Sets whether or not getStringList()/getIntegerList()/etc
     * should return an empty list or null when a value
     * is not found.
     */
    public MapNodeOptions setReturnEmpty(boolean doReturnEmpty) {
        this.returnEmpty = doReturnEmpty;
        return this;
    }

    /**
     * Separator string for node syntax. Defaults to ., 
     * but can be changed to any 1-character string.
     */
    public String getSeparatorChar() {
        return separatorChar;
    }

    /**
     * Sets the separator string for node syntax. Defaults to ., 
     * but can be changed to any 1-character string. If a string
     * is passed with length > 1, it throws a MapNodeException.
     * 
     * @throws MapNodeException
     */
    public MapNodeOptions setSeparatorChar(String separatorChar) {
        if (separatorChar.length() > 1) {
            throw new MapNodeException("Separator character can only be 1 character long.");
        }
        this.separatorChar = separatorChar;
        return this;
    }

    /**
     * Sets the separator character for node syntax. Defaults to ., 
     * but can be changed to any character.
     */
    public MapNodeOptions setSeparatorChar(Character separatorChar) {
        this.separatorChar = separatorChar.toString();
        return this;
    }

    /**
     * Clones the options. This clone method is guaranteed to work.
     * 
     * @return
     */
    @Override
    public Object clone() {
        return new MapNodeOptions().setReturnEmpty(returnEmpty).setReturnNull(returnNull).setSeparatorChar(separatorChar);
    }

    /**
     * Checks whether or not two option instances are identical.
     * 
     * @param obj
     * @return 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof MapNodeOptions)) {
            return false;
        }

        final MapNodeOptions other = (MapNodeOptions) obj;
        if (this.returnNull != other.returnNull) {
            return false;
        }
        if (this.returnEmpty != other.returnEmpty) {
            return false;
        }
        if ((this.separatorChar == null) ? (other.separatorChar != null) : !this.separatorChar.equals(other.separatorChar)) {
            return false;
        }
        return true;
    }

    /**
     * Gets a unique hashcode for this specific set of options.
     * The hash codes for two different instances with identical
     * options will be the same.
     * 
     * @return 
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (this.returnNull ? 1 : 0);
        hash = 97 * hash + (this.returnEmpty ? 1 : 0);
        hash = 97 * hash + (this.separatorChar != null ? this.separatorChar.hashCode() : 0);
        return hash;
    }
}
