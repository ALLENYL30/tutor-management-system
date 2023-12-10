import React, { useState } from "react";
import { Button, Layout, Typography } from "antd";

import TutorCheckALLBooking from "./TutorCheckALLBooking";
import TutorUnconfirmedBooking from "./TutorUnconfirmedBooking";

const { Content } = Layout;
const { Title } = Typography;

function unConfirmedBooking() {
  return <TutorUnconfirmedBooking></TutorUnconfirmedBooking>;
}
function allBooking() {
  return <TutorCheckALLBooking></TutorCheckALLBooking>;
}

function TutorHome() {
  const styles = {
    content: {
      padding: "24px",
      backgroundColor: "#fff",
      textAlign: "center",
      minHeight: "380px",
    },
    button: {
      width: "220px",
      margin: "10px",
    }
  };
  const [showUnConfirmedBooking, setShowUnConfirmedBooking] = useState(true);
  const [showAllBooking, setShowAllBooking] = useState(false);

  function swithToAllBooking() {
    setShowAllBooking(true);
    setShowUnConfirmedBooking(false);
  }

  function swithToUnConfirmedBooking() {
    setShowAllBooking(false);
    setShowUnConfirmedBooking(true);
  }

  return (
    <Content style={styles.content}>
      <Title>Welcome to the Tutor Dashboard</Title>

      <div style={{display: "flex", marginBottom: "13px"}}>
        <Button
          style={styles.button}
          type="default"
          onClick={swithToUnConfirmedBooking}
        >
          Unconfirmed Appointments
        </Button>

        <Button
          style={styles.button}
          type="default"
          onClick={swithToAllBooking}
        >
          All Appointments
        </Button>
      </div>

      {showUnConfirmedBooking && unConfirmedBooking()}
      {showAllBooking && allBooking()}
    </Content>
  );
}

export default TutorHome;
