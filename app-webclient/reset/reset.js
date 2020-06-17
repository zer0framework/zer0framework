resetPsw = () => {
    password = document.getElementById('resetPassword').value
    passwordCnf = document.getElementById('resetPasswordCnf').value
    if (password === "") {
        alert('Please inform a password.')
    } else if (passwordCnf === "") {
        alert('Please confirm your password.')
    } else if (password !== passwordCnf) {
        alert('The passwords do not match.')
    } else {
        getUserToken()
        alert('Password reseted!')
        document.getElementById('resetPassword').value = ''
        document.getElementById('resetPasswordCnf').value = ''
    }
}

function getUserToken() {
    const resetUrl = window.location.toString();
    const resetToken = resetUrl.split('=')[1];

    window.fetch('http://localhost:8080/cipher', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                mode: 'decryptor',
                cipher: resetToken
            })
        }).then(response => response.text())
        .then(data => getUserReset(resetToken, data.split(':')[0]))
}

function getUserReset(token, user) {
    window.fetch('http://localhost:8080/api/users/' + user, {
            method: 'GET',
            headers: { 'Authorization': token },
        })
        .then(response => response.text())
        .then(data => {
            user = JSON.parse(data)
            user.password = this.passwordCnf
            resetPassword(token, user)
        });
}

function resetPassword(token, user) {
    window.fetch('http://localhost:8080/api/users', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': token
        },
        body: JSON.stringify(user)
    }).then(response => {
        return response
    })
}