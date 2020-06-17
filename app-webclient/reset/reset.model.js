/**
 * @class Model
 *
 * Manages the data of the application.
 */
class ResetModel {

    constructor() {}

    onInit() {}

    resetPassword(resetEmail) {
        fetch('http://localhost:8080/resetPasswordRequest', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    email: resetEmail
                })
            })
            .then(response => {
                alert('Email send.')
                return response;
            })
    }

}