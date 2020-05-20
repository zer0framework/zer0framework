if (localStorage.getItem('token') != null) {

  document.body.innerHTML = getTemplate();
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
    if (localStorage.getItem('token') != null) {
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
  loadCSS('login/login.styles.css');
  loginController.onInit();
  document.body.innerHTML = loginController.getView().getTemplate();
}

logout = () => {
  localStorage.removeItem('token');
  loadCSS('login/login.styles.css');
  loginController.onInit();
  document.body.innerHTML = loginController.getView().getTemplate();
}

function getTemplate() {
  return `
  <!-- NAVBAR -->
  <nav class="navbar">
    <div class="navbar-left-item navbar-item"><a href="#" onclick="onNavItemClick('/'); return false;">App Name</a>
    </div>

    <ul class="navbar-list">
      <li class="navbar-item"><a href="#" onclick="logout()">Logout</a></li>
    </ul>
  </nav>

  <!-- MENU -->
  <ul class="menu">
    <li><a id="dashboard" href="#" class="active">Dashboard</a></li>
    <li><a id="todo" href="#" onclick="onNavItemClick('/todo'); return false;">To do</a></li>
    <li><a id="post" href="#" onclick="onNavItemClick('/post'); return false;">Post</a></li>
    <li><a id="contact" href="#" onclick="onNavItemClick('/contact'); return false;">Contact</a></li>
    <li><a id="files" href="#" onclick="onNavItemClick('/files'); return false;">Files</a></li>
  </ul>

  <!-- CONTENT -->
  <div id="content" class="content"></div>

  <!-- FOOTER -->
  <footer class="footer">
    CONTACT:
    <a href="mailto:business@company.com">business@company.com</a> |
    <a href="tel:1234567890">(123) 456-7890</a>
  </footer>
  `;
}

window.onbeforeunload = function () {
  localStorage.removeItem('token');
  return null;
};

function loadCSS(url) {
  var lnk = document.createElement('link');
  lnk.setAttribute('type', "text/css");
  lnk.setAttribute('rel', "stylesheet");
  lnk.setAttribute('href', url);
  document.getElementsByTagName("head").item(0).appendChild(lnk);
}

const index = getTemplate();