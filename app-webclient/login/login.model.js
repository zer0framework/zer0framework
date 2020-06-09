/**
 * @class Model
 *
 * Manages the data of the application.
 */
class LoginModel {

    constructor() {}

    onInit() {}

    authenticate(username, password) {
        fetch('http://localhost:8080/auth', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    username: username,
                    password: password
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
                    this.storeUserId(username);

                    window.history.pushState({}, 'Homepage', window.location.origin + '/');
                    document.body.innerHTML = index;
                    document.getElementById('content').innerHTML = routes['/'].getView().getTemplate();
                }
            })
    }

    storeUserId(username) {
        window.fetch('http://localhost:8080/api/users/' + username, {
                method: 'GET',
                headers: { 'Authorization': localStorage.getItem('token') },
            })
            .then(response => response.json())
            .then(json => {
                localStorage.setItem('userId', json.id);
            });
    }
}