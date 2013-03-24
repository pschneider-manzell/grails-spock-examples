package grails.geb.spock.domain

import grails.plugin.spock.UnitSpec
import grails.geb.spock.Author
import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(Author)
class AuthorSpec extends Specification {
  def "find author by firstname and lastname"() {
    setup:
    mockDomain(Author)

    when:
    new Author(firstname: firstname, lastname: lastname).save()

    then:
    Author.findByFirstnameAndLastname(firstname, lastname) != null

    where:
    firstname = "John"
    lastname = "Doe"
  }

  def "firstname constraints"() {
    setup:
    mockForConstraintsTests(Author)

    when:
    def author = new Author(firstname: firstname, lastname: "Doe")
    author.validate()

    then:
    author.hasErrors() == !valid

    where:

    firstname | valid
    "123456789012345678901" | false //Firstname must not have more than 20 characters 
    "12345678901234567890" | true
    "" | false //Firstname must not be blank

  }

  def "lastname constraints"() {
    setup:
    mockForConstraintsTests(Author)

    when:
    def author = new Author(firstname: "John", lastname: lastname)
    author.validate()

    then:
    author.hasErrors() == !valid

    where:
    lastname | valid
    "123456789012345678901" | false
    "12345678901234567890" | true
    "" | false
  }

}
