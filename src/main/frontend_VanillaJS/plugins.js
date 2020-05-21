$( document ).ready(function() {
    console.log("Ready")
    getPluginList()

    pluginListArray=[]
});

function addPlugintoList(pluginName) {
     var ul = document.getElementById("plugin-list");
     var listItem = document.createElement("div");
     var name = pluginName
     listItem.innerHTML=`
      <div class="card">
      <div class="card-body" >
        <h5 class="card-title" style="font-size: 20px;"> ${pluginName} </h5>
        <a href="javascript:void();" id = '${pluginName}' onclick="addtoConfig('${pluginName}')" class="btn btn-primary" style="font-size: 15px;" >Add to Configuration</a>
        <a href="javascript:void();"  class="btn btn-primary" style="font-size: 15px; margin-left:30px;" >Edit Configuration</a>
        <a href="javascript:void();"  class="btn btn-primary" style="font-size: 15px; margin-left:30px;" >Edit Version</a>
        <br>
        <p style="font-size: 15px; margin-top: 10px; "class="card-text">This will be a description for the plugin </p>
      </div>
    </div>
    <br>`;
    ul.appendChild(listItem)
}

function getPluginList() {
    fetch('http://localhost:8080/api/json-schema/getPlugins')
    .then((resp) => resp.text())
    .then(function (data) {
        var nameArr = data.split(',');
        for(i=0;i<nameArr.length;i++) {
            addPlugintoList(nameArr[i].replace(/^"(.*)"$/, '$1'));
            if(i==10) {
                break;
            }
        }

    }).catch(function(error) {
        console.log(error)
      })
}

function addtoConfig(pluginName) {
    console.log("This has been clicked: " + pluginName)
    document.getElementById(pluginName).innerHTML="Remove configuration"
    pluginListArray.push(pluginName)
    console.log(pluginListArray.length)
}