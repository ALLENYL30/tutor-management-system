import React from "react";
import { Button, Form, Input } from 'antd';
import { LockOutlined, UserOutlined } from '@ant-design/icons';
import img from "../Login/loginImg.jpg";
import styles from "./AdminLogin.module.css"

import makeRequest from '../makeRequest'
import { useNavigate } from 'react-router-dom'

function AdminLogin() {
  
  const navigate = useNavigate();

  async function onFinish(values) {
    const request = {
      password: values.password,
      accountNumber: values.email
    }
    const url = 'login/admin';
    const response = await makeRequest('POST', url, request);
    // console.log('login:', response);
    if (response.code === '200') {
      console.log("admin login success");
      loginSuccess(response)
    } else if (response.code === '400') {
      alert("username or password is invalid");
    }
  }

  function loginSuccess(response) {
    // store token in the localstorage
    const token = response.result.token; 
    localStorage.setItem('token', token);
    navigate('/admin/dashboard');
  }

  return (
    <div>
      <div className={styles.headerDiv}>
        <h2>Admin Login</h2>
      </div>
      <div className={styles.LoginPage}>
        <img className={styles.loginImg} src={img} alt=""></img>
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
        </Form>

      </div>
    </div>
  );
}

export default AdminLogin;