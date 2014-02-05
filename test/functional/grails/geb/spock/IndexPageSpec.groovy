package grails.geb.spock

import geb.spock.GebReportingSpec
import grails.geb.page.IndexPage

/**
 * Peter Schneider-Manzell
 */
class IndexPageSpec extends GebReportingSpec {


  def "test header in page content section of index page"() {

        when:
        IndexPage indexPage = to IndexPage

        then:
        at IndexPage
        indexPage.pageContentHeader.text() == "Welcome to examples how to use spock and geb"
    }
}
