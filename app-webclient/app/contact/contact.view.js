/**
 * @class View
 *
 * Visual representation of the model.
 */
class ContactView {

    constructor() {}

    getTemplate() {
        return `
    <form id="contactForm">
    <label for="contactForename" id="forenameLabel">Forename:</label>
    <input type="text" id="contactForename" value=""></input>
    <label for="contactSurname" lid="surnameLabel">Surname:</label>
    <input type="text" id="contactSurname" value=""></input>
    <label for="contactTelefone" id="telefoneLabel">Telefone:</label>
    <input type="text" id="contactTelefone" value=""></input>
    <br></br>
    <Button id="submit">Submit</Button>
    </form>
    
    <table id="contacts" style="width:100%">
    <tr>
    <th>Forename</th>
    <th>Surname</th>
    <th>Telefone</th>
    <th id="clmButtons"></th>
    </tr>
      `;
    }

    onInit() {
        this.contactForm = this.getElement('#contactForm');
        this.contactForename = this.getElement('#contactForename');
        this.contactSurname = this.getElement('#contactSurname');
        this.contactTelefone = this.getElement('#contactTelefone');
        this.contacts = this.getElement('#contacts')

    }

    resetInput() {
        this.contactForename.value = '';
        this.contactSurname.value = '';
        this.contactTelefone.value = '';
    }

    createElement(tag, className) {
        const element = document.createElement(tag);

        if (className) {
            element.classList.add(className);
        }

        return element;
    }

    getElement(selector) {
        const element = document.querySelector(selector);

        return element
    }

    displayContacts(contacts) {
        var table = document.getElementById("contacts");
        var rowCount = table.rows.length;
        for (var x = rowCount - 1; x > 0; x--) {
            table.deleteRow(x);
        }
        if (contacts !== undefined && contacts !== null) {
            if (contacts.forEach !== undefined) {
                contacts.forEach(contact => {
                    const tr = this.createElement('tr');

                    const tdForename = this.createElement('td');
                    tdForename.textContent = contact.forename;
                    tdForename.contentEditable = "true";
                    tdForename.setAttribute("contenteditable", "true");

                    const tdSurname = this.createElement('td');
                    tdSurname.textContent = contact.surname;
                    tdSurname.contentEditable = "true";
                    tdSurname.setAttribute("contenteditable", "true");

                    const tdTelefone = this.createElement('td');
                    tdTelefone.textContent = contact.telefone;
                    tdTelefone.contentEditable = "true";
                    tdTelefone.setAttribute("contenteditable", "true");

                    tr.append(tdForename);
                    tr.append(tdSurname);
                    tr.append(tdTelefone);

                    const contactDelete = this.createElement('button');
                    contactDelete.textContent = 'Delete';
                    contactDelete.className = 'contactDelete'
                    const contactEdit = this.createElement('button');
                    contactEdit.textContent = 'Edit';
                    contactEdit.className = 'contactEdit'

                    contactEdit.addEventListener('click', function() {
                        contactController.handleEditContact(contact.id, tdForename.textContent,
                            tdSurname.textContent, tdTelefone.textContent)
                    });

                    contactDelete.addEventListener('click', function() {
                        contactController.handleDeleteContact(contact.id)
                    });

                    tr.append(contactEdit, contactDelete);

                    this.contacts.append(tr);
                });
            }
        }
    }

    bindSubmitContact(handler) {
        document.getElementById('submit').addEventListener('click', event => {
            event.preventDefault();
            handler(this.contactForename.value, this.contactSurname.value, this.contactTelefone.value);
            this.resetInput();
        });
    }

}