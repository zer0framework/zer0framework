/**
 * @class Controller
 *
 * Links the user input and the view output.
 *
 * @param model
 * @param view
 */
class LoginController {

    constructor(model, view) {
        this.model = model
        this.view = view
    }

    onInit() {
        this.model.onInit();
        this.view.onInit();

        this.view.bindLogin(this.handleLogin);
    }

    handleLogin = (username, password) => {
        this.model.authenticate(username, password);
    }

    getView() {
        return this.view;
    }

}

const loginController = new LoginController(new LoginModel(), new LoginView());