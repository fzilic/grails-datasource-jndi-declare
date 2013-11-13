grails-datasource-jndi-declare
==============================

Simple grails plugin to declare jndi datasources as resource-ref elements in web.xml

Installation
 - simply add a build dependency to your BuildConfig.groovy
  - build 'datasource-jndi-declare:1.0'
  
Usage
 - there are no configuration options for this plugin
 - it wil detect all datasources with jndiName property and declare them as <resource-ref> element in web.xml
