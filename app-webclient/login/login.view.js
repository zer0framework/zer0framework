/**
 * @class View
 *
 * Visual representation of the model.
 */
class LoginView {

    constructor() {}

    getTemplate() {
        return `
    <div class="login-page">
        <div class="form">
            <form class="login-form">
            <input id="username" type="text" placeholder="username" />
            <input id="password" type="password" placeholder="password" />
            <input type="button" name="login" id="loginBtn" value="login"">
            <p class="message">Not registered? <a href="#">Create an account</a></p>
            <a class="message" href="#">Forgot password?</a>
            </form>
        </div>
    </div>
        `;
    }

    onInit() {
        this.loginForm = this.getElement('#login-form');
        this.username = this.getElement('#username');
        this.password = this.getElement('#password');
    }

    getElement(selector) {
        const element = document.querySelector(selector);

        return element
    }

    bindLogin(handler) {
        document.getElementById('loginBtn').addEventListener('click', event => {
            handler(this.username.value, this.password.value)
        })
    }

}