package grails.geb.page


/**
 * @author Peter Schneider-Manzell
 */
class ShowBookPage extends ScaffoldPage {

    static url = "book/"

    static at = {
        title ==~  /Show Book/
    }


}
