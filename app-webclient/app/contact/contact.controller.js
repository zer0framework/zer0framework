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

    onInit() {
        this.model.onInit();
        this.view.onInit();

        this.model.bindContactListChanged(this.onContactListChanged)
        this.view.bindSubmitContact(this.handleAddContact)

        this.onContactListChanged(this.model.contacts)
    }

    onContactListChanged = contacts => {
        if (contacts != undefined) {
            this.view.displayContacts(contacts);
        }
    }

    getView() {
        return this.view;
    }

    handleAddContact = (surname, forname, telefone) => {
        this.model.addContact(surname, forname, telefone);
    }
}

const contactController = new ContactController(new ContactModel(), new ContactView());