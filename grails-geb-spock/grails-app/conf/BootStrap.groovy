import grails.geb.spock.Author
import grails.geb.spock.Book

class BootStrap {

    def init = { servletContext ->
      Author author = new Author(firstname:"Glen",lastname:"Smith").save(failOnError:true)
      Book book = new Book(title:"Grails in Action",author:author).save(failOnError:true)
    }
    def destroy = {

    }
}
