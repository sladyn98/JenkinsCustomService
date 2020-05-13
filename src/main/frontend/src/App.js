import React, {useState, useEffect} from 'react';
import logo from './logo.svg';
import './App.css';
import axios from "axios"
import Form from "@rjsf/core";

const JsonSchema = () => {
  const schema = {
    title: "Test form",
    type: "string"
  };
 return <Form schema={schema} />
}

function App() {
  return (
    <div className="App"><JsonSchema/>
    </div>
  );
}
export default App;
