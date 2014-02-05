package grails.geb.spock

import geb.spock.GebReportingSpec
import grails.geb.page.CreateBookPage
import grails.geb.page.ShowBookPage

/**
 * Peter Schneider-Manzell
 */
class CreateBookPageSpec extends GebReportingSpec {

    def "open create book page"() {
        when:
        to CreateBookPage

        then:
        at CreateBookPage
    }

    def "try to create book without title"() {

        given:
        CreateBookPage createBookPage = to CreateBookPage

        when:
        createBookPage.createButton.click()

        then:
        //Scaffolding adds an attribute "required" to the textfield, which prevents submitting the form without setting a value
        at CreateBookPage
    }

    def "try to create book with long title"() {
        given:
        CreateBookPage createBookPage = to CreateBookPage

        when:
        createBookPage.inputTtitle.value("123456789012345678901")

        then:
        //Scaffolding adds an attribute "maxlength" to the textfield, which prevents a value longer than 20 characters
        createBookPage.inputTtitle.value() == "12345678901234567890"
    }

    def "create book"() {
        given:
        CreateBookPage createBookPage = to CreateBookPage

        when:
        createBookPage.inputTtitle.value("Nice title")
        createBookPage.createButton.click()

        then:
        at ShowBookPage
        message == "Book 4 created"
    }

}
