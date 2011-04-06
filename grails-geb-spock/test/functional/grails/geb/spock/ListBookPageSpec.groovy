package grails.geb.spock

import grails.plugin.geb.GebSpec

/**
 * Peter Schneider-Manzell
 */
class ListBookPageSpec extends GebSpec {

  def "redirect to list books page"() {
    when:
    go "book/"
    then:
    def firstHeader = $("h1")
    firstHeader.text() == "Book List"
  }

  def "titles of table header"() {
    when:
    go "book/list"
    then:
    //First header is sortable, html: <th...><a...>Id</a></th>....
    def firstTableHeader = $("th",0).find("a")
    firstTableHeader.text() == "Id"

    //Second header is also sortable, but you can obmit the lookup for the <a...> tag, by using "text()", you get the textual content
    def secondTableHeader = $("th",1)
    secondTableHeader.text() == "Title"

    def thirdTableHeader = $("th",2)
    thirdTableHeader.text() == "Author"
  }

  def "sorting of books by id"() {
    when:
    go "book/list"
    //Click first time (books should then sorted by id ascending)
    def firstTableHeader = $("th",0).find("a")
    firstTableHeader.click()

    //Click second time to sort books by id descending
    firstTableHeader = $("th",0).find("a")
    firstTableHeader.click()


    then:
    def firstBookId = $("td").find("a")
    firstBookId.text() != "1"
  }

  def "sorting of books by title"() {
    when:
    go "book/list"
    //Click first time (books should then be sorted by title ascending)
    def firstTableHeader = $("th",1).find("a")
    firstTableHeader.click()



    then:
    def firstBookTitle = $("td",1)
    firstBookTitle.text() == "Clean Code"
  }

  def "click on home link"() {
    when:
    go "book/list"
    //Click first time (books should then be sorted by title ascending)
    def homeLink = $("a",text:"Home")
    homeLink.click()

    then:
    def pageTitle = $("title")
    pageTitle.text() == "Welcome to Grails"
  }

  def "click on create link"() {
    when:
    go "book/list"
    //Click first time (books should then be sorted by title ascending)
    def homeLink = $("a",text:"New Book")
    homeLink.click()

    then:
    def pageTitle = $("title")
    pageTitle.text() == "Create Book"
  }
}
