class LoginView {

    constructor() {
    }

    getTemplate() {
        return `
    <link rel="stylesheet" href="app/login/login.style.css">

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
                window.location = 'http://127.0.0.1:5500/';
                return res.json();
            } else {
                alert('username or password is incorrect');
                return null;
            }

        })
        .then(data => {
            if (data != null) {
                localStorage.setItem('token', data.token)
            }
        })
}