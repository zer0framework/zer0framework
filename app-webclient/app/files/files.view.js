/**
 * @class View
 *
 * Visual representation of the model.
 */
class FilesView {

  constructor() { }

  getTemplate() {
    return `
    <div id="drop-area">
    <form class="my-form">
      <p>Upload multiple files with the file dialog or by dragging and dropping images onto the dashed region</p>
      <input type="file" id="fileElem" multiple accept="image/*" onchange="handleFiles(this.files)">
      <label class="button" for="fileElem">Select some files</label>
    </form>
  </div>
      `;
  }

  onInit() {
    this.bindFile()
  }

  bindFile() {
    let dropArea = document.getElementById('drop-area')
      ;['dragenter', 'dragover', 'dragleave', 'drop'].forEach(eventName => {
        dropArea.addEventListener(eventName, preventDefaults, false)
      })
      ;['dragenter', 'dragover'].forEach(eventName => {
        dropArea.addEventListener(eventName, highlight, false)
      })
      ;['dragleave', 'drop'].forEach(eventName => {
        dropArea.addEventListener(eventName, unhighlight, false)
      })
    dropArea.addEventListener('drop', handleDrop, false)

    function preventDefaults(e) {
      e.preventDefault()
      e.stopPropagation()
    }

    function highlight(e) {
      dropArea.classList.add('highlight')
    }

    function unhighlight(e) {
      dropArea.classList.remove('highlight')
    }

    function handleDrop(e) {
      let dt = e.dataTransfer
      let files = dt.files

      handleFiles(files)
    }

    function handleFiles(files) {
      let confirm = window.confirm('Do you want to upload the files?')

      if (confirm === true) {
        ([...files]).forEach(uploadFile)
      }
    }

    function uploadFile(file) {
      let url = 'http://localhost:8080/api/fileUpload'
      let formData = new FormData()

      formData.append('file', file)

      fetch(url, {
        method: 'POST',
        body: formData,
        headers: { 'Authorization': localStorage.getItem('token') }
      })
        .then(() => { alert('Success') })
        .catch(() => { alert('Error') })
    }
  }

}
