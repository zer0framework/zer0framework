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
    }

    getView() {
        return this.view;
    }
}

const contactController = new ContactController(new ContactModel(), new ContactView());