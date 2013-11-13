import org.grails.plugins.datasourcejndideclare.UpdateWebDescriptor

class DatasourceJndiDeclareGrailsPlugin {
  def version = '1.0.0'
  def grailsVersion = '2.0 > *'
  def dependsOn = [:]
  def pluginExcludes = [
    'grails-app/**'
  ]

  def title = 'Datasource JNDI declare'
  def author = 'Franjo Žilić'
  def authorEmail = 'fzilic@croz.net'
  def description = '''Declares used JNDI DataSource in web.xml - so if JNDI datasource is
used e.g. jndiName exists in dataSource configuration for the
examined environment, then it will be added to the web.xml.
The authentication type (res-auth) will always be Container, but that
is probably what you want if your''re using JNDI datasource.

Credits for original in-house plugin go to Zoran Regvart.'''

  // URL to the plugin's documentation
  def documentation = 'https://github.com/fzilic/grails-datasource-jndi-declare/blob/master/README.md'

  // License: one of 'APACHE', 'GPL2', 'GPL3'
  def license = "APACHE"

  // Details of company behind the plugin (if there is one)
  def organization = [name: "CROZ d.o.o.", url: "http://www.croz.net/"]

  // Any additional developers beyond the author specified above.
  def developers = [
    [name: "Zoran Regvart", email: "zregvart@croz.net"]
  ]

  // Location of the plugin's issue tracker.
  def issueManagement = [ system: "github", url: "https://github.com/fzilic/grails-datasource-jndi-declare/issues" ]

  // Online location of the plugin's browseable source code.
  def scm = [ url: 'https://github.com/fzilic/grails-datasource-jndi-declare' ]

  def doWithWebDescriptor = { xml ->
    def datasources = application.config.findAll { it.key =~ /^dataSource.*/ }

    UpdateWebDescriptor.with(xml, datasources)
  }
}
