import React,{Component} from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import * as serviceWorker from './serviceWorker';


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

ReactDOM.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>,
  document.getElementById('root')
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();