grails-datasource-jndi-declare
==============================

Simple grails plugin to declare jndi datasources as resource-ref elements in web.xml

Installation
------------
Simply add a build dependency to your BuildConfig.groovy
```groovy
build ':datasource-jndi-declare:1.0.0'
```

Usage
-----
There are no configuration options for this plugin
It wil detect all datasources with jndiName property and declare them as ```<resource-ref>``` element in web.xml
