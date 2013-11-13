import org.grails.plugins.datasourcejndideclare.UpdateWebDescriptor

class DatasourceJndiDeclareGrailsPlugin {
  def version = '1.0.0'
  def grailsVersion = '2.0 > *'
  def dependsOn = [:]
  def pluginExcludes = [
    'grails-app/**'
  ]

  def title = 'Declares used JNDI DataSource in web.xml.'
  def author = 'Zoran Regvart'
  def authorEmail = 'zregvart@gmail.com'

  def description = '''Declares used JNDI DataSource in web.xml - so if JNDI datasource is
used e.g. jndiName exists in dataSource configuration for the
examined environment, then it will be added to the web.xml.
The authentication type (res-auth) will always be Container, but that
is probably what you want if your''re using JNDI datasource.'''

  def documentation = 'https://uranus.croz.net/trac/research/wiki/Grails/plugins/datasource-jndi-declare'

  def license = "APACHE"

  def organization = [name: "CROZ d.o.o.", url: "http://www.croz.net/"]

  def developers = [
    [name: "Franjo Žilić", email: "fzilic@croz.net"],
    [name: "Zoran Regvart", email: "zregvart@croz.net"]
  ]

  def doWithWebDescriptor = { xml ->
    def datasources = application.config.findAll { it.key =~ /^dataSource.*/ }

    UpdateWebDescriptor.with(xml, datasources)
  }
}
