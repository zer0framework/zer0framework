/**
 * @class Controller
 *
 * Links the user input and the view output.
 *
 * @param model
 * @param view
 */
class FilesController {

    constructor(model, view) {
        this.model = model
        this.view = view
    }

    onInit() {
        this.model.onInit();
        this.view.onInit();

        this.view.bindFile();

    }

    handleFiles(files) {
        let confirm = window.confirm('Do you want to upload the files?')

        if (confirm === true) {
            for (var i = 0; i < files.length; i++) {
                this.model.upload(files[i])
            }
        }
    }

    getView() {
        return this.view;
    }

}

const filesController = new FilesController(new FilesModel(), new FilesView())