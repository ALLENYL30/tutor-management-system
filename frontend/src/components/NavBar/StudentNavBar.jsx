import React, { useState, useEffect, useRef } from "react";
import { useNavigate } from "react-router-dom";
import { Menu, Dropdown, Layout, notification } from "antd";
import makeRequestWithToken from "../makeRequestWithToken";

const { Header } = Layout;

function StudentNavBar() {
  const navigate = useNavigate();

  const openNotificationWithIcon = (type, message, description) => {
    notification[type]({
      message: message,
      description: description,
    });
  };
  function logout() {
    navigate("/home");
  }

  function goToProfile() {
    console.log("go to profile");
    navigate("/student/profile");
  }

  function goToDashboard() {
    console.log("go to profile");
    navigate("/student/dashboard");
  }

  function goToCourses() {
    navigate("/student/course");
  }

  function goToSearchTutors() {
    navigate("/student/tutors");
  }

  function goToChat() {
    navigate("/student/chat");
  }

  // fetch message
  const fetchNewMessages = async () => {
    const token = localStorage.getItem("token");
    const response = await makeRequestWithToken(
      "POST",
      "/pull/newMsg",
      {},
      token
    );

    if (response.code === "200") {
      const newMessages = response.result.conversationList;

      if (newMessages.length > 0) {
        newMessages.forEach((msg) => {
          openNotificationWithIcon(
            "info",
            "New message from " + msg.userName,
            msg.message
          );
        });
      }
    }
  };

  const styles = {
    layout: {
      backgroundColor: "#f4f4f4",
    },
    header: {
      backgroundColor: "#001529",
      position: "fixed",
      zIndex: 1,
      width: "100%",
    },
  };

  const menu = (
    <Menu>
      <Menu.Item key="1" onClick={logout}>
        Logout
      </Menu.Item>
    </Menu>
  );

  useEffect(() => {
    const intervalId = setInterval(() => {
      fetchNewMessages();
    }, 2000);

    // Clear interval on component unmount or dependency change
    return () => clearInterval(intervalId);
  }, []);

  return (
    <Header style={styles.header}>
      <Menu theme="dark" mode="horizontal" defaultSelectedKeys={["1"]}>
        <Menu.Item key="1" onClick={goToDashboard}>
          Dashboard
        </Menu.Item>
        <Menu.Item key="2" onClick={goToSearchTutors}>
          Find Tutors
        </Menu.Item>
        <Menu.Item key="3" onClick={goToCourses}>
          Courses
        </Menu.Item>
        {/* <Menu.Item key="4">My Sessions</Menu.Item> */}
        <Menu.Item key="4" onClick={goToProfile}>
          Profile
        </Menu.Item>
        <Menu.Item key="5" onClick={goToChat}>
          Chat
        </Menu.Item>
        <Menu.Item key="6" style={{ float: "right" }}>
          <Dropdown overlay={menu} trigger={["click"]}>
            <span style={{ color: "white", cursor: "pointer" }}>
              Dear Student
            </span>
          </Dropdown>
        </Menu.Item>
      </Menu>
    </Header>
  );
}

export default StudentNavBar;
