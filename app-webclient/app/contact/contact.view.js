/**
 * @class View
 *
 * Visual representation of the model.
 */
class ContactView {

  form;

  constructor() { }

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
      `;
  }

  onInit() {
    this.form = this.getElement('#form');
    this.forename = this.getElement('#forename');
    this.surname = this.getElement('#surname');
    this.telefone = this.getElement('#telefone');

    this._temporaryPostText = '';
  }

  getElement(selector) {
    const element = document.querySelector(selector);

    return element
  }

  bindSubmitContact(handler) {
    document.getElementById('submit').addEventListener('click', event => {
      event.preventDefault();
      handler(this.forename.value, this.surname.value, this.telefone.value);
    });
  }
}