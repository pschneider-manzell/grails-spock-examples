package grails.geb.page

import geb.Page

/**
 * Source: https://github.com/geb/geb-example-grails
 */
class ScaffoldPage extends Page {
    static content = {
        heading { $("h1") }
        message { $("div.message").text() }
    }
}
