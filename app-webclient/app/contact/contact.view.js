/**
 * @class View
 *
 * Visual representation of the model.
 */
class ContactView {

    constructor() {}

    getTemplate() {
        return `
    <form id="form">
    <label for="forename" id="forenameLabel">Forename:</label>
    <input type="text" id="forename" value=""></input>
    <label for="surname" lid="surnameLabel">Surname:</label>
    <input type="text" id="surname" value=""></input>
    <label for="telefone" id="telefoneLabel">Telefone:</label>
    <input type="text" id="telefone" value=""></input>
    <br></br>
    <Button id="submit">Submit</Button>
    </form>

    <table id="contacts" style="width:100%">
    <tr>
    <th>Forename</th>
    <th>Surname</th>
    <th>Telefone</th>
    </tr>
      `;
    }

    onInit() {
        this.form = this.getElement('#form');
        this.forename = this.getElement('#forename');
        this.surname = this.getElement('#surname');
        this.telefone = this.getElement('#telefone');
        this.contacts = this.getElement('#contacts')
    }

    resetInput() {
        this.forename.value = '';
        this.surname.value = '';
        this.telefone.value = '';
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
                    const tdSurname = this.createElement('td');
                    tdSurname.textContent = contact.surname;
                    const tdTelefone = this.createElement('td');
                    tdTelefone.textContent = contact.telefone;

                    tr.append(tdForename);
                    tr.append(tdSurname);
                    tr.append(tdTelefone);

                    this.contacts.append(tr);
                });
            }
        }
    }

    bindSubmitContact(handler) {
        document.getElementById('submit').addEventListener('click', event => {
            event.preventDefault();
            handler(this.forename.value, this.surname.value, this.telefone.value);
            this.resetInput();
        });
    }
}