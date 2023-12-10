import React, { useState } from "react";
import makeRequest from '../makeRequest'
import { Layout } from "antd";
import TutorHome from "./TutorHome";
import TutorNavBar from "../NavBar/TutorNavBar";

const { Footer } = Layout;

function TutorDashboard() {
  const styles = {
    layout: {
      backgroundColor: "#f4f4f4",
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
      <TutorNavBar />
      <div style={styles.content}>
        <TutorHome />
      </div>
      {/* <Footer style={styles.footer}>Tutor Management System ©2023</Footer> */}
    </Layout>
  );
}

export default TutorDashboard;