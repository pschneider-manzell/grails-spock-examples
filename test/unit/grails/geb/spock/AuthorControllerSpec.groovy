package grails.geb.spock

// Controller Specs MUST live in the same package as the controller to enable automatic controller lookup
class AuthorControllerSpec extends ExtendedControllerSpec {


  def 'index action'() {
    setup:
    mockLogging(AuthorController, true)

    when:
    controller.index()

    then:
    redirectArgs.action == "list"
  }



  def 'list action: 1 author'() {
    setup:
    mockLogging(AuthorController, true)
    mockDomain(Author, [authorInstance])

    expect:
    controller.list() == [authorInstanceList: [authorInstance], authorInstanceTotal: 1]

    where:
    authorInstance = new Author(firstname: "John", lastname: "Doe")
  }

  def 'list action: 2 authors and max = 1'() {
    setup:
    mockLogging(AuthorController, true)
    mockDomain(Author, [fistAuthorInstance, secondAuthorInstance])
    controller.params.max = 1

    expect:
    controller.list() == [authorInstanceList: [fistAuthorInstance], authorInstanceTotal: 2]

    where:
    fistAuthorInstance = new Author(firstname: "John1", lastname: "Doe1")
    secondAuthorInstance = new Author(firstname: "John2", lastname: "Doe2")
  }



  def "create action"() {
    setup:
    mockLogging(AuthorController, true)
    mockDomain(Author)
    controller.params.firstname = firstname
    controller.params.lastname = lastname

    when:
    def model = controller.create()

    then:
    model.authorInstance != null
    model.authorInstance.firstname == firstname
    model.authorInstance.lastname == lastname

    where:
    firstname = "John"
    lastname = "Doe"
  }

  def 'save action: valid author'() {
    setup:
    mockLogging(AuthorController, true)
    mockDomain(Author)
    mockI18N(AuthorController)
    controller.params.firstname = "John"
    controller.params.lastname = "Doe"

    when:
    controller.save()

    then:
    redirectArgs.action == "show"
    controller.flash.message != null

  }

  def 'save action: invalid author'() {
    setup:
    mockLogging(AuthorController, true)
    mockDomain(Author)
    mockForConstraintsTests(Author)
    mockI18N(AuthorController)
    controller.params.firstname = firstname
    controller.params.lastname = lastname

    when:
    controller.save()

    then:
    renderArgs.view == "create"
    renderArgs.model.authorInstance.firstname == firstname
    renderArgs.model.authorInstance.lastname == lastname

    where:
    firstname = "John"
    lastname = ""

  }

  def 'show action: existing author'() {
    setup:
    mockLogging(AuthorController, true)
    mockDomain(Author, [authorInstance])
    mockI18N(AuthorController)
    controller.params.id = authorInstance.id

    expect:
    controller.show() == [authorInstance: authorInstance]


    where:
    authorInstance = new Author(firstname: "John", lastname: "Doe")

  }

  def 'show action: not existing author'() {
    setup:
    mockLogging(AuthorController, true)
    mockDomain(Author)
    mockI18N(AuthorController)
    controller.params.id = 1L

    when:
    controller.show()

    then:
    redirectArgs.action == "list"
    controller.flash.message != null

  }


  def 'edit action: existing author'() {
    setup:
    mockLogging(AuthorController, true)
    mockDomain(Author, [authorInstance])
    mockI18N(AuthorController)
    controller.params.id = authorInstance.id

    expect:
    controller.edit() == [authorInstance: authorInstance]


    where:
    authorInstance = new Author(firstname: "John", lastname: "Doe")

  }

  def 'edit action: not existing author'() {
    setup:
    mockLogging(AuthorController, true)
    mockDomain(Author)
    mockI18N(AuthorController)
    controller.params.id = 1L

    when:
    controller.edit()

    then:
    redirectArgs.action == "list"
    controller.flash.message != null

  }

  def 'update action: valid author'() {
    setup:
    mockDomain(Author, [authorInstance])
    mockLogging(AuthorController, true)
    mockI18N(AuthorController)
    controller.params.firstname = "John changed"
    controller.params.lastname = "Doe changed"
    controller.params.version = authorInstance.version
    controller.params.id = authorInstance.id

    when:
    controller.update()

    then:
    redirectArgs.action == "show"
    controller.flash.message != null

    where:
    authorInstance = new Author(firstname: "John", lastname: "Doe", version: 1)

  }

  def 'update action: optimistic locking'() {
    setup:
    mockDomain(Author, [authorInstance])
    mockLogging(AuthorController, true)
    mockI18N(AuthorController)
    controller.params.firstname = "John changed"
    controller.params.lastname = "Doe changed"
    //Decrease version of edited object to enforce optimistic locking validation
    controller.params.version = (currentVersionInDB -1)
    controller.params.id = authorInstance.id

    when:
    controller.update()

    then:
    renderArgs.view == "edit"
    renderArgs.model.authorInstance == authorInstance
    renderArgs.model.authorInstance.hasErrors()

    where:
    currentVersionInDB = 2
    authorInstance = new Author(firstname: "John", lastname: "Doe", version: currentVersionInDB)
  }


}
