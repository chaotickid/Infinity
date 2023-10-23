
'use client';
import React, { useState } from 'react'
import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const Login = (e) => {

  const [email, setEmail] = useState('');
  const [password, setPassowrd] = useState('');

  function loginHandler() {
    axios({
      method: 'post',
      url: 'http://localhost:9090/api/usermanagement/signup',
      data: {

        "email": email,
        "password": password
      }
    }).then(function (response) {
      console.log(response.data)
      toast.success("Created user successfully !")
    }).catch(error => {
      console.log(error)
      toast.error(error.response.data.error)
   })
    
  }

  const readEmail = (e) => {
    setEmail(e.currentTarget.value);
    console.log(email)
  }

  const readPassword = (e) => {
    setPassowrd(e.currentTarget.value);
    console.log(password)
  }

  return (
    <div>
      <div classNameName="container px-50 py-24 mx-auto flex sm:flex-nowrap flex-wrap">
        <div className="px-50">
          <h2 className="text-gray-900 text-lg mb-1 font-medium title-font">Infinity</h2>
          <p className="leading-relaxed mb-5 text-gray-600">Wolcome to infinity app</p>
          <div className="relative mb-4">
            <label for="name" className="leading-7 text-sm text-gray-600">Email</label>
            <input type="text" id="name" name="name" className="w-full bg-white rounded border border-gray-300 focus:border-indigo-500 focus:ring-2 focus:ring-indigo-200 text-base outline-none text-gray-700 py-1 px-3 leading-8 transition-colors duration-200 ease-in-out" onChange={readEmail}></input>
          </div>
          <div className="relative mb-4">
            <label for="email" className="leading-7 text-sm text-gray-600">Passoword</label>
            <input type="email" id="email" name="email" className="w-full bg-white rounded border border-gray-300 focus:border-indigo-500 focus:ring-2 focus:ring-indigo-200 text-base outline-none text-gray-700 py-1 px-3 leading-8 transition-colors duration-200 ease-in-out" onChange={readPassword}></input>
          </div>
          <button className="text-white bg-indigo-500 border-0 py-2 px-6 focus:outline-none hover:bg-indigo-600 rounded text-lg" onClick={loginHandler}>Login</button>
        </div>
      </div>
    </div>
  )
}

export default Login