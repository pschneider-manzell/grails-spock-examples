package grails.geb.spock

class Book {

  String title

  static belongsTo = [author: Author]

  static constraints = {
    title(maxSize: 20)
  }

  String toString(){
    "$title"
  }
}
