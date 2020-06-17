/**
 * @class View
 *
 * Visual representation of the model.
 */
class ResetView {

    constructor() {}

    getTemplate() {
        return `
        <div class="resetPage" id="resetPage">
            <div class="resetDiv" id="resetDiv">
                <p class="message">For reseting your password, please inform your account's email adress.</p>
                <input id="resetEmail" type="text" placeholder="Email"/><br>
                <button id="resetBtn">Reset</Button>
            </div>
        </div>
        `;
    }

    onInit() {
        this.resetEmail = document.getElementById('resetEmail')
    }

    getElement(selector) {
        const element = document.querySelector(selector);

        return element
    }

    createElement(tag, className) {
        const element = document.createElement(tag)

        if (className) element.classList.add(className)

        return element
    }

    resetSend() {
        const resetNode = this.createElement('resetDiv');
        resetNode.parentNode.removeChild(resetNode);

        const resetDiv = this.createElement('div')
        resetDiv.className = 'resetDiv'

        const resetP = this.createElement('p')
        resetP.id = 'resetP'
        resetP.textContent = 'Please check your email inbox to reset your password.'

        resetDiv.append(resetP)

        document.getElementById('resetPage').append(resetDiv)
    }

    bindReset(handler) {
        document.getElementById('resetBtn').addEventListener('click', event => {
            if (this.resetEmail.value !== "") {
                handler(this.resetEmail.value)
            } else {
                alert("Please inform your account's email adress.")
            }
        })
    }

}