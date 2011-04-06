package grails.geb.spock

import grails.plugin.geb.GebSpec

/**
 * Peter Schneider-Manzell
 */
class CreateBookPageSpec extends GebSpec {

  def "open create book page"() {
    when:
    go "book/create"
    
    then:
    def pageTitle = $("title")
    pageTitle.text() == "Create Book"
  }

  def "try to create book without title"() {
    when:
    go "book/create"
    $("form").find("input",name:"create").click()
    
    then:
    def pageTitle = $("title")
    pageTitle.text() == "Create Book"

    $("li").text() == "Property [title] of class [class grails.geb.spock.Book] cannot be blank"
  }

  def "try to create book with long title"() {
    when:
    go "book/create"
    $("input",name:"title").value("123456789012345678901")    

    then:
    //Scaffolding adds an attribute "maxlength" to the textfield, which prevents a value longer than 20 characters
    $("input",name:"title").value() == "12345678901234567890" 
  }

  def "create book"(){

    when:
    go "book/create"
    $("input",name:"title").value("Nice title")
    $("form").find("input",name:"create").click()

    then:
    $("title").text() == "Show Book"
    $("div",class:"message").text()=="Book 4 created"
  }

}
