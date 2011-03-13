package grails.geb.spock

class AuthorController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [authorInstanceList: Author.list(params), authorInstanceTotal: Author.count()]
    }

    def create = {
        def authorInstance = new Author()
        authorInstance.properties = params
        return [authorInstance: authorInstance]
    }

    def save = {
        def authorInstance = new Author(params)
        if (authorInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'author.label', default: 'Author'), authorInstance.id])}"
            redirect(action: "show", id: authorInstance.id)
        }
        else {
            render(view: "create", model: [authorInstance: authorInstance])
        }
    }

    def show = {
        def authorInstance = Author.get(params.id)
        if (!authorInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'author.label', default: 'Author'), params.id])}"
            redirect(action: "list")
        }
        else {
            [authorInstance: authorInstance]
        }
    }

    def edit = {
        def authorInstance = Author.get(params.id)
        if (!authorInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'author.label', default: 'Author'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [authorInstance: authorInstance]
        }
    }

    def update = {
        def authorInstance = Author.get(params.id)
        if (authorInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (authorInstance.version > version) {
                    
                    authorInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'author.label', default: 'Author')] as Object[], "Another user has updated this Author while you were editing")
                    render(view: "edit", model: [authorInstance: authorInstance])
                    return
                }
            }
            authorInstance.properties = params
            if (!authorInstance.hasErrors() && authorInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'author.label', default: 'Author'), authorInstance.id])}"
                redirect(action: "show", id: authorInstance.id)
            }
            else {
                render(view: "edit", model: [authorInstance: authorInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'author.label', default: 'Author'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def authorInstance = Author.get(params.id)
        if (authorInstance) {
            try {
                authorInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'author.label', default: 'Author'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'author.label', default: 'Author'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'author.label', default: 'Author'), params.id])}"
            redirect(action: "list")
        }
    }
}
