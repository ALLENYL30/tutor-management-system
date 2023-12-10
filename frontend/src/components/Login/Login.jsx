import { useNavigate } from 'react-router-dom'
import "./Login.css";
import React from "react";
import { LockOutlined, UserOutlined } from '@ant-design/icons';
import { Button, Form, Input } from 'antd';
import makeRequest from '../makeRequest'

function Login() {
  const navigate = useNavigate();
  function goToRegister() {
    navigate('/register');
  }

  // write the login function here.
  async function onFinish(values) {
    // console.log('Received values of form: ', values);

    const request = {
      password: values.password,
      accountNumber: values.email
    }
    const url = 'login/common';
    const response = await makeRequest('POST', url, request)
    if (response.error) {
      console.log(response.error);
    }
    // login success
    if (response.code === '200') {
      console.log("login success");
      console.log(response);
      loginSuccess(response)
    } else if (response.code === '400') {
      alert("email or password is invalid");
    }
  };

  function loginSuccess(response) {
    // store token in the localstorage
    const token = response.result.token; 
    localStorage.setItem('token', token);
    
    const role = response.result.role;
    if (role === "student") {
      navigate('/student/dashboard');
    } else if (role === "tutor") {
      navigate('/tutor/dashboard');
    }
  }

  return (
    <div className="loginComponent">
      <Form
        name="normal_login"
        className="login-form"
        // initialValues={{
        //   remember: true,
        // }}
        onFinish={onFinish}
      >
        <Form.Item
          name="email"
          rules={[
            {
              required: true,
              message: 'Please input your email!',
            },
          ]}
        >
          <Input prefix={<UserOutlined className="site-form-item-icon" />} placeholder="Email" />
        </Form.Item>
        <Form.Item
          name="password"
          rules={[
            {
              required: true,
              message: 'Please input your Password!',
            },
          ]}
        >
          <Input
            prefix={<LockOutlined className="site-form-item-icon" />}
            type="password"
            placeholder="Password"
          />
        </Form.Item>

        <Form.Item>
          <Button type="primary" htmlType="submit" className="login-form-button">
            Log in
          </Button>
        </Form.Item>
        <Form.Item>
          Or <a href="" onClick={goToRegister}>register now!</a>
        </Form.Item>
      </Form>
    </div>
  );
}

export default Login;
