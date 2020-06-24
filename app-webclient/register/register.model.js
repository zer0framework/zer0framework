/**
 * @class Model
 *
 * Manages the data of the application.
 */
class RegisterModel {

    constructor() {}

    onInit() {}

    register(name, birthdate, job, username, password, email) {
        const personUser = {
            name: name,
            birthdate: birthdate,
            job: job,
            username: username,
            password: password,
            email: email
        }
        this.checkUsernameAvailability(personUser)
    }

    checkUsernameAvailability(personUser) {
        window.fetch('http://localhost:8080/checkUserAvailability/' + personUser.username, {
                method: 'GET',
            }).then(response => response.json())
            .then(json => {
                if (json === true) {
                    this.createPerson(personUser)
                }
            });
    }

    createPerson(personUser) {
        window.fetch('http://localhost:8080/register/person', {
            method: 'POST',
            body: JSON.stringify(personUser)
        }).then(response => {
            this.getPersonId(personUser)
            return response
        })
    }

    getPersonId(personUser) {
        window.fetch('http://localhost:8080/checkPersonId/' + personUser.name, {
                method: 'GET',
            }).then(response => response.text())
            .then(data => {
                personUser.personId = data
                this.createUser(personUser)
            });
    }

    createUser(personUser) {
        window.fetch('http://localhost:8080/register/user', {
            method: 'POST',
            body: JSON.stringify(personUser)
        }).then(response => {
            if (response.status === 201) {
                document.body.innerHTML = loginController.getView().getTemplate();
                loginController.getView().onInit();
            }
        })
    }
}