/**
 * @class View
 *
 * Visual representation of the model.
 */
class RegisterView {
    constructor() {}

    getTemplate() {
        return `
    <div class="registerPage" id="registerPage">    
        <div class="registerDiv" id="registerDiv">
            <label for="name">Name:</label>
            <input type="text" id="name" name="name" value=""></input><br>
            <label for="birthdate">Birthdate:</label>
            <input type="date" id="birthdate" name="birthdate" value=""></input><br> 
            <label for="job">Job:</label>
            <input type="text" id="job" name="job" value=""></input><br>    
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" value=""></input><br>    
            <label for="registerPassword">Password:</label>
            <input type="password" id="registerPassword" name="registerPassword" value=""></input><br>
            <label for="registerPasswordCnf">Password confirmation:</label>
            <input type="password" id="registerPasswordCnf" name="registerPasswordCnf" value=""></input><br>
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" value=""></input><br>
            <button id="registerBtn">Register</button>
        </div>
    </div>
        `;
    }

    onInit() {
        this.name = this.getElement('#name');
        this.birthdate = this.getElement('#birthdate');
        this.job = this.getElement('#job');
        this.username = this.getElement('#username');
        this.password = this.getElement('#registerPassword');
        this.passwordCnf = this.getElement('#registerPasswordCnf');
        this.email = this.getElement('#email');
    }

    resetInput() {
        this.name.value = ''
        this.birthdate.value = ''
        this.job.value = ''
        this.username.value = ''
        this.password.value = ''
        this.passwordCnf.value = ''
        this.email.value = ''
    }

    getElement(selector) {
        const element = document.querySelector(selector);

        return element
    }

    fieldsValidate = true;

    validateFields() {
        var fields = [
            [this.name.value, 'name'],
            [this.birthdate.value, 'birthdate'],
            [this.job.value, 'job'],
            [this.username.value, 'username'],
            [this.password.value, 'password'],
            [this.passwordCnf.value, 'password confirmation'],
            [this.email.value, 'email']
        ]
        fields.forEach(field => {
            if (field[0] === "") {
                alert('The field ' + field[1] + ' is empty.')
                this.fieldsValidate = false
            }
        });
    }

    validatePassword() {
        if (this.password.value != this.passwordCnf.value) {
            this.fieldsValidate = false;
        }
    }

    bindRegister(handler) {
        document.getElementById("registerBtn").addEventListener("click", event => {
            this.validateFields()
            this.validatePassword()
            if (this.fieldsValidate === true) {
                handler(this.name.value, this.birthdate.value.replace(/-/g, '/'), this.job.value,
                    this.username.value, this.password.value, this.email.value)
                this.resetInput();
            }
            this.fieldsValidate = false;
        })
    }
}