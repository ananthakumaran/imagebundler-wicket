---
layout: default
title: Maven2 Configuration
header : Maven2 Configuration
meta_keywords: imagebundler , wicket image bundling , Wicket , wicket image sprite  , maven config 
meta_description: maven configuration for imageBundler 
---

Repository
----------
{% highlight xml %}
<repositories>
 <repository>
  <id>org.imagebundler.wicket</id>
  <name>imagebundler Maven Repository</name>
  <url>http://ananthakumaran.github.com/imagebundler-wicket/maven2</url>
 </repository>
</repositories>
<pluginRepositories>
  <pluginRepository>
   <id>maven-annotation-plugin</id>
   <name>maven-annotation-processor Repository</name>
   <url>http://maven-annotation-plugin.googlecode.com/svn/trunk/mavenrepo</url>
  </pluginRepository>
 </pluginRepositories>
{% endhighlight %} 
Dependency
----------
{% highlight xml %}
<dependency>
 <groupId>org.imagebundler.wicket</groupId>
 <artifactId>imagebundler-wicket</artifactId>
 <version>${imagebundler.version}</version>
</dependency>
{% endhighlight %}
Configuring Maven to process annotations
----------------------------------------
{% highlight xml %}
<plugin>
 <!--
  set source compliance level to 1.6, do not use javac to run
  annotation processors, we will use the maven-processor-plugin to do
  this
  -->
 <groupId>org.apache.maven.plugins</groupId>
 <artifactId>maven-compiler-plugin</artifactId>
  <configuration>
	<source>1.6</source>
	<target>1.6</target>
	<compilerArgument>-proc:none</compilerArgument>
 </configuration>
</plugin>
<plugin>
 <groupId>org.bsc.maven</groupId>
 <artifactId>maven-processor-plugin</artifactId>
  <executions>
	<execution>
	 <id>process</id>
	 <goals>
	  <goal>process</goal>
	 </goals>
	<phase>generate-sources</phase>
	 <configuration>
	  <!--
	   maven annotation processor doesn't set the -d option which makes
	   the StandardLocation.CLASS_OUTPUT to point to the project root
	   directory. Note please make sure that the path doesn't contain
	   any spaces(again that breaks everything)
	   http://code.google.com/p/maven-annotation-plugin/issues/detail?id=12
	   -->
	  <compilerArguments>-d ${project.build.outputDirectory}</compilerArguments>
	  <outputDirectory>target/generated-sources/apt/main/java</outputDirectory>
	  </configuration>
	 </execution>
 </executions>
	<dependencies />
</plugin>
{% endhighlight %}
Configure Maven2 Eclipse plugin to setup the project to use ImageBundler annotation processor when mvn eclipse:eclipse is executed
----------------------------------------------------------------------------------------------------------------------------------
{% highlight xml %}
 <plugin>
  <groupId>org.apache.maven.plugins</groupId>
   <artifactId>maven-eclipse-plugin</artifactId>
   <configuration>
   <downloadSources>true</downloadSources>
    <additionalConfig>
     <file>
	  <name>.factorypath</name>
	   <content>
        <![CDATA[<factorypath>
        <factorypathentry kind="VARJAR" 
		id="M2_REPO/org/imagebundler/wicket/imagebundler-wicket/${imagebundler.version}/imagebundler-wicket-${imagebundler.version}.jar" 
		enabled="true" runInBatchMode="false"/>
        </factorypath>]]>
	   </content>
	 </file>
	<file>
	 <name>.settings/org.eclipse.jdt.apt.core.prefs</name>
	 <content>
     <![CDATA[
     eclipse.preferences.version=1
     org.eclipse.jdt.apt.aptEnabled=true
     org.eclipse.jdt.apt.genSrcDir=target/generated-sources/apt/main/java
     org.eclipse.jdt.apt.reconcileEnabled=true]]>
	</content>
	</file>
   <file>
	<name>.settings/org.eclipse.jdt.core.prefs</name>
	<!-- instead of doing this add org.eclipse.jdt.core.compiler.processAnnotations=enabled to the file directly -->
	<content>
	<![CDATA[
      org.eclipse.jdt.core.compiler.codegen.targetPlatform=1.6
      eclipse.preferences.version=1
      org.eclipse.jdt.core.compiler.source=1.6
      org.eclipse.jdt.core.compiler.compliance=1.6
      org.eclipse.jdt.core.compiler.processAnnotations=enabled]]>
	</content>
   </file>
  </additionalConfig>
 </configuration>
</plugin>

<properties>
  <imagebundler.version>1.2</imagebundler.version>
</properties>
{% endhighlight %}
