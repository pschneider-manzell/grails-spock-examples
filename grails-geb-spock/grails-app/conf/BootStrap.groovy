import grails.geb.spock.Author
import grails.geb.spock.Book

class BootStrap {

    def init = { servletContext ->
      Author glenSmith = new Author(firstname:"Glen",lastname:"Smith").save(failOnError:true)
      Book grailsInAction = new Book(title:"Grails in Action",author:glenSmith).save(failOnError:true)

      Author robertMartin = new Author(firstname:"Robert",lastname:"Martin").save(failOnError:true)
      Book cleanCode = new Book(title:"Clean Code",author:robertMartin).save(failOnError:true)
    }
    def destroy = {

    }
}
