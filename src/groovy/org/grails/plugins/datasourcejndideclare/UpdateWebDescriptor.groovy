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

class UpdateWebDescriptor {
  static def with(xml, datasources) {

    datasources.each { name, configOptions ->
      def jndiName = configOptions?.jndiName

      if (jndiName) {
        def referenceName = jndiName.replace('java:comp/env/', '')

        // if a resource reference for the same jdbc resource is already in web.xml no need to add it again
        if (xml.'resource-ref'.grep { it?.'res-ref-name'?.text() == referenceName }) {
          return
        }

        def addPoint = xml.children()[xml.children().size() - 1]
        addPoint + {
          'resource-ref' {
            'description'('')
            'res-ref-name'(referenceName)
            'res-type'('javax.sql.DataSource')
            'res-auth'('Container')
          }
        }
      }
    }
  }
}
