/**
 * @class Model
 *
 * Manages the data of the application.
 */
class RegisterModel {

    constructor() {}

    onInit() {}

    register(name, birthdate, job, username, password, email) {
        const newPerson = {
            name: name,
            birthdate: birthdate,
            job: job,
        };
        const newUser = {
            username: username,
            password: password,
            email: email
        }
        this.checkUsernameAvailability(newPerson, newUser)
    }

    checkUsernameAvailability(newPerson, newUser) {
        window.fetch('http://localhost:8080/checkUser/' + newUser.username, {
                method: 'GET',
            }).then(response => response.json())
            .then(json => {
                if (json === true) {
                    this.createPerson(newPerson, newUser)
                }
            });
    }

    createPerson(person, user) {
        window.fetch('http://localhost:8080/api/person', {
            method: 'POST',
            body: JSON.stringify(person)
        }).then(response => {
            this.getPersonId(person, user)
            return response
        })
    }

    getPersonId(person, user) {
        window.fetch('http://localhost:8080/api/person/' + person.name, {
                method: 'GET',
            }).then(response => response.text())
            .then(data => {
                user.personId = (JSON.parse(data).id)
                this.createUser(user)
            });
    }

    createUser(user) {
        window.fetch('http://localhost:8080/api/users', {
            method: 'POST',
            body: JSON.stringify(user)
        }).then(response => {
            return response
        })
    }
}