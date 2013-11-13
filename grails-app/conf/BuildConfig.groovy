grails.project.dependency.resolution = {
  inherits("global")

  repositories {
    grailsPlugins();
    grailsHome();
    grailsCentral();
    mavenCentral();
    mavenLocal();
  }

  plugins {
    build(':release:2.2.1', ':rest-client-builder:1.0.3') {
       export = false
    }
  }
}

