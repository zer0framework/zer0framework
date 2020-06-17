/**
 * @class Controller
 *
 * Links the user input and the view output.
 *
 * @param model
 * @param view
 */
class ResetController {

    constructor(model, view) {
        this.model = model
        this.view = view
    }

    onInit() {
        this.model.onInit();
        this.view.onInit();

        this.view.bindReset(this.handleReset);
    }

    getView() {
        return this.view;
    }

    handleReset = (resetEmail) => {
        this.model.resetPassword(resetEmail);
    }

}

const resetController = new ResetController(new ResetModel(), new ResetView());