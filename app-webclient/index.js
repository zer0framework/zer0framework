const routes = {
    '/login': loginController,
    '/': homepageController,
    '/index.html': homepageController,
    '/contact': contactController,
    '/files': filesController,
    '/post': postController,
    '/todo': todoController,
};

if (localStorage.getItem('token') != null) {

    document.body.innerHTML = getTemplate();

    let contentDiv = document.getElementById('content');

    contentDiv.innerHTML = routes[window.location.pathname];

} else {
    window.history.pushState({}, 'Login', window.location.origin + '/login');
    document.body.innerHTML = loginController.getView().getTemplate();
    routes['/login'].onInit();
}

onNavItemClick = (pathName) => {
    if (localStorage.getItem('token') != null) {

        contentDiv = document.getElementById('content');

        const currentRoute = pathName.split('/')[1];
        const pathRoute = '/' + currentRoute;
        const pathParam = pathName.split('/')[2];

        if (pathRoute === '/contact') {
            window.history.pushState({}, pathName, window.location.origin + pathName + '/1');
        } else {
            window.history.pushState({}, pathName, window.location.origin + pathName)
        }

        let todo = document.getElementById('todo');
        let post = document.getElementById('post');
        let contact = document.getElementById('contact');
        let files = document.getElementById('files');

        todo.classList.remove("active");
        post.classList.remove("active");
        contact.classList.remove("active");
        files.classList.remove("active");

        let element = document.getElementById(currentRoute);
        contentDiv.innerHTML = routes[pathRoute].getView().getTemplate();
        routes[pathRoute].onInit(pathParam);
        if (pathName != "/") {
            element.classList.add("active");
        }
    } else {
        alert('User unauthorized');
    }
}



logout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('userId');

    window.history.pushState({}, 'Login', window.location.origin + '/login');
    document.body.innerHTML = loginController.getView().getTemplate();
    loginController.onInit();
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

window.onbeforeunload = function() {
    localStorage.removeItem('token');
    localStorage.removeItem('userId');
    return null;
};

const index = getTemplate();