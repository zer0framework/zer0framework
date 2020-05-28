/**
 * @class Model
 *
 * Manages the data of the application.
 */
class ContactModel {

    constructor() {
    }

    onInit() { }

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
        window.fetch('http://localhost:8080/api/contacts', {
            method: 'POST',
            headers: { 'Authorization': localStorage.getItem('token') },
            body: JSON.stringify(contact)
        }).then(response => {
            return response;
        })
    }
}
