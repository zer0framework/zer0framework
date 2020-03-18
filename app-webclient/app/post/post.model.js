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
        headers: { 'Authorization': 'XXXXXXXXX' }
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
    } else if (post.id === undefined || post.id === null) {
      this.create(post);
    } else {
      this.update(post);
    }
  }

  addPost(postId, postTitle, postBody) {
    const newPost = {
      userId: 1,
      id: postId == undefined ? null : postId,
      title: postTitle,
      body: postBody,
    };

    if (this.posts === null) {
      this.posts = [];
    }

    this._commit(newPost);
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

  editPost(id, updatedText) {
    this.posts = this.posts.map(post =>
      post.id === id ? { id: post.id, text: updatedText, complete: post.complete } : post
    )

    this._commit(this.posts, true);
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

  /* Fetch-API */

  create(post) {
    window.fetch(apiUrl, {
      method: 'POST',
      headers: { 'Authorization': 'XXXXXXXXX' },
      body: JSON.stringify(post)
    }).then(function (response) {
      response.json().then(li)
    });
  }

  update(post) {
    let _self = this;
    window.fetch(apiUrl + "/" + post.id, {
      method: 'PATCH',
      headers: { 'Authorization': 'XXXXXXXXX' },
      body: JSON.stringify(post)
    }).then(function (response) {
      response.json().then(function (post) {
        _self.refreshData(post);
      })
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