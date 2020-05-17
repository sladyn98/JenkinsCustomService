
$( document ).ready(function() {
    console.log( "ready!" );
    var jcasc_editor = ace.edit("jcasc_editor");
    jcasc_editor.setTheme("ace/theme/twilight");
    jcasc_editor.getSession().setMode("ace/mode/yaml");
    getYAMLSchema(jcasc_editor);
    document.getElementById('jcasc_editor').style.fontSize='18px';

    var cwp_editor = ace.edit("cwp_config_editor");
    cwp_editor.setTheme("ace/theme/twilight");
    cwp_editor.getSession().setMode("ace/mode/yaml");
    cwp_editor.setHighlightActiveLine(false);
    getCWPConfig(cwp_editor)
    document.getElementById('cwp_config_editor').style.fontSize='18px';
});

function getYAMLSchema(editor) {
    fetch('http://localhost:8080/api/json-schema/getYaml')
    .then((resp) => resp.text())
    .then(function (data) {
        editor.setValue(data)
      return data;
    }).catch(function(error) {
        console.log(error)
      })
}


function getCWPConfig(editor) {
    fetch('http://localhost:8080/api/json-schema/getCWPConfig')
    .then((resp) => resp.text())
    .then(function (data) {
        editor.setValue(data)
      return data;
    }).catch(function(error) {
        console.log(error)
      })
}


function onStartedDownload() {
  console.log(`Started downloading`);
}

function onFailed(error) {
  console.log(`Download failed: ${error}`);
}

function saveData(blob, fileName) {
    var a = document.createElement("a");
    document.body.appendChild(a);
    a.style = "display: none";
    var url = window.URL.createObjectURL(blob);
    a.href = url;
    a.download = fileName;
    a.click();
    window.URL.revokeObjectURL(url);
}

function downloadJcasC() {
  var xhr = new XMLHttpRequest();
  xhr.open("GET", 'http://localhost:8080/apis/download/jcascYaml');
  xhr.responseType = "blob";
  xhr.onload = function () {
      saveData(this.response, 'casc.yml');
  };
  xhr.send();
}

function downloadWAR() {
  var xhr = new XMLHttpRequest();
  xhr.open("GET", 'http://localhost:8080/apis/download/war');
  xhr.responseType = "blob";
  xhr.onload = function () {
      saveData(this.response, 'jenkins.war');
  };
  xhr.send();
}

function downloadDockerfile() {
  var xhr = new XMLHttpRequest();
  xhr.open("GET", 'http://localhost:8080/apis/download/dockerfile');
  xhr.responseType = "blob";
  xhr.onload = function () {
      saveData(this.response, 'Dockerfile');
  };
  xhr.send();
}




