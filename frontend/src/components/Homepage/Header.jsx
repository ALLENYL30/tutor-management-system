import { useNavigate } from 'react-router-dom'
import React from "react";
import { Menu, Button } from "antd";

const Header = () => {
  const navigate = useNavigate();
  function goToRegisterPage() {
    navigate('/register');
  }

  function goToLoginPage() {
    navigate('/login');
  }

  function goToHomePage() {
    navigate('/home');
  }

  return (
    <div
      style={{
        display: "flex",
        justifyContent: "space-between",
        alignItems: "center", // Align items vertically
        padding: "10px", // (可选) 填充
      }}
    >
      <div style={{ display: "flex", alignItems: "center" }}>
        <img
          src="unsw_0.png"
          alt="Logo"
          onClick={goToHomePage}
          style={{
            marginRight: "20px", // Space between the logo and the Menu
            width: "200px", // Setting a specific width
            cursor: 'pointer', // Add cursor style to indicate it's clickable
          }}
        />
        <Menu
          mode="horizontal"
          items={[
            { key: "1", label: "Home", onClick: goToHomePage },
            { key: "2", label: "Course" },
            { key: "3", label: "Tutors" },
            { key: "4", label: "Resources" },
          ]}
          style={{
            border: "none",
            flex: 1,
            fontSize: "16px",
          }}
        />
      </div>
      <div
        style={{
          display: "flex",
          alignItems: "center", // Align items vertically
          gap: "16px", // Space between items
          fontSize: "16px", // 增大字体
          marginRight: "16px",
        }}
      >
        <Button type="primary" onClick={goToLoginPage}>Login</Button>
        <Button type="primary" onClick={goToRegisterPage}>Sign Up</Button>
      </div>
    </div>
  );
};

export default Header;