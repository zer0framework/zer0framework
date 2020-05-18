let contentDiv = document.getElementById('content');

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

let onNavItemClick = (pathName) => {
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

    let element = document.getElementById(currentRoute);
    element.classList.add("active");
    contentDiv.innerHTML = routes[pathRoute].getView().getTemplate();
	  routes[pathRoute].onInit(pathParam);
  }
}

contentDiv.innerHTML = routes[window.location.pathname];