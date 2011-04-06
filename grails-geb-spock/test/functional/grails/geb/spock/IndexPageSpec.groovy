package grails.geb.spock

import grails.plugin.geb.GebSpec

/**
 * Peter Schneider-Manzell
 */
class IndexPageSpec extends GebSpec {


  def "test header in page content section of index page"() {
        when:
        go ""
        then:
        def pageContentDiv = $("div",id:"pageBody")
        def firstHeader = pageContentDiv.find("h1")
        firstHeader.text() == "Welcome to examples how to use spock and geb"
    }
}
