import React,{Component} from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import * as serviceWorker from './serviceWorker';
import Form from "@rjsf/core";


// const schema = {
//   title: "Test form",
//   type: "object",
//   properties: {
//     name: {
//       type: "string"
//     },
//     age: {
//       type: "number"
//     }
//   }
// };

// ReactDOM.render(( <Form schema={schema} />), 
//   document.getElementById('root'));

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
