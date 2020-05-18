class LoginView{

    constructor(){

    }  
}

let authenticate = () =>{ 
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
    .then( res =>{
        return res.json();              
    })
    .then(data => 
        document.cookie = "token="+data.token
        )
        
}