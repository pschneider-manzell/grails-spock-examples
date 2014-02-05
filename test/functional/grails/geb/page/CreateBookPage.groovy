package grails.geb.page

import geb.Page

/**
 * @author Peter Schneider-Manzell
 */
class CreateBookPage extends ScaffoldPage {

    static url = "book/create"
    static at = { title ==~  /Create Book/ }


    static content = {
        createButton(wait:true) {
            $("form").find("input",name:"create")
        }
        inputTtitle(wait:true) {
            $("input",name:"title")
        }
    }

}
