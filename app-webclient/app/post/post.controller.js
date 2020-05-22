/**
 * @class Controller
 *
 * Links the user input and the view output.
 *
 * @param model
 * @param view
 */
class PostController {

  constructor(model, view) {
    this.model = model
    this.view = view
  }

  onInit(param) {
    this.model.onInit(param);
    this.view.onInit(param);
    // Explicit this binding
    this.model.bindPostListChanged(this.onPostListChanged)
    this.view.bindSubmitPost(this.handlePost)
    this.view.bindDeletePost(this.handleDeletePost)

    // Display initial posts
    this.onPostListChanged(this.model.posts)
  }

  onPostListChanged = posts => {
    if (posts != undefined) {
      this.view.displayPosts(posts);
    }
  }

  handlePost = (postId, postTitle, postBody) => {
    if (postId != "") {
      this.handleEditPost(postId, postTitle, postBody)
    } else {
      this.handleAddPost(postId, postTitle, postBody)
    }
  }

  handleAddPost = (postId, postTitle, postBody) => {
    this.model.addPost(postId, postTitle, postBody);
  }

  handleEditPost = (postId, postTitle, postBody) => {
    this.model.editPost(postId, postTitle, postBody)
  }

  handleDeletePost = id => {
    this.model.deletePost(id)
  }

  handleTogglePost = id => {
    this.model.togglePost(id)
  }

  getView() {
    return this.view;
  }

}

const postController = new PostController(new PostModel(), new PostView());
