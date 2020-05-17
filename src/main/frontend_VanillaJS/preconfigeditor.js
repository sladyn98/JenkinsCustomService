$( document ).ready(function() {
    console.log("This preconfigured page is ready")
	params = getParams(window.location.href)
	id = params.id

	var jcasc_editor = ace.edit("jcasc_editor");
    jcasc_editor.setTheme("ace/theme/twilight");
    jcasc_editor.getSession().setMode("ace/mode/yaml");
    getYAMLSchema(jcasc_editor);
	document.getElementById('jcasc_editor').style.fontSize='18px';
	
	var cwp_editor = ace.edit("cwp_config_editor");
    cwp_editor.setTheme("ace/theme/twilight");
    cwp_editor.getSession().setMode("ace/mode/yaml");
    cwp_editor.setHighlightActiveLine(false);
    getCWPConfig(cwp_editor, id);
	document.getElementById('cwp_config_editor').style.fontSize='18px';
	
	document.getElementById('packager-config').innerHTML = id
});

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

