/**
 * @class Model
 *
 * Manages the data of the application.
 */
class FilesModel {

    constructor() {}

    onInit() {}

    upload(file) {
        let formData = new FormData()

        formData.append('file', file)

        fetch(uploadUrl, {
                method: 'POST',
                body: formData,
                headers: { 'Authorization': localStorage.getItem('token') }
            })
            .then(() => { alert('Success') })
            .catch(() => { alert('Error') })
    }
}

const uploadUrl = 'http://localhost:8080/api/fileUpload'