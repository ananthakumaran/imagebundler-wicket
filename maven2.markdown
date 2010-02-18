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
&lt;repositories&gt;
 &lt;repository&gt;
  &lt;id&gt;org.imagebundler.wicket&lt;/id&gt;
  &lt;name&gt;imagebundler Maven Repository&lt;/name&gt;
  &lt;url&gt;http://imagebundler-wicket.googlecode.com/svn/maven2&lt;/url&gt;
 &lt;/repository&gt;
&lt;/repositories&gt;
&lt;pluginRepositories&gt;
  &lt;pluginRepository&gt;
   &lt;id&gt;maven-annotation-plugin&lt;/id&gt;
   &lt;name&gt;maven-annotation-processor Repository&lt;/name&gt;
   &lt;url&gt;http://maven-annotation-plugin.googlecode.com/svn/trunk/mavenrepo&lt;/url&gt;
  &lt;/pluginRepository&gt;
 &lt;/pluginRepositories&gt;
{% endhighlight %} 
Dependency
----------
{% highlight xml %}
&lt;dependency&gt;
 &lt;groupId&gt;org.imagebundler.wicket&lt;/groupId&gt;
 &lt;artifactId&gt;imagebundler-wicket&lt;/artifactId&gt;
 &lt;version&gt;${imagebundler.version}&lt;/version&gt;
&lt;/dependency&gt;
{% endhighlight %}
Configuring Maven to process annotations
----------------------------------------
{% highlight xml %}
&lt;plugin&gt;
 &lt;!--
  set source compliance level to 1.6, do not use javac to run
  annotation processors, we will use the maven-processor-plugin to do
  this
  --&gt;
 &lt;groupId&gt;org.apache.maven.plugins&lt;/groupId&gt;
 &lt;artifactId&gt;maven-compiler-plugin&lt;/artifactId&gt;
  &lt;configuration&gt;
	&lt;source&gt;1.6&lt;/source&gt;
	&lt;target&gt;1.6&lt;/target&gt;
	&lt;compilerArgument&gt;-proc:none&lt;/compilerArgument&gt;
 &lt;/configuration&gt;
&lt;/plugin&gt;
&lt;plugin&gt;
 &lt;groupId&gt;org.bsc.maven&lt;/groupId&gt;
 &lt;artifactId&gt;maven-processor-plugin&lt;/artifactId&gt;
  &lt;executions&gt;
	&lt;execution&gt;
	 &lt;id&gt;process&lt;/id&gt;
	 &lt;goals&gt;
	  &lt;goal&gt;process&lt;/goal&gt;
	 &lt;/goals&gt;
	&lt;phase&gt;generate-sources&lt;/phase&gt;
	 &lt;configuration&gt;
	  &lt;!--
	   maven annotation processor doesn't set the -d option which makes
	   the StandardLocation.CLASS_OUTPUT to point to the project root
	   directory. Note please make sure that the path doesn't contain
	   any spaces(again that breaks everything)
	   http://code.google.com/p/maven-annotation-plugin/issues/detail?id=12
	   --&gt;
	  &lt;compilerArguments&gt;-d ${project.build.outputDirectory}&lt;/compilerArguments&gt;
	  &lt;/configuration&gt;
	 &lt;/execution&gt;
 &lt;/executions&gt;
	&lt;dependencies /&gt;
&lt;/plugin&gt;
{% endhighlight %}
Configure Maven2 Eclipse plugin to setup the project to use ImageBundler annotation processor when mvn eclipse:eclipse is executed
----------------------------------------------------------------------------------------------------------------------------------
{% highlight xml %}
 &lt;plugin&gt;
  &lt;groupId&gt;org.apache.maven.plugins&lt;/groupId&gt;
   &lt;artifactId&gt;maven-eclipse-plugin&lt;/artifactId&gt;
   &lt;configuration&gt;
   &lt;downloadSources&gt;true&lt;/downloadSources&gt;
    &lt;additionalConfig&gt;
     &lt;file&gt;
	  &lt;name&gt;.factorypath&lt;/name&gt;
	   &lt;content&gt;
        &lt;![CDATA[&lt;factorypath&gt;
        &lt;factorypathentry kind="VARJAR" 
		id="M2_REPO/org/imagebundler/wicket/imagebundler-wicket/${imagebundler.version}/imagebundler-wicket-${imagebundler.version}.jar" 
		enabled="true" runInBatchMode="false"/&gt;
        &lt;/factorypath&gt;]]&gt;
	   &lt;/content&gt;
	 &lt;/file&gt;
	&lt;file&gt;
	 &lt;name&gt;.settings/org.eclipse.jdt.apt.core.prefs&lt;/name&gt;
	 &lt;content&gt;
     &lt;![CDATA[
     eclipse.preferences.version=1
     org.eclipse.jdt.apt.aptEnabled=true
     org.eclipse.jdt.apt.genSrcDir=target/generated-sources/apt/main/java
     org.eclipse.jdt.apt.reconcileEnabled=true]]&gt;
	&lt;/content&gt;
	&lt;/file&gt;
   &lt;file&gt;
	&lt;name&gt;.settings/org.eclipse.jdt.core.prefs&lt;/name&gt;
	&lt;!-- instead of doing this add org.eclipse.jdt.core.compiler.processAnnotations=enabled to the file directly --&gt;
	&lt;content&gt;
	&lt;![CDATA[
      org.eclipse.jdt.core.compiler.codegen.targetPlatform=1.6
      eclipse.preferences.version=1
      org.eclipse.jdt.core.compiler.source=1.6
      org.eclipse.jdt.core.compiler.compliance=1.6
      org.eclipse.jdt.core.compiler.processAnnotations=enabled]]&gt;
	&lt;/content&gt;
   &lt;/file&gt;
  &lt;/additionalConfig&gt;
 &lt;/configuration&gt;
&lt;/plugin&gt;

&lt;properties&gt;
  &lt;imagebundler.version&gt;1.0&lt;/imagebundler.version&gt;
&lt;/properties&gt;
{% endhighlight %}
