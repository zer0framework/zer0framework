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
        window.fetch(contactUrl + '/?userId=' + localStorage.getItem('userId'), {
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

    commit(contact, removal) {
        if (removal == true) {
            this.delete(contact)
        } else if (contact.contactId) {
            this.edit(contact);
        } else {
            this.create(contact);
        }
        ''
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

    deleteContact(id) {
        const deleteContact = {
            id: id,
        };

        this.commit(deleteContact, true);
    }

    editContact(id, forname, surname, telefone) {
        const editContact = {
            userId: localStorage.getItem('userId'),
            contactId: id,
            forename: forname,
            surname: surname,
            telefone: telefone,
        }
        this.commit(editContact);
    }

    create(contact) {
        window.fetch(contactUrl, {
            method: 'POST',
            headers: { 'Authorization': localStorage.getItem('token') },
            body: JSON.stringify(contact)
        }).then(response => {
            this.getData();
            return response;
        })
    }

    edit(contact) {
        window.fetch(contactUrl, {
            method: 'PUT',
            headers: { 'Authorization': localStorage.getItem('token') },
            body: JSON.stringify(contact)
        }).then(response => {
            this.getData();
            return response;
        })
    }

    delete(contact) {
        window.fetch(contactUrl + '/' + contact.id, {
            method: 'DELETE',
            headers: { 'Authorization': localStorage.getItem('token') },
            body: JSON.stringify(contact)
        }).then(response => {
            this.getData();
            return response;
        })
    }

}

let contactUrl = 'http://localhost:8080/api/contacts'