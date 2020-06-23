/**
 * @class View
 *
 * Visual representation of the model.
 */
class LoginView {

    constructor() {}

    getTemplate() {
        return `
        <div class="loginPage">
            <div class="loginDiv">
                <form class="loginForm">
                <input id="loginUsername" type="text" placeholder="Username" />
                <input id="loginPassword" type="password" placeholder="Password"/>
                <input type="button" id="loginBtn" value="Login">
                <p class="message">Not registered? <p id="create" onClick="register()">Create an account</p></p>
                <p class="message" id="resetText" onClick="resetPassword()">Forgot password?</p>
                </form>
            </div>
        </div>
        `;
    }

    onInit() {
        this.username = this.getElement('#loginUsername');
        this.password = this.getElement('#loginPassword');
    }

    getElement(selector) {
        const element = document.querySelector(selector);

        return element
    }

    bindLogin(handler) {
        document.getElementById('loginBtn').addEventListener('click', event => {
            handler(this.username.value, this.password.value)
            return event;
        })
    }

}

resetPassword = () => {
    window.history.pushState({}, 'Reset Password', window.location.origin + '/');
    document.body.innerHTML = resetController.getView().getTemplate();
    resetController.onInit();
}

register = () => {
    window.history.pushState({}, 'Register', window.location.origin + '/');
    document.body.innerHTML = registerController.getView().getTemplate();
    registerController.onInit();
}