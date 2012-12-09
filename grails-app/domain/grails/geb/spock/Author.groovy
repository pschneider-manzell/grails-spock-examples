package grails.geb.spock

class Author {

    String firstname
    String lastname

    static hasMany = [books:Book]

    static constraints = {
      firstname(blank:false,maxSize:20)
      lastname(blank:false,maxSize:20)
    }

    String toString(){
      "$firstname $lastname ($id)"
    }
}
