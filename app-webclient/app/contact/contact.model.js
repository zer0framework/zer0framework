/**
 * @class Model
 *
 * Manages the data of the application.
 */
class ContactModel {

    constructor() {}

    onInit() {
        this.getData();
    }

    getData() {
        window.fetch(url, {
                method: 'GET',
                headers: { 'Authorization': localStorage.getItem('token') }
            })
            .then(response => response.json())
            .then(json => {
                this.onContactListChanged(json);
            });

    }

    bindContactListChanged(callback) {
        this.onContactListChanged = callback;
    }

    commit(contact) {
        this.create(contact);
    }

    addContact(forname, surname, telefone) {
        const newContact = {
            userId: localStorage.getItem('userId'),
            forename: forname,
            surname: surname,
            telefone: telefone,
        };

        this.commit(newContact);
    }

    create(contact) {
        window.fetch(url, {
            method: 'POST',
            headers: { 'Authorization': localStorage.getItem('token') },
            body: JSON.stringify(contact)
        }).then(response => {
            this.getData();
            return response;
        })
    }

}

let url = 'http://localhost:8080/api/contacts'