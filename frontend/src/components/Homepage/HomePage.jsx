import React from "react";
import { Button, Card, Avatar, Carousel } from "antd";
import "./hero.css";
import {
  EditOutlined,
  EllipsisOutlined,
  SettingOutlined,
} from "@ant-design/icons";
import { Layout, Row, Col } from "antd";

import icon1 from "./image/att.svg";
import icon2 from "./image/samsung.svg";
import icon3 from "./image/citi.svg";
import icon4 from "./image/ericsson.svg";
import img1 from "./image/1.png";
import img2 from "./image/2.png";
import img3 from "./image/3.png";
import img4 from "./image/4.png";
import Header from "./Header";
import tutor1 from "./image/tutor1.png";
import tutor2 from "./image/tutor2.png";
import tutor3 from "./image/tutor3.png";
import tutor4 from "./image/tutor4.png";
import tutor5 from "./image/tutor5.png";
import tutor6 from "./image/tutor6.png";

const Hero = () => (
  <div className="hero-section">
    <div className="hero-text-div">
      <h1>Learn English Online</h1>
      <p>Connect with native speakers worldwide</p>
      <Button type="primary" size="large" block="true">
        Get Started
      </Button>
    </div>
  </div>
);

const contentStyle = {
  height: "160px",
  color: "#fff",
  lineHeight: "160px",
  textAlign: "center",
  background: "#364d79",
};

const Price = () => (
  <Carousel autoplay>
    <div>
      <h3 style={contentStyle}>1</h3>
    </div>
    <div>
      <h3 style={contentStyle}>2</h3>
    </div>
    <div>
      <h3 style={contentStyle}>3</h3>
    </div>
    <div>
      <h3 style={contentStyle}>4</h3>
    </div>
  </Carousel>
);

const Postvideo = () => {
  // Styles for the homepage container
  const homepageContainerStyle = {
    display: "flex",
    justifyContent: "center", // Center children horizontally
    alignItems: "center", // Center children vertically
    height: "auto", // Take up the full viewport height
  };

  // Styles for the video container
  const videoContainerStyle = {
    width: "33.33vw", // 33.33% of viewport width
    height: "21vw", // 33.33% of viewport height
    overflow: "hidden",
    position: "relative",
    border: "5px solid #333", // Add a frame
  };

  // Styles for the iframe to make it fit the video container
  const iframeStyle = {
    position: "absolute",
    top: 0,
    left: 0,
    width: "100%",
    height: "100%",
  };

  return (
    <div style={homepageContainerStyle}>
      <div style={videoContainerStyle}>
        <iframe
          width="100%"
          height="100%"
          src="https://www.youtube.com/embed/LnvSNO-PUsg"
          frameBorder="0"
          allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
          allowFullScreen
          title="YouTube Video"
          style={iframeStyle}
        ></iframe>
      </div>
    </div>
  );
};

const Features = () => (
  <div className="features-section" style={{ padding: "50px" }}>
    <Card title="Flexible Schedule" bordered={false} style={{ margin: "20px" }}>
      <p>Learn at your own pace with our flexible scheduling options.</p>
    </Card>
    <Card
      title="Experienced Tutors"
      bordered={false}
      style={{ margin: "20px" }}
    >
      <p>Our tutors are certified and experienced in teaching English.</p>
    </Card>
    {/* ... more feature cards */}
  </div>
);
const { Meta } = Card;

const TutorCards = () => (
  <div style={{ textAlign: "center", padding: "50px" }}>
    <h2 style={{ fontSize: "36px", fontWeight: "bold", color: "DeepSkyBlue " }}>
      Choose Your Favourite Tutor
    </h2>
    <div
      style={{
        display: "flex",
        justifyContent: "center",
        gap: "20px",
        flexWrap: "wrap",
      }}
    >
      <Card
        hoverable
        style={{ width: 240 }}
        cover={<img alt="tutor1" src={tutor4} />}
      >
        <Meta
          title="Tutor Mike"
          description="Hi there. My name is Mike and I am from Toronto, Canada. I speak English, French, and some basic Korean. I have lived in South Korea, UAE and Mauritius and now I live in Panama where I am learning Spanish.  "
        />
      </Card>
      <Card
        hoverable
        style={{ width: 240 }}
        cover={<img alt="example" src={tutor5} />}
      >
        <Meta
          title="Teacher Alex"
          description="Hello, my name is Alex. I am a TEFL certified English teacher that loves to make learning fun and engaging. Together with my wife and 6 year old daughter I like to explore the world. "
        />
      </Card>
      <Card
        hoverable
        style={{ width: 240 }}
        cover={<img alt="example" src={tutor6} />}
      >
        <Meta
          title="Ms Gypsy"
          description="Fluent American English! Hi, I am Ms.Gypsy. I love how languages around the world can connect different cultures. I enjoy teaching and I have experience teaching English to children and adults.  "
        />
      </Card>
    </div>
  </div>
);

const PriceCards = () => (
  <div style={{ textAlign: "center", padding: "50px" }}>
    <h2 style={{ fontSize: "36px", fontWeight: "bold", color: "DeepSkyBlue " }}>
      Choose Your Study Plan
    </h2>
    <div
      style={{
        display: "flex",
        justifyContent: "center",
        gap: "20px",
        flexWrap: "wrap",
      }}
    >
      {/* ... your price cards */}
      <Card style={{ width: 300 }} cover={<img alt="example" src={tutor1} />}>
        <Meta
          avatar={
            <Avatar src="https://xsgames.co/randomusers/avatar.php?g=pixel" />
          }
          title="Basic Plan"
          description="Access to core courses focusing on workplace English, including intermediate grammar and professional vocabulary."
        />
      </Card>
      <Card style={{ width: 300 }} cover={<img alt="example" src={tutor2} />}>
        <Meta
          avatar={
            <Avatar src="https://xsgames.co/randomusers/avatar.php?g=pixel" />
          }
          title="Standard Plan"
          description="Comprehensive access to advanced business English courses, including specialized vocabulary and professional communication skills."
        />
      </Card>
      <Card style={{ width: 300 }} cover={<img alt="example" src={tutor3} />}>
        <Meta
          avatar={
            <Avatar src="https://xsgames.co/randomusers/avatar.php?g=pixel" />
          }
          title="Premium Plan"
          description="All-encompassing access to premium courses, including advanced business English and professional jargon across various industries"
        />
      </Card>
    </div>
  </div>
);

const Footer = () => (
  <div
    className="footer"
    style={{
      padding: "40px 0",
      background: "#f4f4f4",
      borderTop: "1px solid #e8e8e8",
    }}
  >
    <Row justify="space-around" align="middle" style={{ textAlign: "center" }}>
      <Col span={8}>
        <h3 style={{ marginBottom: "16px" }}>Group Superstars</h3>
        <p>
          <a href="https://www.unsw.edu.au/about-us">About Us</a>
        </p>
        <p>
          {" "}
          <a href="https://www.jobs.unsw.edu.au/">Careers</a>
        </p>
        <p>
          {" "}
          <a href="https://www.unsw.edu.au/about-us/our-story/contact-us">
            Contact Us
          </a>
        </p>

        <p>More...</p>
      </Col>
      <Col span={8}>
        <h3 style={{ marginBottom: "16px" }}>UNSW</h3>
        <p>
          <a href="https://www.unsw.edu.au/study">UNSW Global</a>
        </p>
        <p>
          <a href="https://www.unsw.edu.au/study">
            UNSW Institute of Languages
          </a>
        </p>
        <a href="https://www.unsw.edu.au/study">UNSW Global Assessments</a>

        <p>More...</p>
      </Col>
      <Col span={8}>
        <h3 style={{ marginBottom: "16px" }}>Resources</h3>
        <p>
          {" "}
          <a href="https://www.oracle.com/">Oracle</a>
        </p>
        <p>
          {" "}
          <a href="https://react.dev/">React</a>
        </p>
        <p>
          {" "}
          <a href="https://www.mongodb.com/">MongoDB</a>
        </p>
        <p>More...</p>
      </Col>
    </Row>
    <div style={{ textAlign: "center", marginTop: "40px" }}>
      <p>Superstar © COPYRIGHT 2023. ALL RIGHTS RESERVED.</p>
    </div>
  </div>
);

{
  /* 
  控制主页中的走马灯
*/
}

const contentStyle1 = {
  height: "160px",
  color: "grey",
  background: "#364d79",
  display: "flex",
  flexDirection: "column",
  justifyContent: "center",
  alignItems: "center",
  lineHeight: "normal", // 设置为 normal，以避免影响居中
};

const iconRowStyle = {
  display: "flex",
  justifyContent: "space-around",
  width: "100%",
};

const Userexperience = () => (
  <>
    <style>
      {`
        .ant-carousel .slick-dots {
          bottom: 25px;
          z-index: 2;
        }
        .ant-carousel .slick-dots li button {
          background-color: yellow;
        }
        .ant-carousel .slick-prev,
        .ant-carousel .slick-next {
          top: 40%;
          color: black;
          background-color: rgba(0, 0, 0, 0.4);
          z-index: 2;
        }
        .ant-carousel h3 {
          margin: 0;
          display: flex;
          align-items: center;
          justify-content: center;
          height: 100%;
        }
        .iconRowStyle img {
          max-width: 20%;
          border: 1px solid grey;
        }
        .ant-carousel .slick-slide div h3 {
          margin: 0;
          display: flex;
          align-items: center;
          justify-content: center;
          height: 100%;
        }
      `}
    </style>

    <Carousel autoplay autoplaySpeed={5000}>
      <div style={contentStyle1}>
        <h3>
          Trusted by over 14,400 companies and millions of learners worldwide
        </h3>
        <div style={iconRowStyle}>
          <img src={icon1} alt="Icon 1" />
          <img src={icon2} alt="Icon 2" />
          <img src={icon3} alt="Icon 3" />
          <img src={icon4} alt="Icon 4" />
        </div>
      </div>
      <div style={contentStyle1}>
        <h3>Learn from 250 leading institutions</h3>
        <div style={iconRowStyle}>
          <img src={img1} alt="Icon 1" />
          <img src={img2} alt="Icon 2" />
          <img src={img3} alt="Icon 3" />
          <img src={img4} alt="Icon 4" />
        </div>
      </div>
    </Carousel>
  </>
);

const HomePage = () => {
  return (
    <div className="home-page">
      <Header />
      <Hero />
      <Postvideo />
      <TutorCards />
      <Userexperience />
      <PriceCards />

      <Footer />
    </div>
  );
};

export default HomePage;
