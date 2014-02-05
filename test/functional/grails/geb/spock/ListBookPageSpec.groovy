package grails.geb.spock

import geb.spock.GebReportingSpec
import grails.geb.page.CreateBookPage
import grails.geb.page.IndexPage
import grails.geb.page.ListBooksPage

/**
 * Peter Schneider-Manzell
 */
class ListBookPageSpec extends GebReportingSpec {

  def "redirect to list books page"() {
    when:
    to ListBooksPage

    then:
    at ListBooksPage
  }

  def "titles of table header"() {
    when:
    ListBooksPage listBooksPage  = to ListBooksPage
    then:
    //First header is sortable, html: <th...><a...>Id</a></th>....
    listBooksPage.headerTitle.text() == "Title"

    //Second header is also sortable, but you can omit the lookup for the <a...> tag, by using "text()", you get the textual content
     listBooksPage.headerAuthor.text() == "Author"
  }

  def "sorting of books by title"() {
    when:
    ListBooksPage listBooksPage  = to ListBooksPage
    //Click first time (books should then sorted by title ascending)
    listBooksPage.headerTitle.click()

    //Click second time to sort books by title descending
      listBooksPage.headerTitle.click()


    then:
    listBooksPage.bookResult(0).title.text() == "Grails in Action"
  }


  def "click on home link"() {
    when:
    ListBooksPage listBooksPage  = to ListBooksPage
    //Click first time (books should then be sorted by title ascending)
    listBooksPage.homeLink.click()

    then:
    at IndexPage
  }

  def "click on create link"() {
    when:
    ListBooksPage listBooksPage  = to ListBooksPage
    listBooksPage.createBookLink.click()

    then:
    at CreateBookPage
  }
}
