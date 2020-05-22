/**
 * @class Model
 *
 * Manages the data of the application.
 */
class PostModel {

  constructor() {
  }

  onInit(param) {
    const url = param !== undefined && param != null ? apiUrl + '/' + param : apiUrl;
    window.fetch(url,
      {
        method: 'GET',
        headers: { 'Authorization': localStorage.getItem('token') }
      })
      .then(response => response.json())
      .then(json => {
        // only if array
        if (Array.isArray(json)) {
          this.posts = json;
        }
        this.onPostListChanged(json);
      });
  }

  bindPostListChanged(callback) {
    this.onPostListChanged = callback;
  }

  _commit(post, removal) {
    this.onPostListChanged(post);
    if (removal === true) {
      this.remove(post);
    } else if (post.id === undefined) {
      this.create(post);
    } else {
      this.update(post);
    }
  }

  addPost(postId, postTitle, postBody) {
    const newPost = {
      userId: localStorage.getItem('userId'),
      title: postTitle,
      body: postBody,
    };

    if (this.posts === null) {
      this.posts = [];
    }

    this._commit(newPost);
  }

  editPost(postId, postTitle, postBody) {
    const editedPost = {
      id: postId,
      userId: localStorage.getItem('userId'),
      title: postTitle,
      body: postBody,
    };
    this._commit(editedPost);
  }

  refreshData(newPost) {
    const index = this.posts.findIndex(p => p.id === newPost.id)
    if (index === -1) {
      this.posts.push(newPost);
    } else {
      this.posts[index] = newPost;
    }

    this.onPostListChanged(this.posts);
  }


  deletePost(id) {
    this.posts = this.posts.filter(post => post.id !== id)

    this._commit(this.posts)
  }

  togglePost(id) {
    this.posts = this.posts.map(post =>
      post.id === id ? { id: post.id, text: post.text, complete: !post.complete } : post
    )

    this._commit(this.posts)
  }

  create(post) {
    window.fetch(apiUrl, {
      method: 'POST',
      headers: { 'Authorization': localStorage.getItem('token') },
      body: JSON.stringify(post)
    }).then(response => {
      return response;
    })
  }

  update(post) {
    window.fetch(apiUrl + "/", {
      method: 'PUT',
      headers: { 'Authorization': localStorage.getItem('token') },
      body: JSON.stringify(post)
    }).then(response => {
      return response;
    });
  }


  remove(post) {
    window.fetch(apiUrl + "/" + post.id, {
      method: 'DELETE'
    }).then(function () {
      posts.removeChild(event.target.parentNode)
    });
  }

}

// const apiUrl = 'http://jsonplaceholder.typicode.com/posts';
const apiUrl = 'http://localhost:8080/api/posts';