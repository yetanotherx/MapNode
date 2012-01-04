package com.yetanotherx.mapnode;

import com.yetanotherx.mapnode.converter.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

/**
 * Main MapNode class. This stores a Map<String, Object> in
 * a way that can easily access values without knowing the
 * content of the map. This works well when dealing with JSONObjects,
 * YAML results, and any other application that deals with
 * Maps of Maps. It accepts any type of Map<String, Object>, however,
 * clone() is supported only if the base is a HashMap<String, Object>.
 * 
 * @author yetanotherx
 */
public class MapNode implements Cloneable {

    /**
     * Map base instance.
     */
    protected Map<String, Object> base;
    /**
     * Options to use in this node. Properties
     * set will be used in all sub-nodes.
     */
    protected MapNodeOptions options;

    /**
     * Creates a new MapNode object with an empty HashMap
     * and a default set of options.
     */
    public MapNode() {
        this.base = new HashMap<String, Object>();
        this.options = new MapNodeOptions();
    }

    /**
     * Creates a new MapNode object with the given HashMap
     * and a default set of options.
     * 
     * @param base 
     */
    public MapNode(Map<String, Object> base) {
        this.base = base;
        this.options = new MapNodeOptions();
    }

    /**
     * Creates a new MapNode object with the given HashMap
     * and the given set of options.
     * 
     * @param base
     * @param options 
     */
    public MapNode(Map<String, Object> base, MapNodeOptions options) {
        this.base = base;
        this.options = options;
    }

    /**
     * Returns the actual base Map<String, Object>.
     * @return 
     */
    public Map<String, Object> getBase() {
        return base;
    }

    /**
     * Sets the base Map<String, Object>.
     * @param base 
     */
    public void setBase(Map<String, Object> base) {
        this.base = base;
    }

    /**
     * Returns the options used in this node.
     * 
     * @return 
     */
    public MapNodeOptions getOptions() {
        return options;
    }

    /**
     * Sets the options used in this node.
     * 
     * @param props 
     */
    public void setOptions(MapNodeOptions props) {
        this.options = props;
    }

    /**
     * Gets the raw object with the given path.
     * 
     * To access a sub-map of the base node, simply
     * list the keys of the map, separated by a period
     * (the separator character can be set in the options).
     * 
     * For example, if the base is a Map with the HashMap
     * signature {base={core={test=foo}}}, calling the method
     * as getObject("base.core.test") would return "foo";
     * 
     * If prop == null, it will return null;
     * If prop == "", it will return the base map itself.
     * 
     * @param prop
     * @return 
     */
    @SuppressWarnings("unchecked")
    public Object getObject(String prop) {
        if (prop == null) {
            return null;
        }

        if (prop.contains(options.separatorChar)) {

            String[] split = prop.split(Pattern.quote(options.separatorChar));

            Map<String, Object> newBase = this.base;
            Object out = null;
            for (String splat : split) {
                Object got = newBase.get(splat);

                if (got == null) {
                    return null;
                } else {

                    try {
                        if (got instanceof Map) {
                            newBase = (Map<String, Object>) got;
                            out = newBase;
                        } else if (got instanceof List) {
                            //Makes a map with the key the numerical values

                            Map<String, Object> newMap = new HashMap<String, Object>();
                            Integer i = 0;

                            for (Object obj : (List<Object>) got) {
                                newMap.put(i.toString(), obj);
                                ++i;
                            }
                            newBase = newMap;
                            out = newBase;
                        } else {
                            out = got;
                        }
                    } catch (ClassCastException ex) {
                        out = got;
                    }
                }

            }
            return out;
        } else {
            if (prop.length() == 0) {
                return base;
            }

            return base.get(prop);
        }
    }

    /**
     * Sets the object at the given path to the given value.
     * 
     * To set a value in a sub-map of the base node, simply
     * list the keys of the map, separated by a period
     * (the separator character can be set in the options).
     * 
     * For example, calling setObject("base.test.core", "foo");
     * would result in a Map with the following signature:
     * 
     * {base={test={core=foo}}}
     * 
     * If prop == null, it will not set anything;
     * If prop == "", it will set the base map itself (but
     * only if the value is a Map. Otherwise, it will not
     * set anything).
     * 
     * @param prop
     * @param value
     * @return 
     */
    @SuppressWarnings("unchecked")
    public void setObject(String prop, Object value) {
        if (prop == null) {
            return;
        }

        if (prop.contains(options.separatorChar)) {

            String[] split = prop.split(Pattern.quote(options.separatorChar));

            Map<String, Object> newBase = this.base;

            for (int i = 0; i < split.length; ++i) {
                Object got = newBase.get(split[i]);

                if (i == split.length - 1) {
                    newBase.put(split[i], value);
                    return;
                }

                try {
                    if (got == null) {
                        newBase.put(split[i], new HashMap<String, Object>());
                    } else {
                        if (got instanceof Map) {
                            newBase = (Map<String, Object>) got;
                        } else if (got instanceof List) {
                            //Makes a map with the key the numerical values

                            Map<String, Object> newMap = new HashMap<String, Object>();
                            Integer j = 0;

                            for (Object obj : (List<Object>) got) {
                                newMap.put(j.toString(), obj);
                                ++j;
                            }
                            newBase = newMap;
                        } else {
                            newBase.put(split[i], new HashMap<String, Object>());
                        }
                    }

                } catch (ClassCastException ex) {
                    newBase.put(split[i], new HashMap<String, Object>());
                }

            }
        } else {
            if (prop.length() == 0 && value instanceof Map) {
                base = (Map<String, Object>) value;
            }

            base.put(prop, value);
        }
    }

    /**
     * Removes the object at the given path to the given value.
     * 
     * To remove a value in a sub-map of the base node, simply
     * list the keys of the map, separated by a period
     * (the separator character can be set in the options).
     * 
     * For example, calling removeObject("base.test.core") on a
     * MapNode with the signature {base={test={core=foo}}}
     * would result in a Map with the following signature:
     * 
     * {base={test=null}}
     * 
     * If prop == null, it will not remove anything;
     * If prop == "", it will set the base map to an
     * empty HashMap<String, Object>();
     * 
     * @param prop
     * @return 
     */
    @SuppressWarnings("unchecked")
    public void removeObject(String prop) {
        if (prop == null) {
            return;
        }

        if (prop.contains(options.separatorChar)) {

            String[] split = prop.split(Pattern.quote(options.separatorChar));

            Map<String, Object> newBase = this.base;

            for (int i = 0; i < split.length; ++i) {
                Object got = newBase.get(split[i]);

                if (i == split.length - 1) {
                    newBase.remove(split[i]);
                    return;
                }

                try {
                    newBase = (Map<String, Object>) got;
                } catch (ClassCastException ex) {
                    return;
                }

            }
        } else {
            if (prop.length() == 0) {
                base = new HashMap<String, Object>();
            }

            base.remove(prop);
        }
    }

    /**
     * Gets the String value of the value with the given
     * location (see getProperty for a description of the
     * node syntax). If no property is found, or is equal
     * to null, it will return either null or an empty string
     * depending on the options set. If options.returnNull
     * is true, then it will return null if no value is found. 
     * If it is false, then it will return an empty string.
     * 
     * @param prop
     * @return 
     */
    public String getString(String prop) {
        if (options.returnNull) {
            return getString(prop, null);
        } else {
            return getString(prop, "");
        }
    }

    /**
     * Gets the String value of the value with the given
     * location (see getProperty for a description of the
     * node syntax). If no property is found, or is equal
     * to null, it will return the given default value.
     * 
     * @param prop
     * @param defaultResult
     * @return 
     */
    public String getString(String prop, String defaultResult) {
        Object out = this.getObject(prop);
        if (out == null) {
            return defaultResult;
        } else {
            return out.toString();
        }
    }

    /**
     * Gets the Integer value of the value with the given
     * location (see getProperty for a description of the
     * node syntax). If one of the following conditions is met,
     * 
     * - the value at the location is not found
     * - the value at the location is set to null
     * - the value at the location cannot be converted to an int
     * 
     * then it will return either null or an empty string
     * depending on the options set. If options.returnNull
     * is true, then it will return null if no value is found. 
     * If it is false, then it will return 0.
     * 
     * @param prop
     * @return 
     */
    public Integer getInteger(String prop) {
        if (options.returnNull) {
            return getInteger(prop, null);
        } else {
            return getInteger(prop, 0);
        }
    }

    /**
     * Gets the Integer value of the value with the given
     * location (see getProperty for a description of the
     * node syntax). If one of the following conditions is met,
     * 
     * - the value at the location is not found
     * - the value at the location is set to null
     * - the value at the location cannot be converted to an int
     * 
     * then it will return the given default result.
     * 
     * @param prop
     * @param defaultResult
     * @return 
     */
    public Integer getInteger(String prop, Integer defaultResult) {
        Object out = this.getObject(prop);
        if (out == null) {
            return defaultResult;
        } else {
            return new IntegerConverter().transform(out);
        }
    }

    /**
     * Gets the Double value of the value with the given
     * location (see getProperty for a description of the
     * node syntax). If one of the following conditions is met,
     * 
     * - the value at the location is not found
     * - the value at the location is set to null
     * - the value at the location cannot be converted to a double
     * 
     * then it will return either null or an empty string
     * depending on the options set. If options.returnNull
     * is true, then it will return null if no value is found. 
     * If it is false, then it will return 0.0D.
     * 
     * @param prop
     * @return 
     */
    public Double getDouble(String prop) {
        if (options.returnNull) {
            return getDouble(prop, null);
        } else {
            return getDouble(prop, 0.0);
        }
    }

    /**
     * Gets the Double value of the value with the given
     * location (see getProperty for a description of the
     * node syntax). If one of the following conditions is met,
     * 
     * - the value at the location is not found
     * - the value at the location is set to null
     * - the value at the location cannot be converted to a double
     * 
     * then it will return the given default result.
     * 
     * @param prop
     * @param defaultResult
     * @return 
     */
    public Double getDouble(String prop, Double defaultResult) {
        Object out = this.getObject(prop);
        if (out == null) {
            return defaultResult;
        } else {
            return new DoubleConverter().transform(out);
        }
    }

    /**
     * Gets the Boolean value of the value with the given
     * location (see getProperty for a description of the
     * node syntax). If one of the following conditions is met,
     * 
     * - the value at the location is not found
     * - the value at the location is set to null
     * - the value at the location cannot be converted to a boolean
     * 
     * then it will return either null or an empty string
     * depending on the options set. If options.returnNull
     * is true, then it will return null if no value is found. 
     * If it is false, then it will return false.
     * 
     * @param prop
     * @return 
     */
    public Boolean getBoolean(String prop) {
        if (options.returnNull) {
            return getBoolean(prop, null);
        } else {
            return getBoolean(prop, false);
        }
    }

    /**
     * Gets the Boolean value of the value with the given
     * location (see getProperty for a description of the
     * node syntax). If one of the following conditions is met,
     * 
     * - the value at the location is not found
     * - the value at the location is set to null
     * - the value at the location cannot be converted to a boolean
     * 
     * then it will return the given default result.
     * 
     * @param prop
     * @param defaultResult
     * @return 
     */
    public Boolean getBoolean(String prop, Boolean defaultResult) {
        Object out = this.getObject(prop);
        if (out == null) {
            return defaultResult;
        } else {
            return new BooleanConverter().transform(out);
        }
    }

    /**
     * Gets the MapNode value of the value with the given
     * location (see getProperty for a description of the
     * node syntax). If one of the following conditions is met,
     * 
     * - the value at the location is not found
     * - the value at the location is set to null
     * - the value at the location is not a Map<String, Object>
     * 
     * then it will return either null or an empty string
     * depending on the options set. If options.returnNull
     * is true, then it will return null if no value is found. 
     * If it is false, then it will return a blank MapNode.
     * 
     * @param prop
     * @return 
     */
    public MapNode getMapNode(String prop) {
        if (options.returnNull) {
            return getMapNode(prop, null);
        } else {
            return getMapNode(prop, new MapNode(new HashMap<String, Object>(), options));
        }
    }

    /**
     * Gets the MapNode value of the value with the given
     * location (see getProperty for a description of the
     * node syntax). If one of the following conditions is met,
     * 
     * - the value at the location is not found
     * - the value at the location is set to null
     * - the value at the location is not a Map<String, Object>
     * 
     * then it will return the given default result.
     * 
     * @param prop
     * @param defaultResult
     * @return 
     */
    @SuppressWarnings("unchecked")
    public MapNode getMapNode(String prop, MapNode defaultResult) {
        Object out = this.getObject(prop);
        if (out == null) {
            return defaultResult;
        } else {
            if (out instanceof Map) {
                return new MapNode((Map<String, Object>) out, options);
            } else {
                return null;
            }
        }
    }

    /**
     * Gets a list of values from the given location (see getProperty()
     * for a description of the node syntax). If one of the 
     * following conditions is met,
     * 
     * - the value at the location is not found
     * - the value at the location is set to null
     * - the value at the location is not a list of values
     * 
     * then it will return either null or an empty list
     * depending on the options set. If options.returnEmpty
     * is false, then it will return null if no value is found. 
     * If it is true, then it will return an empty ArrayList.
     * 
     * @param prop
     * @return 
     */
    public List<Object> getList(String prop) {
        if (!options.returnEmpty) {
            return getList(prop, null);
        } else {
            return getList(prop, new ArrayList<Object>());
        }
    }

    /**
     * Gets a list of values from the given location (see getProperty()
     * for a description of the node syntax). If one of the 
     * following conditions is met,
     * 
     * - the value at the location is not found
     * - the value at the location is set to null
     * - the value at the location is not a list of values
     * 
     * then it will return the given default result.
     * 
     * @param prop
     * @param defaultResult
     * @return 
     */
    @SuppressWarnings("unchecked")
    public List<Object> getList(String prop, List<Object> defaultResult) {
        Object out = this.getObject(prop);

        if (out == null) {
            return defaultResult;
        } else {
            if (out instanceof List) {
                return (List<Object>) out;
            } else {
                return defaultResult;
            }
        }
    }

    /**
     * Gets a list of integers from the given location (see getProperty()
     * for a description of the node syntax). If one of the 
     * following conditions is met,
     * 
     * - the value at the location is not found
     * - the value at the location is set to null
     * - the value at the location is not a list of int values
     * 
     * then it will return either null or an empty list
     * depending on the options set. If options.returnEmpty
     * is false, then it will return null if no value is found. 
     * If it is true, then it will return an empty ArrayList.
     * 
     * @param prop
     * @return 
     */
    public List<Integer> getIntegerList(String prop) {
        if (!options.returnEmpty) {
            return getIntegerList(prop, null);
        } else {
            return getIntegerList(prop, new ArrayList<Integer>());
        }
    }

    /**
     * Gets a list of integers from the given location (see getProperty()
     * for a description of the node syntax). If one of the 
     * following conditions is met,
     * 
     * - the value at the location is not found
     * - the value at the location is set to null
     * - the value at the location is not a list of integer values
     * 
     * then it will return the given default result.
     * 
     * @param prop
     * @param defaultResult
     * @return 
     */
    public List<Integer> getIntegerList(String prop, List<Integer> defaultResult) {
        List<Object> out = this.getList(prop, null);

        if (out == null) {
            return defaultResult;
        } else {
            List<Integer> newList = new ArrayList<Integer>();
            new CollectionConverter<Integer>().transform(new IntegerConverter(), out, newList);
            return newList;
        }
    }

    /**
     * Gets a list of doubles from the given location (see getProperty()
     * for a description of the node syntax). If one of the 
     * following conditions is met,
     * 
     * - the value at the location is not found
     * - the value at the location is set to null
     * - the value at the location is not a list of double values
     * 
     * then it will return either null or an empty list
     * depending on the options set. If options.returnEmpty
     * is false, then it will return null if no value is found. 
     * If it is true, then it will return an empty ArrayList.
     * 
     * @param prop
     * @return 
     */
    public List<Double> getDoubleList(String prop) {
        if (!options.returnEmpty) {
            return getDoubleList(prop, null);
        } else {
            return getDoubleList(prop, new ArrayList<Double>());
        }
    }

    /**
     * Gets a list of doubles from the given location (see getProperty()
     * for a description of the node syntax). If one of the 
     * following conditions is met,
     * 
     * - the value at the location is not found
     * - the value at the location is set to null
     * - the value at the location is not a list of double values
     * 
     * then it will return the given default result.
     * 
     * @param prop
     * @param defaultResult
     * @return 
     */
    public List<Double> getDoubleList(String prop, List<Double> defaultResult) {
        List<Object> out = this.getList(prop, null);

        if (out == null) {
            return defaultResult;
        } else {
            List<Double> newList = new ArrayList<Double>();
            new CollectionConverter<Double>().transform(new DoubleConverter(), out, newList);
            return newList;
        }
    }

    /**
     * Gets a list of booleans from the given location (see getProperty()
     * for a description of the node syntax). If one of the 
     * following conditions is met,
     * 
     * - the value at the location is not found
     * - the value at the location is set to null
     * - the value at the location is not a list of boolean values
     * 
     * then it will return either null or an empty list
     * depending on the options set. If options.returnEmpty
     * is false, then it will return null if no value is found. 
     * If it is true, then it will return an empty ArrayList.
     * 
     * @param prop
     * @return 
     */
    public List<Boolean> getBooleanList(String prop) {
        if (!options.returnEmpty) {
            return getBooleanList(prop, null);
        } else {
            return getBooleanList(prop, new ArrayList<Boolean>());
        }
    }

    /**
     * Gets a list of booleans from the given location (see getProperty()
     * for a description of the node syntax). If one of the 
     * following conditions is met,
     * 
     * - the value at the location is not found
     * - the value at the location is set to null
     * - the value at the location is not a list of boolean values
     * 
     * then it will return the given default result.
     * 
     * @param prop
     * @param defaultResult
     * @return 
     */
    public List<Boolean> getBooleanList(String prop, List<Boolean> defaultResult) {
        List<Object> out = this.getList(prop, null);

        if (out == null) {
            return defaultResult;
        } else {
            List<Boolean> newList = new ArrayList<Boolean>();
            new CollectionConverter<Boolean>().transform(new BooleanConverter(), out, newList);
            return newList;
        }
    }

    /**
     * Gets a list of strings from the given location (see getProperty()
     * for a description of the node syntax). If one of the 
     * following conditions is met,
     * 
     * - the value at the location is not found
     * - the value at the location is set to null
     * - the value at the location is not a list of strings
     * 
     * then it will return either null or an empty list
     * depending on the options set. If options.returnEmpty
     * is false, then it will return null if no value is found. 
     * If it is true, then it will return an empty ArrayList.
     * 
     * @param prop
     * @return 
     */
    public List<String> getStringList(String prop) {
        if (!options.returnEmpty) {
            return getStringList(prop, null);
        } else {
            return getStringList(prop, new ArrayList<String>());
        }
    }

    /**
     * Gets a list of strings from the given location (see getProperty()
     * for a description of the node syntax). If one of the 
     * following conditions is met,
     * 
     * - the value at the location is not found
     * - the value at the location is set to null
     * - the value at the location is not a list of strings
     * 
     * then it will return the given default result.
     * 
     * @param prop
     * @param defaultResult
     * @return 
     */
    public List<String> getStringList(String prop, List<String> defaultResult) {
        List<Object> out = this.getList(prop, null);

        if (out == null) {
            return defaultResult;
        } else {
            List<String> newList = new ArrayList<String>();
            new CollectionConverter<String>().transform(new StringConverter(), out, newList);
            return newList;
        }
    }

    /**
     * Gets a list of MapNodes from the given location (see getProperty()
     * for a description of the node syntax). If one of the 
     * following conditions is met,
     * 
     * - the value at the location is not found
     * - the value at the location is set to null
     * - the value at the location is not a list of Maps
     * 
     * then it will return either null or an empty list
     * depending on the options set. If options.returnEmpty
     * is false, then it will return null if no value is found. 
     * If it is true, then it will return an empty ArrayList.
     * 
     * @param prop
     * @return 
     */
    public List<MapNode> getMapNodeList(String prop) {
        if (!options.returnEmpty) {
            return getMapNodeList(prop, null);
        } else {
            return getMapNodeList(prop, new ArrayList<MapNode>());
        }
    }

    /**
     * Gets a list of MapNodes from the given location (see getProperty()
     * for a description of the node syntax). If one of the 
     * following conditions is met,
     * 
     * - the value at the location is not found
     * - the value at the location is set to null
     * - the value at the location is not a list of Maps
     * 
     * then it will return the given default result.
     * 
     * @param prop
     * @param defaultResult
     * @return 
     */
    public List<MapNode> getMapNodeList(String prop, List<MapNode> defaultResult) {
        List<Object> out = this.getList(prop, null);

        if (out == null) {
            return defaultResult;
        } else {
            List<MapNode> newList = new ArrayList<MapNode>();
            new CollectionConverter<MapNode>().transform(new MapNodeConverter(), out, newList);
            return newList;
        }
    }

    /**
     * Returns a YAML-formatter string of the base Map.
     * 
     * It is valid YAML syntax, and can actually be re-entered into
     * the MapNode at a later time with loadYaml().
     * 
     * @return 
     */
    public String debug() {
        DumperOptions yamlOpt = new DumperOptions();
        yamlOpt.setIndent(4);
        yamlOpt.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

        Yaml yaml = new Yaml(yamlOpt);
        return yaml.dump(base).trim();
    }

    /**
     * Fills in the base Map from the given YAML syntax.
     * 
     * @param input 
     */
    @SuppressWarnings("unchecked")
    public void loadYaml(String input) {
        DumperOptions yamlOpt = new DumperOptions();
        yamlOpt.setIndent(4);
        yamlOpt.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

        Yaml yaml = new Yaml(yamlOpt);
        Object output = yaml.load(input);
        if (output != null) {
            this.setBase((Map<String, Object>) output);
        } else {
            this.reset();
        }
    }

    /**
     * Clears the contents of the base Map.
     * 
     */
    public void reset() {
        this.base.clear();
    }

    /**
     * Clones the MapNode. This only works if the base map is
     * an instance of a HashMap, as Map does not implement the
     * Cloneable interface. 
     * 
     * @return
     * @throws CloneNotSupportedException 
     */
    @Override
    @SuppressWarnings("unchecked")
    public Object clone() throws CloneNotSupportedException {
        if (this.base instanceof HashMap) {
            HashMap<String, Object> hm = (HashMap<String, Object>) this.base;
            MapNode map = new MapNode((Map<String, Object>) hm.clone());
            map.options = (MapNodeOptions) this.options.clone();
            return map;
        }
        throw new CloneNotSupportedException("Base must be a HashMap");
    }

    /**
     * Returns true of the contents of both base objects are identical
     * and both of the options are equal. 
     * 
     * @param obj
     * @return 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof MapNode)) {
            return false;
        }

        final MapNode other = (MapNode) obj;
        if (this.base != other.base && (this.base == null || !this.base.equals(other.base))) {
            return false;
        }
        if (this.options != other.options && (this.options == null || !this.options.equals(other.options))) {
            return false;
        }
        return true;
    }

    /**
     * Gets the hashcode of the MapNode based off the Map and options.
     * @return 
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + (this.base != null ? this.base.hashCode() : 0);
        hash = 71 * hash + (this.options != null ? this.options.hashCode() : 0);
        return hash;
    }

    /**
     * Alias for debug(), outputs a YAML-formatted visual of the base Map.
     * @return 
     */
    @Override
    public String toString() {
        return debug();
    }
}
