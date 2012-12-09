package grails.geb.spock.service

import grails.plugin.spock.UnitSpec
import grails.geb.spock.GuttenbergService
import grails.geb.spock.Author
import grails.geb.spock.Book

class GuttenbergServiceSpec extends UnitSpec {

  def "when creating thesis a new book is created"() {
    setup:
    mockLogging(GuttenbergService, true)
    def guttenbergService = new GuttenbergService()
    mockDomain(Author, [authorInstance])
    mockDomain(Book)


    when:
    guttenbergService.createThesis(authorInstance.id)

    then:
    Book.count() == 1

    where:
    authorInstance = new Author(firstname: "John", lastname: "Doe")
  }
  
  def "when creating thesis author is assigned to thesis"() {
    //2. Setup mocks
    setup:
    //Enable logging for GuttenbergService
    mockLogging(GuttenbergService, true)
    //Create GuttenbergServce
    def guttenbergService = new GuttenbergService()
    //Use the author object created in the "where" statement to create mocked domain objects.
    //In this step, the author instance get's the ID assigned
    mockDomain(Author, [authorInstance])
    //Mock domain class book (required by the guttenberg service)
    mockDomain(Book)

    //3. Call the method to test
    when:
    def thesis = guttenbergService.createThesis(authorInstance.id)

    then:
    //4. Make asserts on the result
    thesis != null
    thesis.author.id == authorInstance.id

    //1. Create a dummy author instance used for the test. This instance has NO id yet
    where:
    authorInstance = new Author(firstname: "John", lastname: "Doe")
  }

  def "when creating guttenberg thesis title is set"() {
    setup:
    mockLogging(GuttenbergService, true)
    def guttenbergService = new GuttenbergService()
    mockDomain(Author, [authorInstance])
    mockDomain(Book)


    when:
    def thesis = guttenbergService.createThesis(authorInstance.id)

    then:
    thesis != null
    thesis.title == "My copied thesis"

    where:
    authorInstance = new Author(firstname: "John", lastname: "Doe")
  }


}
