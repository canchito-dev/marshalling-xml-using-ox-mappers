[![Buy Me a Coffee](images/donate_with_crypto.PNG)](https://commerce.coinbase.com/checkout/faf64f90-2e80-46ee-aeba-0fde14cbeb46)
[![Buy Me a Coffee](https://www.paypalobjects.com/en_US/ES/i/btn/btn_donateCC_LG.gif)](https://www.paypal.com/donate?hosted_button_id=GTSXAJQEBZ7XG)

# marshalling-xml-using-ox-mappers

<p style="text-align: justify;">In this post, you will create a Spring project and use Castor XML mapping for marshalling/unmarshalling Java objects into an XML document.</p>

<h2 style="text-align: justify;">Contribute Code</h2>
<p style="text-align: justify;">If you would like to become an active contributor to this project please follow these simple steps:</p>

<ol>
 	<li style="text-align: justify;">Fork it</li>
 	<li style="text-align: justify;">Create your feature branch</li>
 	<li style="text-align: justify;">Commit your changes</li>
 	<li style="text-align: justify;">Push to the branch</li>
 	<li style="text-align: justify;">Create new Pull Request</li>
</ol>
<p class="sect1"><strong>Source code</strong> can be downloaded from <a href="https://github.com/canchito-dev/marshalling-xml-using-ox-mappers" target="_blank" rel="noopener noreferrer">github</a>.</p>

<h2 class="sect1">What you’ll need</h2>
<ul>
 	<li>About 40 minutes</li>
 	<li>A favorite IDE. In this post, we use:Eclipse IDE for Java DevelopersVersion: Mars.2 Release (4.5.2)
Build id: 20160218-0600</li>
 	<li><a href="http://www.oracle.com/technetwork/java/javase/downloads/index.html" target="_blank" rel="noopener noreferrer">JDK 7</a> or later. It can be made to work with JDK6, but it will need configuration tweaks. Please check the Spring Boot documentation</li>
 	<li>An empty Spring project. You can follow the steps from <a href="http://canchito-dev.com/public/blog/2017/04/16/spring-initializer/" target="_blank" rel="noopener noreferrer">here</a>.</li>
</ul>
<h2>Introduction</h2>
<p style="text-align: justify;">Sometimes, you might come to need an easy way to read or write an XML file, or a Web service reply in the form of XML. When this happens, <a href="http://castor-data-binding.github.io/castor/">Castor</a> comes very handy.</p>
<p style="text-align: justify;"><a href="http://castor-data-binding.github.io/castor/">Castor</a> XML is an XML data binding framework, which deals data in an XML documents as object models which represent that data. This convertion from data to object and viceversa is refered as marshalling/unmarshalling.</p>
<p style="text-align: justify;">For those who are not familiar with the above teminology, <em>"marshalling"</em> means converting an object to a stream or sequence of bytes. While <em>"unmarshalling"</em> means converting a stream to an object.</p>
<p style="text-align: justify;">The conversion between Java object and XML is done by the XML data binding framework, and consists of the following classes:</p>

<ul>
 	<li style="text-align: justify;"><code>org.exolab.castor.xml.Marshaller</code>: Worker class for converting a Java object to XML document.</li>
 	<li style="text-align: justify;"><code>org.exolab.castor.xml.Unmarshaller</code>: Worker class for converting XML document to Java object.</li>
 	<li style="text-align: justify;"><code>org.exolab.castor.xml.XMLContext</code>: A bootstrap class used for configuration of the XML data binding framework and instantiation of the two worker objects.</li>
</ul>
<p style="text-align: justify;">There are three modes in which Castor XML can be used for marshalling/unmarshalling data to and from XML:</p>

<ul>
 	<li style="text-align: justify;">introspection mode</li>
 	<li style="text-align: justify;">mapping mode</li>
 	<li style="text-align: justify;">descriptor mode (aka generation mode)</li>
</ul>
<p style="text-align: justify;">In this post, we will focus only in the mapping mode, in which a user-defined mapping file is provided to <a href="http://castor-data-binding.github.io/castor/">Castor</a> XML. This user-defined mapping file is also an XML that allows the whole or partial definition of a customized mapping between Java classes (and their properties) and XML.</p>
<p style="text-align: justify;">For our example, we will read from an XML file and that same information will be written to a new XML document. We will do it in three samples:</p>

<ul style="text-align: justify;">
 	<li style="text-align: justify;">Marshal/Unmarshal a very simple XML file composed of one root element and several child elements.</li>
 	<li style="text-align: justify;">Marshal/Unmarshal a complex XML file composed of one root element and several child elements, but one of the child elements is a list of elements.</li>
 	<li style="text-align: justify;">Marshal/Unmarshal a complex XML file, but this time using custom field handler.</li>
</ul>
<p style="text-align: justify;">Even though the project has three examples, we will focus on explaining the most complex example: marshalling/unmarshalling a complex XML file composed of one root element and several child elements, and one of the child elements is a list of elements. During the unmarshalling process, we will use two custom field handler.</p>

<h2 style="text-align: justify;">Project's Layout</h2>
<pre class="EnlighterJSRAW" data-enlighter-language="raw" data-enlighter-linenumbers="false" data-enlighter-theme="enlighter">src/main/java
 |
 +- com
 |  +- canchitodev
 |     +- example
 |        +- MarshallingXmlUsingOxMappersApplication.java
 |        +- MarshallingXmlUsingOxMappersApplication.java
 |        +- MarshallingXmlUsingOxMappersApplication.java
 |        |
 |        +- domain
 |        |  +- basic
 |        |     +- Platform.java
 |        |
 |        |  +- complex
 |        |     +- Game.java
 |        |     +- Games.java
 |        |     +- Platform.java
 |        |
 |        +- field
 |           +- handlers
 |           |  +- DateHandler.java
 |           |  +- PriceHandler.java
 |           |
 |           |  +- domain
 |                 +- Game.java
 |                 +- Games.java
 |                 +- Platform.java
src/main/resources
 |
 +- basic
 |  +- basic-example.xml
 |  +- basic-mapping.xml
 |
 +- complex
 |  +- complex-example.xml
 |  +- complex-mapping.xml
 |
 +- field-convertion
 |  +- field-convertion-example.xml
 |  +- field-convertion-mapping.xml
 |
src/test/java
 |
 +- com
    +- canchitodev
       +- example
          +- MarshallingXmlUsingOxMappersApplicationTests.java
</pre>
<p style="text-align: justify;">So let's get started!</p>

<h2>Getting Started</h2>
<p style="text-align: justify;">Once you have created an empty project and imported into your favorite IDE, it is time to modify the <code>pom.xml</code> file. If you have not created the project yet, you can follow the steps described in <a href="http://canchito-dev.com/public/blog/2017/04/16/spring-initializer/" target="_blank" rel="noopener noreferrer">here</a>.</p>
<p style="text-align: justify;">Let's open the <code>pom.xml</code> file, and add the dependencies needed by <a href="http://castor-data-binding.github.io/castor/">Castor</a>.</p>
<p style="text-align: justify;">First we add Castor's and Xerces' version, that we will be using, as a property. Notice that we will use the latest current version at the moment of writing this post.</p>

<pre class="EnlighterJSRAW" data-enlighter-language="xml" data-enlighter-theme="classic">&lt;properties&gt;
  &lt;project.build.sourceEncoding&gt;UTF-8&lt;/project.build.sourceEncoding&gt;
  &lt;project.reporting.outputEncoding&gt;UTF-8&lt;/project.reporting.outputEncoding&gt;
  &lt;java.version&gt;1.8&lt;/java.version&gt;
  &lt;castor.version&gt;1.4.1&lt;/castor.version&gt;
  &lt;xerces.version&gt;2.11.0&lt;/xerces.version&gt;
&lt;/properties&gt;
</pre>
<p style="text-align: justify;">Next, we add three dependencies (Spring Oxm, Castor &amp; Xerces). But do not remove the previously added ones.</p>

<pre class="EnlighterJSRAW" data-enlighter-language="xml" data-enlighter-theme="classic">&lt;dependencies&gt;		
  &lt;!-- Marshalling XML using O/X Mappers --&gt;
  &lt;dependency&gt;
    &lt;groupId&gt;org.springframework&lt;/groupId&gt;
    &lt;artifactId&gt;spring-oxm&lt;/artifactId&gt;
  &lt;/dependency&gt;
  &lt;!-- Marshalling XML using O/X Mappers --&gt;
 
  &lt;!-- Castor - Data binding made easy --&gt;
  &lt;dependency&gt;
    &lt;groupId&gt;org.codehaus.castor&lt;/groupId&gt;
    &lt;artifactId&gt;castor-xml&lt;/artifactId&gt;
    &lt;version&gt;${castor.version}&lt;/version&gt;
  &lt;/dependency&gt;
  &lt;dependency&gt;
    &lt;groupId&gt;org.codehaus.castor&lt;/groupId&gt;
    &lt;artifactId&gt;castor-codegen&lt;/artifactId&gt;
    &lt;version&gt;${castor.version}&lt;/version&gt;
  &lt;/dependency&gt;
  &lt;!-- Castor need this --&gt;
  &lt;dependency&gt;
    &lt;groupId&gt;xerces&lt;/groupId&gt;
    &lt;artifactId&gt;xercesImpl&lt;/artifactId&gt;
    &lt;version&gt;${xerces.version}&lt;/version&gt;
  &lt;/dependency&gt;
  &lt;!-- Castor - Data binding made easy --&gt;
&lt;/dependencies&gt;
</pre>
<p style="text-align: justify;">That's it. You have successfully included <a href="http://castor-data-binding.github.io/castor/">Castor</a> into your project.</p>

<h2 style="text-align: justify;">Defining the XML</h2>
<p style="text-align: justify;">Here is a representation of the XML file that we will be unmarshalling. You can find it at <code>src/java/resources/field-convertion</code>.</p>

<pre class="EnlighterJSRAW" data-enlighter-language="xml">&lt;?xml version="1.0" encoding="UTF-8"?&gt;
&lt;Platform&gt;
  &lt;Name&gt;Playstation 4&lt;/Name&gt;
  &lt;Developer&gt;Sony Interactive Entertainment&lt;/Developer&gt;
  &lt;Manufacturer&gt;Sony, Foxconn&lt;/Manufacturer&gt;
  &lt;ReleaseDate&gt;29/11/2013&lt;/ReleaseDate&gt;
  &lt;Price&gt;€399.99&lt;/Price&gt;
  &lt;Website&gt;http://playstation.com/ps4/&lt;/Website&gt;
  &lt;Games&gt;
    &lt;Game&gt;
      &lt;Name&gt;Uncharted 4: A Thief's End&lt;/Name&gt;
      &lt;ReleaseDate&gt;10/05/2016&lt;/ReleaseDate&gt;
      &lt;Price&gt;€35.90&lt;/Price&gt;
      &lt;URL&gt;https://www.unchartedthegame.com/en-us/&lt;/URL&gt;
      &lt;Developer&gt;Naughty Dog&lt;/Developer&gt;
      &lt;Publisher&gt;Sony Computer Entertainment&lt;/Publisher&gt;
    &lt;/Game&gt;
    &lt;Game&gt;
      &lt;Name&gt;Batman: Arkham Knight&lt;/Name&gt;
      &lt;ReleaseDate&gt;23/06/2015&lt;/ReleaseDate&gt;
      &lt;Price&gt;€17.99&lt;/Price&gt;
      &lt;URL&gt;https://www.batmanarkhamknight.com/&lt;/URL&gt;
      &lt;Developer&gt;Rocksteady Studios&lt;/Developer&gt;
      &lt;Publisher&gt;Warner Bros. Interactive Entertainment&lt;/Publisher&gt;
    &lt;/Game&gt;
    &lt;Game&gt;
      &lt;Name&gt;Wolfenstein II: The New Colossus&lt;/Name&gt;
      &lt;ReleaseDate&gt;27/10/2017&lt;/ReleaseDate&gt;
      &lt;Price&gt;€69.99&lt;/Price&gt;
      &lt;URL/&gt;
      &lt;Developer&gt;MachineGames&lt;/Developer&gt;
      &lt;Publisher&gt;Bethesda Softworks&lt;/Publisher&gt;
    &lt;/Game&gt;
  &lt;/Games&gt;
&lt;/Platform&gt;</pre>
<h2 style="text-align: justify;">Defining the Java Objects</h2>
<p style="text-align: justify;">As you might remember, we will focus this post on explaining how to marshal/unmarshal a complex XML file composed of one root element and several child elements, and one of the child elements is a list of elements. During the unmarshalling process, we will use two custom field handler. All of the Java object that will be used are located in package <code>com.canchitodev.example.field.handlers.domain</code>.</p>
Based on the XML, we can see that there are three objects:
<pre class="EnlighterJSRAW" data-enlighter-language="java">public class Platform {
  
  private String name;
  private String developer;
  private String manufacturer;
  private Date releaseDate;
  private String price;
  private String website;
  private Games games;

  public Platform() {}

  public Platform(String name, String developer, String manufacturer, Date releaseDate, String price,
      String website, Games games) {
    this.name = name;
    this.developer = developer;
    this.manufacturer = manufacturer;
    this.releaseDate = releaseDate;
    this.price = price;
    this.website = website;
    this.games = games;
  }

  // Getters and Setters
  // ...
}</pre>
<pre class="EnlighterJSRAW" data-enlighter-language="java">public class Games {
  
  private List&lt;Game&gt; game;

  public Games() {
    this.game = new ArrayList&lt;Game&gt;();
  }

  public Games(List&lt;Game&gt; game) {
    this.game = game;
  }

  // Getters and Setters
  // ...
}</pre>
<pre class="EnlighterJSRAW" data-enlighter-language="java">public class Game {

  private String name;
  private Date releaseDate;
  private String price;
  private String url;
  private String developer;
  private String publisher;
  
  public Game() {}

  public Game(String name, Date releaseDate, String price, String url, String developer, String publisher) {
    this.name = name;
    this.releaseDate = releaseDate;
    this.price = price;
    this.url = url;
    this.developer = developer;
    this.publisher = publisher;
  }

  // Getters and Setters
  // ...
}</pre>
If you pay attention, we have created an object for each XML's element that has child elements.
<h2 style="text-align: justify;">Defining the XML Mapping</h2>
<p style="text-align: justify;">In order to use the XML mapping technique, we have to first define the mapping information. This is an XML document, which describes how the properties of the Java Object have to be translated into XML. However, the only contraint for the mapping file is that <a href="http://castor-data-binding.github.io/castor/">Castor</a> must unambiguously infer from it, how a given XML element/attribute has to be translated into the object model during unmarshalling.</p>
Put into simple words, the XML mapping file explains for each Java object how each of its fields have to be mapped into XML. For this, <a href="http://castor-data-binding.github.io/castor/">Castor</a> considers each field as an abstraction of an object's property. And by it, it can be accessed via public class variable or using accessor methods (setters and getters). Whenever <a href="http://castor-data-binding.github.io/castor/">Castor</a> has to handle an object or XML data which information is not in the mapping file, it will follow its default behavior, by using Java's Reflection API to introspect the Java object and determine what to do. Both methods can be simultaneously used.
<p style="text-align: justify;"><strong>Note:</strong> <a href="http://castor-data-binding.github.io/castor/">Castor</a> can’t handle all possible mappings. In some complex cases, it may be necessary to rely on an XSL transformation in conjunction with <a href="http://castor-data-binding.github.io/castor/">Castor</a> to adapt the XML document to a more friendly format.</p>

<pre class="EnlighterJSRAW" data-enlighter-language="xml">&lt;?xml version="1.0"?&gt;
&lt;!DOCTYPE mapping PUBLIC "-//EXOLAB/Castor Mapping DTD Version 1.0//EN" "http://castor.org/mapping.dtd"&gt;
&lt;mapping&gt;
    &lt;description&gt;
    marshalling-xml-using-ox-mappers: Demo project for marshalling XML using O/X Mappers with Castos
    &lt;/description&gt;
    
  	&lt;class name="com.canchitodev.example.field.handlers.domain.Platform"&gt;   
  		&lt;map-to xml="Platform" /&gt;
        &lt;field name="name" type="string"&gt;
            &lt;bind-xml name="Name" node="element" /&gt;
        &lt;/field&gt;
        &lt;field name="developer" type="string"&gt;
            &lt;bind-xml name="Developer" node="element" /&gt;
        &lt;/field&gt;
        &lt;field name="manufacturer" type="string"&gt;
            &lt;bind-xml name="Manufacturer" node="element" /&gt;
        &lt;/field&gt;
        &lt;field name="releaseDate" type="string" handler="com.canchitodev.example.field.handlers.DateHandler"&gt;
            &lt;bind-xml name="ReleaseDate" node="element" /&gt;
        &lt;/field&gt;
        &lt;field name="price" type="string" handler="com.canchitodev.example.field.handlers.PriceHandler"&gt;
            &lt;bind-xml name="Price" node="element" /&gt;
        &lt;/field&gt;
        &lt;field name="website" type="string"&gt;
            &lt;bind-xml name="Website" node="element" /&gt;
        &lt;/field&gt;
        &lt;field name="Games" type="com.canchitodev.example.field.handlers.domain.Games"&gt;
            &lt;bind-xml name="Games" node="element" /&gt;
        &lt;/field&gt;
   	&lt;/class&gt;
   	
   	&lt;class name="com.canchitodev.example.field.handlers.domain.Games"&gt;   
  		&lt;map-to xml="Games" /&gt;
        &lt;field name="Game" type="com.canchitodev.example.field.handlers.domain.Game" collection="arraylist"&gt;
        	&lt;bind-xml name="Game" node="element" /&gt;
        &lt;/field&gt;
   	&lt;/class&gt;
   	
   	&lt;class name="com.canchitodev.example.field.handlers.domain.Game"&gt;   
  		&lt;map-to xml="Game" /&gt;
        &lt;field name="name" type="string"&gt;
            &lt;bind-xml name="Name" node="element" /&gt;
        &lt;/field&gt;
        &lt;field name="releaseDate" type="string" handler="com.canchitodev.example.field.handlers.DateHandler"&gt;
            &lt;bind-xml name="ReleaseDate" node="element" /&gt;
        &lt;/field&gt;
        &lt;field name="price" type="string" handler="com.canchitodev.example.field.handlers.PriceHandler"&gt;
            &lt;bind-xml name="Price" node="element" /&gt;
        &lt;/field&gt;
        &lt;field name="url" type="string"&gt;
            &lt;bind-xml name="URL" node="element" /&gt;
        &lt;/field&gt;
        &lt;field name="developer" type="string"&gt;
            &lt;bind-xml name="Developer" node="element" /&gt;
        &lt;/field&gt;
        &lt;field name="publisher" type="string"&gt;
            &lt;bind-xml name="Publisher" node="element" /&gt;
        &lt;/field&gt;
   	&lt;/class&gt;
     	
 &lt;/mapping&gt;</pre>
<h2 style="text-align: justify;">Marshalling Behavior</h2>
<p style="text-align: justify;">When using <a href="http://castor-data-binding.github.io/castor/">Castor</a>'s XML framework, each XML element has to map to a Java class. Everytime <a href="http://castor-data-binding.github.io/castor/">Castor</a> marshals an object, it will:</p>

<ul style="text-align: justify;">
 	<li style="text-align: justify;">if present, use the mapping information to find the name of the element to create; or</li>
 	<li style="text-align: justify;">create a name using the name of the class, which is its default behavior</li>
</ul>
<p style="text-align: justify;">Afterwards, the fields' information from the mapping file is used to decide the way a particular object's property has to converted into into one only one of the following:</p>

<ul style="text-align: justify;">
 	<li style="text-align: justify;">an attribute</li>
 	<li style="text-align: justify;">an element</li>
 	<li style="text-align: justify;">text content</li>
 	<li style="text-align: justify;">nothing, as we can choose to ignore a particular field</li>
</ul>
<p style="text-align: justify;">If no inforamtion for a given class in found in the mapping XML file, by default, <a href="http://castor-data-binding.github.io/castor/">Castor</a> will introspect the class and apply a set of predefined rules to guess the fields and marshal them. The predefined rules are as follows:</p>

<ul style="text-align: justify;">
 	<li style="text-align: justify;">All primitive types, including the primitive type wrappers (Boolean, Short, etc…) are marshalled as attributes.</li>
 	<li style="text-align: justify;">All other objects are marshalled as elements with either text content or element content.</li>
</ul>
<h2 style="text-align: justify;">Unmarshalling Behavior</h2>
<p style="text-align: justify;">During the unmarshalling process, if <a href="http://castor-data-binding.github.io/castor/">Castor</a> finds an element, it will try to use the information found in the XML mapping file to determine which object to instantiate. When no mapping information is found, <a href="http://castor-data-binding.github.io/castor/">Castor</a> will try to guess the name of the class to instantiate, by using the element's name. Afterwards, it will use the field information of the mapping file to handle the content of the element.</p>
<p style="text-align: justify;">If no inforamtion for a given class in found in the mapping XML file, by default, <a href="http://castor-data-binding.github.io/castor/">Castor</a> will introspect the class in order to find out if there any method of the form getXxxYyy()/setXxxYyy(&lt;type&gt; x). This accessor will be associated with XML element/attribute named ‘xxx-yyy’.</p>
<p style="text-align: justify;">Before continuing, it is important to understand the <a href="http://castor-data-binding.github.io/castor/">Castor</a>'s marshalling/unmarshalling behavior. <a href="http://castor-data-binding.github.io/castor/">Castor</a>'s XML framework is very well documented. I recommend reading the following links: (1) <a href="https://castor-data-binding.github.io/castor/reference-guide/reference/xml/xml-framework.html">XML Framework</a> and (2) <a href="https://castor-data-binding.github.io/castor/reference-guide/reference/xml/xml-mapping.html">XML Mapping</a>.</p>

<h2 style="text-align: justify;">Defining the Marshalling/Unmarshalling Class</h2>
First we define the class for handling marshalling/unmarshalling of the XML. Notice that this class has both methods.
<pre class="EnlighterJSRAW" data-enlighter-language="java">public class XMLMarshalUtil {
  
  private Marshaller marshaller;
    private Unmarshaller unmarshaller;
    
    public XMLMarshalUtil() {}
    
    public void setMarshaller(Marshaller marshaller) {
        this.marshaller = marshaller;
    }
    
    public void setUnmarshaller(Unmarshaller unmarshaller) {
        this.unmarshaller = unmarshaller;
    }

    //Converts Object to XML file
    public void doMarshaling(String filename, Object graph) throws IOException {
    	OutputStream fos = null;
        try {
        	fos = new FileOutputStream(filename);
            marshaller.marshal(graph, new StreamResult(fos));
        } finally {
        	if(fos != null)
        		fos.close();
        }
    }
    
    //Converts Object to XML file
    public void doMarshaling(OutputStream fos, Object graph) throws IOException {
        try {
            marshaller.marshal(graph, new StreamResult(fos));
        } finally {
        	if(fos != null)
        		fos.close();
        }
    }
    
    //Converts XML to Java Object
    public Object doUnMarshaling(String filename) throws IOException {
        InputStream fis = null;
        try {
            fis = new FileInputStream(filename);
            return unmarshaller.unmarshal(new StreamSource(fis));
        } finally {
        	if(fis != null)
        		fis.close();
        }
    }
    
    //Converts XML to Java Object
    public Object doUnMarshaling(InputStream fis) throws IOException {
        try {
            return unmarshaller.unmarshal(new StreamSource(fis));
        } finally {
        	if(fis != null)
        		fis.close();
        }
    }
}</pre>
Second, we define the bean that will return us an instance of the class for manipulating the XML.
<pre class="EnlighterJSRAW" data-enlighter-language="java">@Configuration
public class XMLMarshalBean {
  
  @Bean(name = "xmlFieldConvertionHandler")
  public XMLMarshalUtil getXmlFieldConvertionHandler() throws IOException{
    XMLMarshalUtil handler = new XMLMarshalUtil();
    handler.setMarshaller(getXmlFieldConvertionMarshaller());
    handler.setUnmarshaller(getXmlFieldConvertionMarshaller());
    return handler;
  }
  
  @Bean(name = "xmlFieldConvertionMarshaller")
  public CastorMarshaller getXmlFieldConvertionMarshaller() throws IOException {
    CastorMarshaller castorMarshaller = new CastorMarshaller();
    castorMarshaller.setCastorProperties(this.castorProperties());
    castorMarshaller.setMappingLocation(
        new ClassPathResource("field-convertion/field-convertion-mapping.xml")
    );
    return castorMarshaller;
  }
  
  private HashMap&lt;String, String&gt; castorProperties() {
    	HashMap&lt;String, String&gt; properties = new HashMap&lt;String, String&gt;();
        properties.put("org.exolab.castor.indent", "true");
        properties.put("org.exolab.castor.debug", "true");
        return properties;
    }
}</pre>
<h2 style="text-align: justify;">Custom Field Handlers</h2>
<p style="text-align: justify;">There are occassions in which we need to deal with data format that are not nativably support by <a href="http://castor-data-binding.github.io/castor/">Castor</a>, or manipulate fields to get the desired output without changing the object model. To deal with this, <a href="http://castor-data-binding.github.io/castor/">Castor</a> has several interesting interfaces. For this post, we will focus on <code>org.exolab.castor.mapping.GeneralizedFieldHandler</code> interface. We have implemented two field handlers: (1) for converting a date from English date format to Spanish date format, and (2) for converting the price form Euro (€) to US Dollars (US$). Both field handlers can be found in package <code>com.canchitodev.example.field.handlers</code>.</p>
Here is the class for manipulating dates:
<pre class="EnlighterJSRAW" data-enlighter-language="java">/**
 * The GeneralizedFieldHandler for the Date class
 * A org.exolab.castor.mapping.GeneralizedFieldHandler is an extension of FieldHandler interface where we simply write the conversion 
 * methods and Castor will automatically handle the underlying get/set operations. This allows us to re-use the same FieldHandler for 
 * fields from different classes that require the same conversion.
 **/
public class DateHandler extends GeneralizedFieldHandler  {
  
  private static final String SOURCE_FORMAT = "dd/MM/yyyy";

  /**
     * Creates a new DateHandler instance
     **/
  public DateHandler() {
    super();
  }

  /**
     * This method is used to convert the value when the getValue method is called. The getValue method will
     * obtain the actual field value from given 'parent' object.
     * 
     * This convert method is then invoked with the field's value. The value returned from this method will be
     * the actual value returned by getValue method.
     *
     * @param value the object value to convert after  performing a get operation
     * @return the converted value.
     **/
  @Override
  public Object convertUponGet(Object value) {
        SimpleDateFormat formatter = new SimpleDateFormat(SOURCE_FORMAT);
    if (value == null) return formatter.format(new Date());
        Date date = (Date)value;
        return formatter.format(date);
  }

  /**
     * This method is used to convert the value when the setValue method is called. The setValue method will
     * call this method to obtain the converted value.
     * 
     * The converted value will then be used as the value to set for the field.
     *
     * @param value the object value to convert before performing a set operation
     * @return the converted value.
     **/
  @Override
  public Object convertUponSet(Object value) {
        SimpleDateFormat formatter = new SimpleDateFormat(SOURCE_FORMAT);
        Date date = null;
        try {
            date = formatter.parse((String)value);
        }
        catch(ParseException px) {
            throw new IllegalArgumentException(px.getMessage());
        }
        return date;
  }

  /**
     * Returns the class type for the field that this
     * GeneralizedFieldHandler converts to and from. This should be the type that is used in the object model.
     *
     * @return the class type of of the field
     **/
  @SuppressWarnings("rawtypes")
  @Override
  public Class getFieldType() {
    return Date.class;
  }
  
  /**
     * Creates a new instance of the object described by this field.
     *
     * @param parent The object for which the field is created
     * @return A new instance of the field's value
     * @throws IllegalStateException This field is a simple type and cannot be instantiated
     **/
    public Object newInstance(Object parent) throws IllegalStateException{
        //-- Since it's marked as a string...just return null,
        //-- it's not needed.
        return null;
    }
}</pre>
And here is the class for doing the currency convertion:
<pre class="EnlighterJSRAW" data-enlighter-language="java">/**
 * The GeneralizedFieldHandler for the Date class
 * A org.exolab.castor.mapping.GeneralizedFieldHandler is an extension of FieldHandler interface where we simply write the conversion 
 * methods and Castor will automatically handle the underlying get/set operations. This allows us to re-use the same FieldHandler for 
 * fields from different classes that require the same conversion.
 **/
public class PriceHandler extends GeneralizedFieldHandler  {
  
  private static final String EUROS_SIGN = "€";
  private static final String DOLLARS_SIGN = "$";

  /**
     * Creates a new DateHandler instance
     **/
  public PriceHandler() {
    super();
  }

  /**
     * This method is used to convert the value when the getValue method is called. The getValue method will
     * obtain the actual field value from given 'parent' object.
     * 
     * This convert method is then invoked with the field's value. The value returned from this method will be
     * the actual value returned by getValue method.
     *
     * @param value the object value to convert after  performing a get operation
     * @return the converted value.
     **/
  @Override
  public Object convertUponGet(Object value) {
    if (value == null) return "$0.00";
    
    String price = (String) value;
    price = price.replace(this.EUROS_SIGN, "");
    
    Double priceInDollars = (double) Math.round(Double.parseDouble(price) * 1.2);
    return this.DOLLARS_SIGN + priceInDollars.toString();
  }

  /**
     * This method is used to convert the value when the setValue method is called. The setValue method will
     * call this method to obtain the converted value.
     * 
     * The converted value will then be used as the value to set for the field.
     *
     * @param value the object value to convert before performing a set operation
     * @return the converted value.
     **/
  @Override
  public Object convertUponSet(Object value) {
    return value;
  }

  /**
     * Returns the class type for the field that this
     * GeneralizedFieldHandler converts to and from. This should be the type that is used in the object model.
     *
     * @return the class type of of the field
     **/
  @SuppressWarnings("rawtypes")
  @Override
  public Class getFieldType() {
    return String.class;
  }
  
  /**
     * Creates a new instance of the object described by this field.
     *
     * @param parent The object for which the field is created
     * @return A new instance of the field's value
     * @throws IllegalStateException This field is a simple type and cannot be instantiated
     **/
    public Object newInstance(Object parent) throws IllegalStateException{
        //-- Since it's marked as a string...just return null,
        //-- it's not needed.
        return null;
    }
}</pre>
<p style="text-align: justify;"><a href="http://castor-data-binding.github.io/castor/">Castor</a>'s XML framework is very well documented. I recommend reading the following <a href="https://castor-data-binding.github.io/castor/reference-guide/reference/xml/xml-fieldhandlers.html">link</a> in order to understand much better how to write custom field handlers.</p>

<h2 style="text-align: justify;">Let's test it!!</h2>
<p style="text-align: justify;">The only thing left to do it to test our code. You can find a jUnit class with three functions for testing our three examples. Just remember to comment the <code>@Ignore</code> annotation.</p>
<p style="text-align: justify;"><!--Ads4--></p>

<h2 style="text-align: justify;">Summary</h2>
<p style="text-align: justify;">In this post, you will learned:</p>

<ul>
 	<li>Get familier with Castor XML mapping for reading/writing XML documents.</li>
 	<li style="text-align: justify;">Convert an XML document to a Java object using Castor Unmarshaller.</li>
 	<li style="text-align: justify;">Write Java objects to an XML document using Castor Marshaller.</li>
 	<li style="text-align: justify;">Writing Castor XML custom field handlers.</li>
</ul>
<p style="text-align: justify;">Hope you enjoyed this post as much as I did writing it. Please leave your comments and feedback.</p>
