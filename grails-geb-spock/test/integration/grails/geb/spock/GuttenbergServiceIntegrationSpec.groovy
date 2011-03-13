package grails.geb.spock

import grails.plugin.spock.IntegrationSpec

/**
 * Peter Schneider-Manzell
 */
class GuttenbergServiceIntegrationSpec extends IntegrationSpec {

  def guttenbergService

  def "Create thesis for first author in DB"() {

    when:
    def thesis = guttenbergService.createThesis(authorId)

    then:
    thesis != null
    Book.countByAuthor(author) == 2

    where:
    authorId = 1L
    author = Author.get(authorId)
  }
}
