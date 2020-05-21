$( document ).ready(function() {
    console.log("This preconfigured page is ready")
	params = getParams(window.location.href)
  id = params.id
  prlink = params.prlink
  console.log(prlink)

  if(typeof prlink === 'undefined') {
    console.log()
  } else {
    document.getElementById('prLink').style.visibility='visible'
    document.getElementById('pullRequestLinkTag').text = prlink;
    document.getElementById('pullRequestLinkTag').href = prlink;

  }
	var jcasc_editor = ace.edit("jcasc_editor");
    jcasc_editor.setTheme("ace/theme/twilight");
    jcasc_editor.getSession().setMode("ace/mode/yaml");
    getYAMLSchema(jcasc_editor);
	document.getElementById('jcasc_editor').style.fontSize='18px';
	
	var cwp_editor = ace.edit("cwp_config_editor");
    cwp_editor.setTheme("ace/theme/twilight");
    cwp_editor.getSession().setMode("ace/mode/yaml");
    cwp_editor.setHighlightActiveLine(false);

    console.log(id)
    if(id === "example.yaml") {
      getExampleCWPConfig(cwp_editor)
    } else {
    getCWPConfig(cwp_editor, id);
    }
	  document.getElementById('cwp_config_editor').style.fontSize='18px';
	
	document.getElementById('packager-config').innerHTML = id
});


class githubData  {
  constructor(branchName, description) {
    this.branchName = branchName;
    this.description = description;
  }
}

var getParams = function (url) {
	var params = {};
	var parser = document.createElement('a');
	parser.href = url;
	var query = parser.search.substring(1);
	var vars = query.split('&');
	for (var i = 0; i < vars.length; i++) {
		var pair = vars[i].split('=');
		params[pair[0]] = decodeURIComponent(pair[1]);
	}
	return params;
};


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

function getCWPConfig(editor, configName) {
    fetch('https://raw.githubusercontent.com/jenkins-zh/jenkins-formulas/master/formulas/' + configName)
    .then((resp) => resp.text())
    .then(function (data) {
        editor.setValue(data)
      return data;
    }).catch(function(error) {
        console.log(error)
      })
}

function getExampleCWPConfig(editor) {
  fetch('https://raw.githubusercontent.com/jenkinsci/custom-war-packager/master/demo/casc/packager-config.yml')
  .then((resp) => resp.text())
  .then(function (data) {
      editor.setValue(data)
    return data;
  }).catch(function(error) {
      console.log(error)
    })
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
  var cwp_editor = ace.edit("cwp_config_editor")
  fetch('http://localhost:8080/apis/download/downloadWAR', {
              method: 'POST',
              headers: {
                'Content-Type': 'text/plain',
              },
              body: cwp_editor.getValue(),
            })
            .then(response => response.json())
            .then(data => {
              console.log('Success:', data);})
            .catch((error) => {
              console.error('Error:', error);});
}

function createPR() {
  console.log("Clicked")
  console.log(document.getElementById('username'))
  document.getElementById('github-form').style.visibility='visible'
  document.getElementById('github-submit').style.visibility='visible'
}

function sendPR() {
  let desc = document.getElementById('prDesc').value
  let branchName = document.getElementById('prBranch').value
  var data = JSON.stringify({"branchName": branchName, "description": desc}); 
  fetch('http://localhost:8080/create_PR', {
              method: 'POST',
              headers: {
                'Content-Type': 'application/json',
              },
              body: data,
            })
            .then(response => response.text())
            .then(data => {
              console.log('Success:', data);
              window.location.href="http://localhost:5500/frontend_VanillaJS/preconfigeditor.html?prlink=" + data
            })
            .catch((error) => {
              console.error('Error:', error);});
}

