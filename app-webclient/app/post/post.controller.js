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
    this.view.bindAddPost(this.handleAddPost)
    this.view.bindDeletePost(this.handleDeletePost)

    // Display initial posts
    this.onPostListChanged(this.model.posts)
  }

  onPostListChanged = posts => {
    if (posts != undefined) {
      this.view.displayPosts(posts);
    }
  }

  handleAddPost = (postId, postTitle, postBody) => {
    this.model.addPost(postId, postTitle, postBody);
  }

  handleEditPost = (id, postText) => {
    this.model.editPost(id, postText)
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
