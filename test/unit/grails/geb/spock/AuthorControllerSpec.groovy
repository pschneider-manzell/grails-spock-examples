package grails.geb.spock

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(AuthorController)
@Mock([Author])
class AuthorControllerSpec extends Specification {


    def 'index action'() {

        when:
        controller.index()

        then:
        response.redirectUrl.endsWith "list"
    }



    def 'list action: 1 author'() {
        setup:
        authorInstance.save()

        expect:
        controller.list() == [authorInstanceList: [authorInstance], authorInstanceTotal: 1]

        where:
        authorInstance = new Author(firstname: "John", lastname: "Doe")
    }

    def 'list action: 2 authors and max = 1'() {
        setup:
        fistAuthorInstance.save()
        secondAuthorInstance.save()
        controller.params.max = 1

        expect:
        controller.list() == [authorInstanceList: [fistAuthorInstance], authorInstanceTotal: 2]

        where:
        fistAuthorInstance = new Author(firstname: "John1", lastname: "Doe1")
        secondAuthorInstance = new Author(firstname: "John2", lastname: "Doe2")
    }



    def "create action"() {
        setup:
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
        controller.params.firstname = "John"
        controller.params.lastname = "Doe"

        when:
        controller.save()

        then:
        response.redirectUrl.endsWith "show/1"
        controller.flash.message != null

    }

    def 'save action: invalid author'() {
        setup:
        controller.params.firstname = firstname
        controller.params.lastname = lastname

        when:
        controller.save()

        then:
        view.endsWith "create"
        model.authorInstance.firstname == firstname
        model.authorInstance.lastname == lastname

        where:
        firstname = "John"
        lastname = ""

    }

    def 'show action: existing author'() {
        setup:
        authorInstance.save()
        controller.params.id = authorInstance.id

        expect:
        controller.show()  == [authorInstance: authorInstance]

        where:
        authorInstance = new Author(firstname: "John", lastname: "Doe")

    }

    def 'show action: not existing author'() {
        setup:
        controller.params.id = 1L

        when:
        controller.show()

        then:
        response.redirectUrl.endsWith"list"
        controller.flash.message != null

    }


    def 'edit action: existing author'() {
        setup:
        authorInstance.save()
        controller.params.id = authorInstance.id

        expect:
        controller.edit()  == [authorInstance: authorInstance]

        where:
        authorInstance = new Author(firstname: "John", lastname: "Doe")

    }

    def 'edit action: not existing author'() {
        setup:
        controller.params.id = 1L

        when:
        controller.edit()

        then:
        response.redirectUrl.endsWith "list"
        controller.flash.message != null

    }

    def 'update action: valid author'() {
        setup:
        authorInstance.save()
        controller.params.firstname = "John changed"
        controller.params.lastname = "Doe changed"
        controller.params.version = authorInstance.version
        controller.params.id = authorInstance.id

        when:
        controller.update()

        then:
        response.redirectUrl.endsWith "show/1"
        controller.flash.message != null

        where:
        authorInstance = new Author(firstname: "John", lastname: "Doe", version: 1)

    }

    /**
     * Testing optimistic locking currently not possible due to this bug: http://jira.grails.org/browse/GRAILS-9862
    def 'update action: optimistic locking'() {
        setup:
        authorInstance = authorInstance.save()
        assert  authorInstance.id


        when:
        controller.params.firstname = "John changed"
        controller.params.lastname = "Doe changed"
        //Decrease version of edited object to enforce optimistic locking validation
        controller.params.version = authorInstance.version -1
        controller.params.id = authorInstance.id
        controller.update()

        then:
        view == "edit"
        model.authorInstance == authorInstance
        model.authorInstance.hasErrors()

        where:
        authorInstance = new Author(firstname: "John", lastname: "Doe")
    }
    **/


}
