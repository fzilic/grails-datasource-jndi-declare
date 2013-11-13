import org.grails.plugins.datasourcejndideclare.UpdateWebDescriptor

class DatasourceJndiDeclareGrailsPlugin {
  def version = '1.0.0'
  def grailsVersion = '2.0 > *'

  def title = 'Datasource JNDI declare'
  def author = 'Franjo Žilić'
  def authorEmail = 'fzilic@croz.net'
  def description = '''Declares used JNDI DataSource in web.xml - so if JNDI datasource is
used e.g. jndiName exists in dataSource configuration for the
examined environment, then it will be added to the web.xml.
The authentication type (res-auth) will always be Container, but that
is probably what you want if your''re using JNDI datasource.

Credits for original in-house plugin go to Zoran Regvart.'''

  def documentation = 'https://github.com/fzilic/grails-datasource-jndi-declare/blob/master/README.md'

  def license = "APACHE"
  def organization = [name: "CROZ d.o.o.", url: "http://www.croz.net/"]
  def developers = [
    [name: "Zoran Regvart", email: "zregvart@croz.net"]
  ]
  def issueManagement = [ system: "github", url: "https://github.com/fzilic/grails-datasource-jndi-declare/issues" ]
  def scm = [ url: 'https://github.com/fzilic/grails-datasource-jndi-declare' ]

  def doWithWebDescriptor = { xml ->
    def datasources = application.config.findAll { it.key =~ /^dataSource.*/ }

    UpdateWebDescriptor.with(xml, datasources)
  }
}
