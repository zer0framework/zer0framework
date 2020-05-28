/**
 * @class Controller
 *
 * Links the user input and the view output.
 *
 * @param model
 * @param view
 */
class ContactController {

    constructor(model, view) {
        this.model = model
        this.view = view
    }

    onInit(param) {
        this.model.onInit(param);
        this.view.onInit(param);

        this.view.bindSubmitContact(this.handleAddContact)
    }

    getView() {
        return this.view;
    }

    handleAddContact = (surname, forname, telefone) => {
        this.model.addContact(surname, forname, telefone);
    }
}

const contactController = new ContactController(new ContactModel(), new ContactView());