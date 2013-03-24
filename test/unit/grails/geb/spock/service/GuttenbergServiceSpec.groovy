package grails.geb.spock.service

import grails.geb.spock.GuttenbergService
import grails.geb.spock.Author
import grails.geb.spock.Book
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(GuttenbergService)
@Mock([Author,Book])
class GuttenbergServiceSpec extends Specification {

  def "when creating thesis a new book is created"() {

    when:
    authorInstance.save()
    service.createThesis(authorInstance.id)

    then:
    Book.count() == 1

    where:
    authorInstance = new Author(firstname: "John", lastname: "Doe")
  }
  
  def "when creating thesis author is assigned to thesis"() {

    //3. Call the method to test
    when:
    authorInstance.save()
    def thesis = service.createThesis(authorInstance.id)

    then:
    //4. Make asserts on the result
    thesis != null
    thesis.author.id == authorInstance.id

    //1. Create a dummy author instance used for the test. This instance has NO id yet
    where:
    authorInstance = new Author(firstname: "John", lastname: "Doe")
  }

  def "when creating guttenberg thesis title is set"() {


    when:
    authorInstance.save()
    def thesis = service.createThesis(authorInstance.id)

    then:
    thesis != null
    thesis.title == "My copied thesis"

    where:
    authorInstance = new Author(firstname: "John", lastname: "Doe")
  }


}
