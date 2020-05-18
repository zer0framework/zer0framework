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
        if(res.ok){
            window.location = 'http://127.0.0.1:5500/';
            return res.json();   
        }else{
            alert('username or password is incorrect');
            return null;
        }
                   
    })
    .then(data => {
         if(data != null){
            document.cookie = "token="+data.token
         }        
})
}