/**
 * @class View
 *
 * Visual representation of the model.
 */
class LoginView {

    constructor() {
    }

    getTemplate() {
        loadCSS('login/login.styles.css');
        return `
    <div class="login-page">
        <div class="form">
            <form class="login-form">
            <input id="username" type="text" placeholder="username" />
            <input id="password" type="password" placeholder="password" />
            <input type="button" name="login" id="loginBtn" value="login" onclick="authenticate()">
            <p class="message">Not registered? <a href="#">Create an account</a></p>
            <a class="message" href="#">Forgot password?</a>
            </form>
        </div>
    </div>
        `;
    }

    onInit() {
    }

}

let authenticate = () => {
    fetch('http://localhost:8080/auth', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            username: document.getElementById('username').value,
            password: document.getElementById('password').value
        })
    })
        .then(res => {
            if (res.ok) {
                return res.json();
            } else {
                alert('username or password is incorrect');
                return null;
            }

        })
        .then(data => {
            if (data != null) {
                localStorage.setItem('token', data.token);
                storeUserId();

                loginController.onInit();

                window.history.pushState({}, 'Homepage', window.location.origin + '/');
                loadCSS('styles.css');
                document.body.innerHTML = index;
                document.getElementById('content').innerHTML = routes['/'].getView().getTemplate();
            }
        })
}

function storeUserId() {
    window.fetch('http://localhost:8080/api/users/' + document.getElementById('username').value, {
        method: 'GET',
        headers: { 'Authorization': localStorage.getItem('token') },
    })
        .then(response => response.json())
        .then(json => {
            localStorage.setItem('userId', json.id);
        });
}


function loadCSS(url) {
    var index = document.createElement('link');
    lnk.setAttribute('type', "text/css");
    lnk.setAttribute('rel', "stylesheet");
    lnk.setAttribute('href', url);
    document.getElementsByTagName("head").item(0).appendChild(lnk);
}