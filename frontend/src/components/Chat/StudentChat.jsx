import React, { useState, useEffect, useRef } from "react";
import { Layout, List, Input, Button, notification } from "antd";
import makeRequestWithToken from "../makeRequestWithToken";
import { useNavigate } from "react-router-dom"; // Import useHistory

import "./StudentChat.css";
import logo from "./1.png";
const { Sider, Content } = Layout;

function Chat() {
  const messagesEndRef = useRef(null);
  const [contacts, setContacts] = useState([]);
  const [selectedReceiver, setSelectedReceiver] = useState(null); // 添加 selectedReceiver 状态
  const [currentMessage, setCurrentMessage] = useState("");
  const [conversation, setConversation] = useState([]);
  const [serverResponse, setServerResponse] = useState(null);
  const [username, setUsername] = useState("");
  const [searchQuery, setSearchQuery] = useState("");
  const navigate = useNavigate(); // Initialize useNavigate
  const navigateToDashboard = () => {
    navigate("/student/dashboard"); // Replace '/dashboard' with your dashboard route
  };

  //获取用户名
  const fetchUserProfile = async () => {
    const token = localStorage.getItem("token");
    if (!token) {
      console.error("No token found");
      return;
    }

    try {
      const response = await makeRequestWithToken(
        "POST",
        "/pull/profile",
        {},
        token
      );

      if (response.code === "200") {
        console.log("Profile fetched successfully");
        return response.result; // 包含用户资料的对象
      } else {
        console.error("Error fetching profile:", response.message);
      }
    } catch (error) {
      console.error("Error fetching profile:", error);
    }
  };
  useEffect(() => {
    fetchUserProfile().then((profile) => {
      if (profile) {
        setUsername(profile.userName);
      }
    });
  }, []);
  //滚轮更新
  const scrollToBottom = () => {
    messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
  };
  //弹窗
  const openNotificationWithIcon = (type, message, description) => {
    notification[type]({
      message: message,
      description: description,
    });
  };
  //获得最新的消息
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
        // Update conversation only if the new messages belong to the selected receiver
        if (selectedReceiver) {
          const relevantMessages = newMessages.filter(
            (msg) => msg.userName === selectedReceiver
          );
          if (relevantMessages.length > 0) {
            setConversation((prevConversation) => [
              ...prevConversation,
              ...relevantMessages,
            ]);
          }
        }

        // Update the contacts list
        const newContacts = newMessages
          .map((msg) => msg.userName)
          .filter((userName, index, self) => self.indexOf(userName) === index) // Get unique userNames
          .map((userName) => {
            // Here you need to get the contact's full data
            // This is a placeholder to find the contact by userName, you'll need to adjust this
            return (
              contacts.find((contact) => contact.userName === userName) || {
                userName,
              }
            );
          });

        setContacts((prevContacts) => {
          // This will create a new array with the new contacts at the beginning
          // followed by the previous contacts, excluding the new contacts.
          return [
            ...newContacts,
            ...prevContacts.filter(
              (contact) =>
                !newContacts.some(
                  (newContact) => newContact.userName === contact.userName
                )
            ),
          ];
        });
      }
    } else {
      // Handle any errors or different response codes here
      console.error("Error fetching new messages:", response.message);
    }
  };

  //发送消息
  const sendMessage = async () => {
    if (!selectedReceiver || currentMessage.trim() === "") {
      console.error("No receiver selected or message is empty");
      return;
    }

    const token = localStorage.getItem("token");
    const body = {
      receiver: selectedReceiver,
      message: currentMessage,
    };
    const response = await makeRequestWithToken(
      "POST",
      "update/newMsg",
      body,
      token
    );
    if (response.code === "200") {
      const newMessage = {
        userName: username,
        message: currentMessage,
        time: new Date().toISOString(),
      };
      setConversation((prevConversation) => [...prevConversation, newMessage]);
      setCurrentMessage("");
    } else {
      console.error("Error sending message:", response.message);
    }
  };

  //获取会话
  const fetchConversation = async (userName) => {
    const url = "/pull/chat";
    const token = localStorage.getItem("token");
    const body = {
      userName: userName,
    };

    const response = await makeRequestWithToken("POST", url, body, token);
    if (response.code === "200") {
      setConversation(response.result.conversationList);
    } else {
      console.error("Error fetching conversation:", response.message);
    }
  };
  const fetchContacts = async () => {
    const url = "pull/chatList";
    const token = localStorage.getItem("token");
    const response = await makeRequestWithToken("POST", url, undefined, token);
    setServerResponse(response); // 保存服务器响应数据
    if (response.code === "200") {
      setContacts(response.result.list);
    } else {
      console.error("Error fetching chat list:", response.message);
    }
  };
  useEffect(() => {
    fetchContacts();

    const intervalId = setInterval(() => {
      if (selectedReceiver) {
        fetchNewMessages();
      }
    }, 5000);

    // Clear interval when the component unmounts or selectedReceiver changes
    return () => clearInterval(intervalId);
  }, [selectedReceiver]);

  useEffect(() => {
    scrollToBottom();
  }, [conversation]);

  return (
    <Layout className="layout">
      <Sider theme="light" width={300} className="sider">
        <div className="logoContainer">
          <img src={logo} alt="Logo" className="logo" />
          <h1 className="title">Tutor Learning System</h1>
        </div>
        <Input
          className="searchInput"
          placeholder="Search contacts"
          value={searchQuery}
          onChange={(e) => setSearchQuery(e.target.value)}
        />
        <List
          className="contactList"
          dataSource={contacts.filter((contact) =>
            contact.userName.toLowerCase().includes(searchQuery.toLowerCase())
          )}
          renderItem={(contact) => (
            <List.Item
              className={`contactItem ${
                selectedReceiver === contact.userName ? "selected" : ""
              }`}
              key={contact.userId}
              onClick={() => {
                setSelectedReceiver(contact.userName);
                fetchConversation(contact.userName);
              }}
            >
              {contact.userName}
            </List.Item>
          )}
        />
        <Button
          className="dashboardButton"
          type="primary"
          onClick={navigateToDashboard}
        >
          Return to Dashboard
        </Button>
      </Sider>

      <Content className="content">
        <div className="messageBox">
          {selectedReceiver ? (
            conversation.map((msg, index) => (
              <div key={index} className="message">
                <strong>{msg.userName}</strong>
                <span> - {new Date(msg.time).toLocaleString()}:</span>
                <div>{msg.message}</div>
              </div>
            ))
          ) : (
            <div className="welcomeMessage">
              Welcome to the Online Tutoring System! Please select a contact to
              start discussing your learning journey.
            </div>
          )}
          <div ref={messagesEndRef} />
        </div>

        {selectedReceiver && (
          <div className="messageInput">
            <Input
              className="input"
              type="text"
              value={currentMessage}
              onChange={(e) => setCurrentMessage(e.target.value)}
              placeholder="Type a message"
            />
            <Button className="sendButton" onClick={sendMessage} type="primary">
              Send
            </Button>
          </div>
        )}
      </Content>
    </Layout>
  );
}

export default Chat;
