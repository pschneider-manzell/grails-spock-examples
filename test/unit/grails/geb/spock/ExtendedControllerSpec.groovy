package grails.geb.spock

import geb.spock.GebReportingSpec

/**
 * Peter Schneider-Manzell
 */
abstract class ExtendedControllerSpec extends GebReportingSpec {

  //Mocking i18n, see http://jira.codehaus.org/browse/GRAILS-5926 for more details



  def mockI18N = { controller ->
    controller.metaClass.message = { Map map ->
      return "I18N message ($map)"

    }
  }
}
