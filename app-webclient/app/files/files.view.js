/**
 * @class View
 *
 * Visual representation of the model.
 */
class FilesView {

    constructor() {}

    getTemplate() {
        return `
    <div id="dropArea">
      <form class="myForm">
        <p>Upload multiple files with the file dialog or by dragging and dropping images onto the dashed region</p>
        <input type="file" id="fileElem" multiple accept="image/*" onchange="bindSelect(this.files)"></input>
        <label class="button" for="fileElem">Select some files</label>
      </form>
    </div>
      `;
    }

    onInit() {}

    bindFile() {
        let dropArea = document.getElementById('dropArea');
        ['dragenter', 'dragover', 'dragleave', 'drop'].forEach(eventName => {
            dropArea.addEventListener(eventName, preventDefaults, false)
        });
        ['dragenter', 'dragover'].forEach(eventName => {
            dropArea.addEventListener(eventName, highlight, false)
        });
        ['dragleave', 'drop'].forEach(eventName => {
            dropArea.addEventListener(eventName, unhighlight, false)
        })
        dropArea.addEventListener('drop', handleDrop, false)

        function preventDefaults(e) {
            e.preventDefault()
            e.stopPropagation()
        }

        function highlight() {
            dropArea.classList.add('highlight')
        }

        function unhighlight() {
            dropArea.classList.remove('highlight')
        }

        function handleDrop(e) {
            let dt = e.dataTransfer
            let files = dt.files

            filesController.handleFiles(files)
        }
    }

}

bindSelect = (files) => {
    filesController.handleFiles(files)
}