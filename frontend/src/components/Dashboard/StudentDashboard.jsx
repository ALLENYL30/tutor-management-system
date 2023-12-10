import React from "react";
import { Layout } from "antd";
import StudentNavBar from "../NavBar/StudentNavBar";
import StudentHome from "./StudentHome";
const { Footer } = Layout;

function StudentDashboard() {
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
    content: {
      paddingTop: "80px",
    },
    footer: {
      backgroundColor: "#001529",
      color: "#fff",
      textAlign: "center",
    },
  };

  return (
    <Layout style={styles.layout}>
      <StudentNavBar />
      <div style={styles.content}>
        <StudentHome />
      </div>
      {/* <Footer style={styles.footer}>Tutor Management System ©2023</Footer> */}
    </Layout>
  );
}

export default StudentDashboard;