import React from "react";
import { Button, Layout, Typography } from "antd";
import { useNavigate } from 'react-router-dom'
import StudentCheckBooking from "./StudentCheckBooking";

const { Content } = Layout;
const { Title } = Typography;

function StudentHome() {
  const styles = {
    content: {
      padding: "24px",
      backgroundColor: "#fff",
      textAlign: "center",
      minHeight: "380px",
    },
  };

  const navigate = useNavigate();
  function goToSearchTutors() {
    navigate('/student/tutors');
  }

  return (
    <Content style={styles.content}>
      <Title>Welcome to the Student Dashboard</Title>
      <StudentCheckBooking></StudentCheckBooking>
      {/* <Button type="primary" onClick={goToSearchTutors}>Find a Tutor</Button> */}
    </Content>
  );
}

export default StudentHome;