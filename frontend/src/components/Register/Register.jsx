import React, { useState } from "react";

import { Button, Checkbox, Form, Input, Select, Modal } from "antd";
import makeRequest from '../makeRequest'
import { useNavigate } from 'react-router-dom'

const formItemLayout = {
  labelCol: {
    xs: {
      span: 24,
    },
    sm: {
      span: 8,
    },
  },
  wrapperCol: {
    xs: {
      span: 24,
    },
    sm: {
      span: 16,
    },
  },
};
const tailFormItemLayout = {
  wrapperCol: {
    xs: {
      span: 24,
      offset: 0,
    },
    sm: {
      span: 16,
      offset: 8,
    },
  },
};
const Register = () => {
  const [isModalOpen, setIsModalOpen] = useState(false);

  const showModal = () => {
    setIsModalOpen(true);
  };

  const handleOk = () => {
    setIsModalOpen(false);
  };

  const handleCancel = () => {
    setIsModalOpen(false);
  };
  const { Option } = Select;

  const [form] = Form.useForm();

  // register account
  function onFinish(values) {
    console.log("Received values of form: ", values);
    if (values.role === "student") {
      studentRegister(values);
    } else if (values.role === "tutor") {
      tutorRegister(values);
    }
  };

  async function studentRegister(values) {
    const url = "register/student";
    const request = {
      "account": values.email,
      "password": values.password,
      "userName": "xxx",
      "address":values.address,
      "phone":values.phone,
      "timeZone":"Sydney",
      "image": "",
    }
    const response = await makeRequest('POST', url, request)
    if (response.error) {
      console.log(response.error);
    }
    // login success
    if (response.code === '200') {
      alert("register success");
      goToLogin();
    } else if (response.code === '400') {
      alert("email or password is invalid");
    }
  }

  async function tutorRegister(values) {
    const url = "register/tutor";
    const request = {
      "account": values.email,
      "password": values.password,
      "userName": "xxx",
      "address":values.address,
      "phone":values.phone,
      "timeZone":"Sydney",
      "bio": values.bio,
      "filePath": "D:/courses.txt",
      "image": "",
    }
    const response = await makeRequest('POST', url, request)
    if (response.error) {
      console.log(response.error);
    }
    // login success
    if (response.code === '200') {
      alert("register success");
      goToLogin()
    } else if (response.code === '400') {
      alert("email or password is invalid");
    }
  }
  const navigate = useNavigate();
  function goToLogin() {
    navigate('/login');
  }

  return (
    <Form
      {...formItemLayout}
      form={form}
      name="register"
      onFinish={onFinish}
      initialValues={{
        residence: ["zhejiang", "hangzhou", "xihu"],
        prefix: "86",
      }}
      style={{
        minWidth: 400,
      }}
      scrollToFirstError
    >
      <Form.Item
        name="email"
        label="E-mail"
        rules={[
          {
            type: "email",
            message: "The input is not valid E-mail!",
          },
          {
            required: true,
            message: "Please input your E-mail!",
          },
        ]}
      >
        <Input />
      </Form.Item>

      <Form.Item
        name="password"
        label="Password"
        rules={[
          {
            required: true,
            message: "Please input your password!",
          },
        ]}
        hasFeedback
      >
        <Input.Password />
      </Form.Item>

      <Form.Item
        name="confirm"
        label="Confirm Password"
        dependencies={["password"]}
        hasFeedback
        rules={[
          {
            required: true,
            message: "Please confirm your password!",
          },
          ({ getFieldValue }) => ({
            validator(_, value) {
              if (!value || getFieldValue("password") === value) {
                return Promise.resolve();
              }
              return Promise.reject(
                new Error("The new password that you entered do not match!")
              );
            },
          }),
        ]}
      >
        <Input.Password />
      </Form.Item>

      <Form.Item
        name="role"
        label="Role"
        hasFeedback
        rules={[{ required: true, message: "Please select your role!" }]}
      >
        <Select placeholder="Please select a role">
          <Option value="tutor">Tutor</Option>
          <Option value="student">Student</Option>
        </Select>
      </Form.Item>

      <Form.Item
        name="address"
        label="Address"
        rules={[
          {
            required: true,
            message: "Please input your address!",
          },
        ]}
        hasFeedback
      >
        <Input />
      </Form.Item>

      <Form.Item
        name="phone"
        label="Phone"
        rules={[
          {
            required: true,
            message: "Please input your phone!",
          },
        ]}
        hasFeedback
      >
        <Input />
      </Form.Item>

      <Form.Item
        name="bio"
        label="Bio"
        rules={[
          {
            required: true,
            message: "Please input your bio!",
          },
        ]}
        hasFeedback
      >
        <Input.TextArea showCount maxLength={100} />
      </Form.Item>


      <Form.Item
        name="agreement"
        valuePropName="checked"
        rules={[
          {
            validator: (_, value) =>
              value
                ? Promise.resolve()
                : Promise.reject(new Error("Should accept agreement")),
          },
        ]}
        {...tailFormItemLayout}
      >
        <Checkbox>
          I have read the{" "}
          <Button type="link" onClick={showModal} style={{ padding: 0 }}>
            Agreement
          </Button>
          <Modal
            title="Agreement"
            open={isModalOpen}
            onOk={handleOk}
            onCancel={handleCancel}
          >
            <p>
              <strong>1. Acceptance of Terms</strong>
            </p>
            <p>
              By accessing and using this online learning website, you accept
              and agree to be bound by the terms and conditions of this user
              agreement.
            </p>

            <p>
              <strong>2. Registration and Account Security</strong>
            </p>
            <p>
              Users must register for an account to access certain features of
              the website. You agree to provide accurate, current, and complete
              information during the registration process and to update such
              information to keep it accurate, current, and complete.
            </p>

            <p>
              <strong>3. Privacy Policy</strong>
            </p>
            <p>
              Your use of the website is also governed by our Privacy Policy,
              which can be found at [link to Privacy Policy].
            </p>

            <p>
              <strong>4. User Conduct</strong>
            </p>
            <p>
              You agree to use the website and its content for lawful purposes
              only and to comply with all applicable laws, regulations, and
              rules.
            </p>

            <p>
              <strong>5. Intellectual Property Rights</strong>
            </p>
            <p>
              All content on this website, including text, graphics, logos,
              images, and software, is the property of the website or its
              content suppliers and is protected by international copyright
              laws.
            </p>

            <p>
              <strong>6. User-Generated Content</strong>
            </p>
            <p>
              You may be able to submit content, such as comments or discussion
              posts. You are solely responsible for any content you submit and
              the consequences of posting or publishing it.
            </p>

            <p>
              <strong>7. Payment and Refund Policy</strong>
            </p>
            <p>
              Some courses and features on the website may require payment.
              Detailed information regarding the payment and refund policy can
              be found at [link to Payment and Refund Policy].
            </p>

            <p>
              <strong>8. Termination</strong>
            </p>
            <p>
              We reserve the right to terminate or restrict your access to the
              website, without notice, for any or no reason whatsoever.
            </p>

            <p>
              <strong>9. Disclaimer of Warranties</strong>
            </p>
            <p>
              The website and its content are provided "as is" and without
              warranties of any kind, either express or implied.
            </p>

            <p>
              <strong>10. Limitation of Liability</strong>
            </p>
            <p>
              In no event will the website, its affiliates, or their licensors,
              service providers, employees, agents, officers, or directors be
              liable for damages of any kind arising from the use of the website
              or its content.
            </p>

            <p>
              <strong>11. Changes to the Agreement</strong>
            </p>
            <p>
              We reserve the right to modify this User Agreement at any time.
              Any changes will be posted on this page and your continued use of
              the website signifies your agreement to the changes.
            </p>

            <p>
              <strong>12. Contact Information</strong>
            </p>
            <p>
              For any questions regarding this User Agreement, please contact us
              at [contact information].
            </p>
          </Modal>
        </Checkbox>
      </Form.Item>
      <Form.Item {...tailFormItemLayout}>
        <Button type="primary" htmlType="submit">
          Register
        </Button>
      </Form.Item>
    </Form>
  );
};
export default Register;
