package grails.geb.spock.domain

import grails.plugin.spock.UnitSpec
import grails.geb.spock.Author

class AuthorSpec extends UnitSpec  {
  def "find author by firstname and lastname"() {
          setup:
          mockDomain(Author)

          when:
          new Author(firstname: firstname,lastname:lastname).save()

          then:
          Author.findByFirstnameAndLastname(firstname,lastname) != null

          where:
          firstname = "John"
          lastname = "Doe"
   }

   def "firstname not longer than 20 characters"() {
          setup:
          mockForConstraintsTests(Author)

          when:
          def author =new Author(firstname: firstname,lastname:lastname)
          author.validate()

          then:
          author.hasErrors()

          where:
          firstname = "123456789012345678901"
          lastname = "Doe"
   }

  def "lastname not longer than 20 characters"() {
          setup:
          mockForConstraintsTests(Author)

          when:
          def author =new Author(firstname: firstname,lastname:lastname)
          author.validate()

          then:
          author.hasErrors()

          where:
          firstname = "John"
          lastname = "123456789012345678901"
   }

}
