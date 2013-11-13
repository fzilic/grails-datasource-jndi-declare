/*
* Licensed to the Apache Software Foundation (ASF) under one
* or more contributor license agreements.  See the NOTICE file
* distributed with this work for additional information
* regarding copyright ownership.  The ASF licenses this file
* to you under the Apache License, Version 2.0 (the
* "License"); you may not use this file except in compliance
* with the License.  You may obtain a copy of the License at
*
*  http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*/

package org.grails.plugins.datasourcejndideclare

import groovy.xml.StreamingMarkupBuilder
import org.codehaus.groovy.grails.commons.ApplicationHolder
import org.codehaus.groovy.grails.commons.DefaultGrailsApplication;
import org.junit.Test

class UpdateWebDescriptorTest {

  def WEB_XML = '''<?xml version="1.0" encoding="utf-16"?>
<web-app xsi:schemaLocation="http://java.sun.com/xml/ns/j2ee http://java.sun.com/xml/ns/j2ee/web-app_2_4.xsd" version="2.4" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://java.sun.com/xml/ns/j2ee">
    <display-name>/datasource-jndi-declare-development-1.0.0-SNAPSHOT</display-name>
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>/WEB-INF/applicationContext.xml</param-value>
    </context-param>
    <servlet>
        <servlet-name>grails</servlet-name>
        <servlet-class>org.codehaus.groovy.grails.web.servlet.GrailsDispatcherServlet</servlet-class>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <welcome-file-list>
        <welcome-file>index.html</welcome-file>
        <welcome-file>index.jsp</welcome-file>
        <welcome-file>index.gsp</welcome-file>
    </welcome-file-list>
</web-app>'''

  String serialize(xml) {
    def outputBuilder = new StreamingMarkupBuilder()
    outputBuilder.bind {
      mkp.declareNamespace('': 'http://java.sun.com/xml/ns/j2ee')
      mkp.yield xml
    }
  }

  @Test
  void shouldNotAddIfJndiDataSourceIsNotConfigured() {

    def application = new DefaultGrailsApplication()
    application.config.dataSource.dbCreate = 'create-drop'
    application.config.dataSource.url = 'jdbc:h2:mem:devDb;MVCC=TRUE'

//    def config = (application.config as ConfigObject)
//    application.config = config

    def datasources = application.config.findAll { it.key =~ /^dataSource.*/ }

    def xml = new XmlSlurper().parseText(WEB_XML)

    UpdateWebDescriptor.with(xml, datasources)

    def result = serialize(xml)

    assert result
    assert !result.contains('resource-ref')
  }

  @Test
  void shouldAddIfJndDataSourceIsConfigured() {
    def application = new DefaultGrailsApplication()
    application.metadata['app.name'] = 'app-name'
    application.config.dataSource.jndiName = 'java:comp/env/jdbc/ds'

    def xml = new XmlSlurper().parseText(WEB_XML)

    def datasources = application.config.findAll { it.key =~ /^dataSource.*/ }

    UpdateWebDescriptor.with(xml, datasources)

    def result = serialize(xml)

    assert result

    def given = new XmlSlurper().parseText(result)

    def resourceRef = given.'resource-ref'
    assert resourceRef

//    assert resourceRef.'description' == 'app-name\'s datasource'
    assert resourceRef.'res-ref-name' == 'jdbc/ds'
    assert resourceRef.'res-type' == 'javax.sql.DataSource'
    assert resourceRef.'res-auth' == 'Container'
  }

  @Test
  void shouldAddIfJndDataSourceIsConfiguredWithLegacyJndiName() {
    def application = new DefaultGrailsApplication()
    application.metadata['app.name'] = 'app-name'
    application.config.dataSource.jndiName = 'jdbc/ds'

    def xml = new XmlSlurper().parseText(WEB_XML)

    def datasources = application.config.findAll { it.key =~ /^dataSource.*/ }

    UpdateWebDescriptor.with(xml, datasources)

    def result = serialize(xml)

    assert result

    def given = new XmlSlurper().parseText(result)

    def resourceRef = given.'resource-ref'
    assert resourceRef

//    assert resourceRef.'description' == 'app-name\'s datasource'
    assert resourceRef.'res-ref-name' == 'jdbc/ds'
    assert resourceRef.'res-type' == 'javax.sql.DataSource'
    assert resourceRef.'res-auth' == 'Container'
  }

}
