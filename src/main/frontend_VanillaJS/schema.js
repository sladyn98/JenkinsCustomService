var fetchedSchema
fetch('http://localhost:8080/api/json-schema')
.then((resp) => resp.json())
.then(function (data) {
  fetchedSchema = data
  console.log(fetchedSchema)
  $('form').jsonForm({
			schema: fetchedSchema,
			"onSubmit": function (errors, values) {
    if (errors) {
      alert('Check the form for invalid values!');
      return;
    }
    // "values" follows the schema, yeepee!
    console.log(values);
  }
});
}).catch(function(error) {
  console.log(error)
})
  