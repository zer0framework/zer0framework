/**
 * @class View
 *
 * Visual representation of the model.
 */
class PostView {
  postForm;

  constructor() { }

  getTemplate() {
    return `
    <form id="postForm">
      <label for="fname">Id:</label><br>
      <input type="text" id="postId" name="postId" value=""><br>
      <label for="lname">Title:</label><br>
      <input type="text" id="postTitle" name="postTitle"><br>
      <label for="lname">Body:</label><br>
      <textarea id="postBody" name="postBody" rows="4" cols="50"></textarea>
      <br><br>
      <button id="submit">Submit</button>
      <button id="delete">Delete</button>
      </form>

    <h2>Posts</h2><br>
    
    <ul id="postList" class="post-list">
		</ul>
	`;
  }

  onInit() {
    this.postForm = this.getElement('#postForm');
    this.postId = this.getElement('#postId');
    this.postTitle = this.getElement('#postTitle');
    this.postBody = this.getElement('#postBody');
    this.postList = this.getElement('#postList');

    this._temporaryPostText = '';
  }

  get _postTitle() {
    return this.postTitle.value;
  }

  get _postBody() {
    return this.postBody.value;
  }

  _resetInput() {
    this.postId.value = '';
    this.postTitle.value = '';
    this.postBody.value = '';
  }

  createElement(tag, className) {
    const element = document.createElement(tag);

    if (className) {
      element.classList.add(className);
    }

    return element;
  }

  getElement(selector) {
    const element = document.querySelector(selector);

    return element
  }

  displayPosts(posts) {
    while (this.postList.firstChild) {
      this.postList.removeChild(this.postList.firstChild)
    }

    if (posts !== undefined && posts !== null) {
      if (posts.forEach !== undefined) {
        // Create nodes
        posts.forEach(post => {
          // Append nodes
          const li = this.createElement('li');
          li.id = post.id
          const link = this.createElement('a');
          link.textContent = post.title;
          link.addEventListener('click', event => {
            event.preventDefault();
            onNavItemClick(`/post/${post.id}`);
          });
          link.href = '#';
          li.append(link);

          // Append nodes
          this.postList.append(li);
        });

        // Debugging
        console.log(posts);
      } else {
        this.postId.value = posts.id;
        this.postTitle.value = posts.title;
        this.postBody.value = posts.body;
      }
    }
  }

  bindSubmitPost(handler) {
    document.getElementById('submit').addEventListener('click', event => {
      event.preventDefault();
      handler(this.postId.value, this.postTitle.value, this.postBody.value);
      this._resetInput();
    });
  }

  bindDeletePost(handler) {
    document.getElementById('delete').addEventListener('click', event => {
      event.preventDefault();
      if (this.postId.value === "") {
        alert('Please select a post.')
      } else {
        handler(this.postId.value);
        this._resetInput();
      }
    });
  }

}