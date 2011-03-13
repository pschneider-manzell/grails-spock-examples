package grails.geb.spock

class GuttenbergService {

  static transactional = true

  Book createThesis(Long authorId) {
    Author authorInstance = Author.get(authorId)
    Book bookInstance = new Book(title: "My copied thesis", author: authorInstance).save()
    log.debug("Created thesis $bookInstance")
    return bookInstance
  }

}
