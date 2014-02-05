package grails.geb.page

import grails.geb.modules.BookTableRow

/**
 * @author Peter Schneider-Manzell
 */
class ListBooksPage extends ScaffoldPage {

    static url = "book/index"

    static at = {
        title ==~  /Book List/
    }

    static content = {
        bookResult { index ->
            module BookTableRow, $("table tbody").find("tr", index)
        }


        headerTitle { $("table tr").find(" th",0) }
        headerAuthor {  $("table tr").find(" th",1) }

        homeLink { $("a",text:"Home")}
        createBookLink { $("a",text:"New Book")}
    }
}
