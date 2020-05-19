if (localStorage.getItem('token') == null) {

  let contentDiv = document.getElementById('content');

  loadCSS('styles.css');

  let routes = {
    '/': homepage,
    '/index.html': homepage,
    '/todo': todoController,
    '/post': postController,
    '/contact': contact,
  };

  window.onpopstate = () => {
    contentDiv.innerHTML = routes[window.location.pathname];
  }

  onNavItemClick = (pathName) => {
    if (localStorage.getItem('token') == null) {
      window.history.pushState({}, pathName, window.location.origin + pathName);

      if (typeof routes[pathName] === 'string' || routes[pathName] instanceof String) {
        contentDiv.innerHTML = routes[pathName];
      } else {
        const currentRoute = pathName.split('/')[1];
        const pathRoute = '/' + currentRoute;
        const pathParam = pathName.split('/')[2];

        let dashboard = document.getElementById('dashboard');
        let todo = document.getElementById('todo');
        let post = document.getElementById('post');
        let contact = document.getElementById('contact');
        let files = document.getElementById('files');

        dashboard.classList.remove("active");
        todo.classList.remove("active");
        post.classList.remove("active");
        contact.classList.remove("active");
        files.classList.remove("active");

        loadCSS('app/' + currentRoute + pathRoute + '.styles.css');

        alert(currentRoute + " - " + pathRoute + " - " + pathParam)
        //loadCSS('app/styles.css');
        let element = document.getElementById(currentRoute);
        element.classList.add("active");
        contentDiv.innerHTML = routes[pathRoute].getView().getTemplate();
        routes[pathRoute].onInit(pathParam);
      }
    } else {
      alert('User unauthorized');
    }
  }

  contentDiv.innerHTML = routes[window.location.pathname];
} else {
  loginController.onInit();
  document.body.innerHTML = loginController.getView().getTemplate();
  loadCSS('login/login.styles.css');
}

function loadCSS(url) {
  var lnk = document.createElement('link');
  lnk.setAttribute('type', "text/css");
  lnk.setAttribute('rel', "stylesheet");
  lnk.setAttribute('href', url);
  document.getElementsByTagName("head").item(0).appendChild(lnk);
}