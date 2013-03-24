package grails.geb.spock.domain

import grails.geb.spock.Book
import grails.geb.spock.Author
import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(Book)
class BookSpec extends Specification   {
    def "find book by title"() {
          setup:
          mockDomain(Book)
          mockDomain(Author)

          when:
          new Book(title: title,author:new Author(firstname:"John",lastname:"Doe").save()).save()

          then:
          Book.findByTitle(title) != null

          where:
           title = "Nice book"
   }

  def "title not longer than 20 characters"() {
          setup:
          mockForConstraintsTests(Book)
          mockDomain(Author)

          when:
          def book =new Book(title:title, author:new Author(firstname:"John",lastname:"Doe").save())
          book.validate()

          then:
          book.errors.hasFieldErrors("title")

          where:
          title="123456789012345678901"
   }

  def "title not blank"() {
          setup:
          mockForConstraintsTests(Book)
          mockDomain(Author)

          when:
          def book =new Book(title:title, author:new Author(firstname:"John",lastname:"Doe").save())
          book.validate()

          then:
          book.errors.hasFieldErrors("title")

          where:
          title=""
   }

  def "author not blank"() {
          setup:
          mockForConstraintsTests(Book)
          mockDomain(Author)

          when:
          def book =new Book(title:"nice title", author:null)
          book.validate()

          then:
          book.errors.hasFieldErrors("author")

   }

   
}
