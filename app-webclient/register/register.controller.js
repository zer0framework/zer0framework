/**
 * @class Controller
 *
 * Links the user input and the view output.
 *
 * @param model
 * @param view
 */
class RegisterController {

    constructor(model, view) {
        this.model = model
        this.view = view
    }

    onInit() {
        this.model.onInit();
        this.view.onInit();

        this.view.bindRegister(this.handleRegister);
    }

    getView() {
        return this.view;
    }

    handleRegister = (name, birthdate, job, username, password, email) => {
        this.model.register(name, birthdate, job, username, password, email)
    }

}

const registerController = new RegisterController(new RegisterModel(), new RegisterView());