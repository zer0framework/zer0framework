/**
 * @class View
 *
 * Visual representation of the model.
 */
class TodoView {

    constructor() {}

    getTemplate() {
        return `
		<form id="todoForm">
      <input id="todoInput" type="text" placeholder="Add todo">
      <br></br>
      <button>Submit</button>
		</form>
		<ul id="todoList" class="todo-list">
		</ul>
	`;
    }

    onInit() {
        this.todoForm = this.getElement('#todoForm')
        this.todoInput = this.getElement('#todoInput');
        this.todoList = this.getElement('#todoList');

        this.temporaryTodoText = '';
        this.initLocalListeners();
    }

    resetInput() {
        this.todoInput.value = ''
    }

    createElement(tag, className) {
        const element = document.createElement(tag)

        if (className) element.classList.add(className)

        return element
    }

    getElement(selector) {
        const element = document.querySelector(selector)

        return element
    }

    displayTodos(todos) {
        while (this.todoList.firstChild) {
            this.todoList.removeChild(this.todoList.firstChild)
        }

        if (todos.length === 0) {
            const p = this.createElement('p')
            p.id = 'noTodo'
            p.textContent = 'Nothing to do! Add a task?'
            this.todoList.append(p)
        } else {
            todos.forEach(todo => {
                const li = this.createElement('li')
                li.id = todo.id

                const checkbox = this.createElement('input')
                checkbox.type = 'checkbox'
                checkbox.checked = todo.complete

                const span = this.createElement('span')
                span.contentEditable = true
                span.classList.add('editable')

                if (todo.complete) {
                    const strike = this.createElement('s')
                    strike.textContent = todo.text
                    span.append(strike)
                } else {
                    span.textContent = todo.text
                }

                const deleteButton = this.createElement('button', 'delete')
                deleteButton.id = 'deleteTodo'
                deleteButton.textContent = 'Delete'
                li.append(checkbox, span, deleteButton)

                this.todoList.append(li)
            })
        }

    }

    initLocalListeners() {
        this.todoList.addEventListener('input', event => {
            if (event.target.className === 'editable') {
                this.temporaryTodoText = event.target.innerText
            }
        })
    }

    bindAddTodo(handler) {
        this.todoForm.addEventListener('submit', event => {
            event.preventDefault();
            if (this.todoInput.value) {
                handler(this.todoInput.value)
                this.resetInput()
            }
        })
    }

    bindDeleteTodo(handler) {
        this.todoList.addEventListener('click', event => {
            if (event.target.className === 'delete') {
                const id = parseInt(event.target.parentElement.id)
                handler(id)
            }
        })
    }

    bindEditTodo(handler) {
        this.todoList.addEventListener('focusout', event => {
            if (this.temporaryTodoText) {
                const id = parseInt(event.target.parentElement.id)
                handler(id, this.temporaryTodoText)
                this.temporaryTodoText = ''
            }
        })
    }

    bindToggleTodo(handler) {
        this.todoList.addEventListener('change', event => {
            if (event.target.type === 'checkbox') {
                const id = parseInt(event.target.parentElement.id)
                handler(id)
            }
        })
    }
}