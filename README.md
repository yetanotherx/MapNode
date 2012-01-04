MapNode
-------

A simple library used to access Map<String, Object> objects in a reliable,
easy-to-use method. It is inspired by the original ConfigurationNode by
[sk89q](http://sk89q.com) for [Bukkit](http://bukkit.org). This library
aims to be fairly similar to the old style, while extending functionality
and giving the user more control over its usage.


Usage
-----

MapNode is stored in a remote maven repository. To add it to your project,
you must add the following code to your maven pom.xml file:

    <repositories>
        <repository>
            <id>mcbouncer-repo</id>
            <url>http://maven.mcbouncer.com/archiva/repository/internal/</url>
        </repository>
    </repositories>

This will add the repository that hosts MapNode to your project, so it can use
the MapNode artifact. Once you've added the repository, add this dependency:

    <dependency>
        <groupId>com.yetanotherx</groupId>
        <artifactId>MapNode</artifactId>
        <version>1.0</version>
    </dependency>

After that, you are set to use MapNode! The heart of MapNode is the MapNode
class (surprise!), as that stores most of the logic that is necessary.

To create a new MapNode object for a Map<String, Object>, simply pass
the Map as the constructor parameter:

    Map<String, Object> map = new HashMap<String, Object>();
    Map<String, Object> map2 = new HashMap<String, Object>();
    map2.put("bix", "bic");
    
    map.put("foo", "bar");
    map.put("baz", map2);
    
    MapNode node = new MapNode(map);

`node` now contains a MapNode object for map. Now, say you wanted to access
the value of map2 with the key "bix". You can use `map.get("baz").get("bix")`,
but that has some issues. For example, what if `map.get("baz")` returns null?
You got a NullPointerException on your hands! What if you are trying to get a
Map that is contained in 5 other maps? You have a long, unwieldly length of
commands. If any of them fail, it's a pain to track. 

With MapNode, it's much easier to access that value. 

    String out = node.getString("baz.bix"); //out is now equal to "bic"

The [JavaDoc](http://bit.ly/mapnode_javadoc) has information about all the 
methods that you can call on MapNode, and how to use options to control the output.


Compiling
---------

You need to have Maven installed (http://maven.apache.org), as that will
include the necessary dependencies and package the mod automatically. If
there are any missing dependencies, you may need to download and build 
them manually. 

Note: For Maven to work properly, be sure to add Maven to your "PATH".

Once installed, run the following code to build MapNode:

    mvn clean install
    
This will build the classes, run unit tests on them, and build a JAR
that can be used by your project. 


Contributing
------------

Developing is a tricky business, and the more eyes, the better! I'm always
welcome to contributions to the code! If you find a security problem, a
rendering improvement, or any way to make the code better, feel free to fork
MapNode on GitHub, add your changes, and then submit a pull request. I'll
look at it, make comments, and merge it if I think your changes are good enough.


Support
-------

If you need help installing, find a bug, or just want to talk about MapNode,
feel free to message me on irc.esper.net. If you want to talk to me in 
private, you can also send me a message on GitHub. All messages on GitHub are sent
to my email, so I'll get back to you quickly.


Continuous Integration
----------------------

MapNode is continuously integrated, which means that each time a commit is
made to the repository, it is also build and packaged automatically. MapNode
uses Bamboo, and it is hosted at [bamboo.mcbouncer.com](http://bamboo.mcbouncer.com).


Credits
-------

 * [sk89q](http://sk89q.com) for writing ConfigurationNode, the inspiration for MapNode.


Legal stuff
-----------

This code is licensed under the BSD license.