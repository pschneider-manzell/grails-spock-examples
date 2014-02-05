package grails.geb.modules

/**
 * @author Peter Schneider-Manzell
 */
class BookTableRow extends geb.Module {

    static content = {
        cell { i -> $("td",i) }
        title { cell(0) }
        author { cell(1) }
    }
}
