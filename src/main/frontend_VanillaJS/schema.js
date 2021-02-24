var fetchedSchema
fetch('http://localhost:8080/api/json-schema')
.then((resp) => resp.json())
.then(function (data) {
  fetchedSchema = data
  console.log(fetchedSchema)
  $('form').jsonForm({
			"schema": fetchedSchema,
      "onSubmitValid": function (values) {
        console.log(values);
        fetch('http://localhost:8080/api/json-schema/uploadSchema', {
              method: 'POST',
              headers: {
                'Content-Type': 'application/json',
              },
              body: JSON.stringify(values),
            })
            .then(response => response.json())
            .then(data => {
              console.log('Success:', data);})
            .catch((error) => {
              console.error('Error:', error);});
        window.location.href = "http://localhost:5500/frontend_VanillaJS/preconfigeditor.html?id=example.yaml"
      }
});
}).catch(function(error) {
  console.log(error)
})


  