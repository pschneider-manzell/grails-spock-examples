package grails.geb.page

import geb.Page

/**
 * @author Peter Schneider-Manzell
 */
class IndexPage extends ScaffoldPage {

    static url = ""

    static at = {
        title ==~ /Welcome to examples how to use spock and geb/
    }

    static  content = {
        pageContentHeader(wait:true) { $("#page-body").find("h1")   }
    }
}
