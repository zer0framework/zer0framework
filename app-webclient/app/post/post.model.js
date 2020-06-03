/**
 * @class Model
 *
 * Manages the data of the application.
 */
class PostModel {

    constructor() {}

    onInit(param) {
        this.getData(param)
    }

    getData(param) {
        const url = param !== undefined && param != null ? postUrl + '/' + param : postUrl + '/?userId=' + localStorage.getItem('userId');
        window.fetch(url, {
                method: 'GET',
                headers: { 'Authorization': localStorage.getItem('token') }
            })
            .then(response => response.json())
            .then(json => {
                this.onPostListChanged(json);
            });
    }

    bindPostListChanged(callback) {
        this.onPostListChanged = callback;
    }

    commit(post, removal) {
        if (removal === true) {
            this.remove(post);
        } else if (post.id === undefined) {
            this.create(post);
        } else {
            this.update(post);
        }
    }

    addPost(postTitle, postBody) {
        const newPost = {
            userId: localStorage.getItem('userId'),
            title: postTitle,
            body: postBody,
        };
        this.commit(newPost);
    }

    editPost(postId, postTitle, postBody) {
        const editedPost = {
            id: postId,
            userId: localStorage.getItem('userId'),
            title: postTitle,
            body: postBody,
        };
        this.commit(editedPost);
    }

    deletePost(id) {
        const toDelete = { id: id }

        this.commit(toDelete, true)
    }

    create(post) {
        window.fetch(postUrl, {
            method: 'POST',
            headers: { 'Authorization': localStorage.getItem('token') },
            body: JSON.stringify(post)
        }).then(response => {
            this.getData();
            return response;
        })
    }

    update(post) {
        window.fetch(postUrl + "/", {
            method: 'PUT',
            headers: { 'Authorization': localStorage.getItem('token') },
            body: JSON.stringify(post)
        }).then(response => {
            this.getData();
            return response;
        });
    }


    remove(post) {
        window.fetch(postUrl + "/" + post.id, {
            method: 'DELETE',
            headers: { 'Authorization': localStorage.getItem('token') }
        }).then(response => {
            this.getData();
            return response;
        });
    }

}

const postUrl = 'http://localhost:8080/api/posts';