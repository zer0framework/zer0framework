/**
 * @class Controller
 *
 * Links the user input and the view output.
 *
 * @param model
 * @param view
 */
class HomepageController {

    constructor(model, view) {
        this.model = model
        this.view = view
    }

    onInit() {
        this.model.onInit();
        this.view.onInit();
    }

    getView() {
        return this.view;
    }

}

const homepageController = new HomepageController(new HomepageModel(), new HomepageView());