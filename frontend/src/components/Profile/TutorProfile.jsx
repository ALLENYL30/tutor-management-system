import React, { useState } from 'react';
import { UploadOutlined } from '@ant-design/icons';
import {
  Button,
  Form,
  Input,
  Select,
  Upload,
} from 'antd';
import TutorNavBar from "../NavBar/TutorNavBar";
import { useNavigate } from 'react-router-dom'
import { fileToDataUrl } from "../readImage";
import makeRequestWithToken from '../makeRequestWithToken';
import "./TutorProfile.css";

function TutorProfile() {
  const [profileImg, setProfileImg] = useState("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAWElEQVR42mNkwA/qgbgRnwJGAgb8BwI7RkbGw5QYUAs0oGXUAPwGgKKqgYF0ANLTyAi1xhZI2WOYzsjYDJTbC2QewGHIwcERBsPcgHqgAX8pMQAcxfhyIwATTkxL+hgX2QAAAABJRU5ErkJggg==")
  const [profileFormDisabled, setProfileFormDisabled] = useState(true);
  const [buttonText, setButtonText] = useState("Edit");

  const [username, setUsername] = useState("");
  const [address, setAddress] = useState("");
  const [phone, setPhone] = useState("");
  const [selectedTimezone, setSelectedTimezone] = useState("");
  const [bio, setBio] = useState("");
  const [certificateFile, setCertificateFile] = useState("");

  const { TextArea } = Input;

  // fetch the profile when first loading the page
  React.useEffect(() => {
    async function fetchProfile() {
      const url = "pull/profile";
      const token = localStorage.getItem("token");
      const response = await makeRequestWithToken("POST", url, undefined, token);
      console.log('fetch profile when reload', response);
      if (response.code === "200") {
        const profileContent = response.result;
        setUsername(profileContent.userName);
        setAddress(profileContent.address);
        setPhone(profileContent.phone);
        setSelectedTimezone(profileContent.timeZone);
        setBio(profileContent.bio);
        if (profileContent.image !== "data:image/png;base64,") {
          setProfileImg(profileContent.image);
        } else {
          setProfileImg("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAWElEQVR42mNkwA/qgbgRnwJGAgb8BwI7RkbGw5QYUAs0oGXUAPwGgKKqgYF0ANLTyAi1xhZI2WOYzsjYDJTbC2QewGHIwcERBsPcgHqgAX8pMQAcxfhyIwATTkxL+hgX2QAAAABJRU5ErkJggg==");
        }
        setCertificateFile(profileContent.filePath);
      }
    }
    fetchProfile();

  }, [])


  const navigate = useNavigate();
  function goBackToTutorDashboard() {
    navigate('/tutor/dashboard');
  }

  // edit or save profile
  function editOrSaveButton() {
    // console.log(buttonText);
    if (buttonText === "Edit") {
      setButtonText("Save");
      setProfileFormDisabled(false);

    } else {
      setButtonText("Edit");
      setProfileFormDisabled(true);
      const value = {
        userName: username,
        address: address,
        phone: phone,
        timeZone: selectedTimezone,
        bio: bio,
        filePath: certificateFile,
        image: profileImg.slice(22),
      }
      // to do 
      // post the new profile to the server
      console.log(value);
      uploadProfile(value);
    }
  }

  async function changeProfilePhote(info) {
    // console.log("file", info);
    if (info.file && info.file.status === 'done') {
      // info.file.originFileObj contains the uploaded file
      const newThumbnail = await fileToDataUrl(info.file.originFileObj);
      // console.log(newThumbnail);
      setProfileImg(newThumbnail);
    }
  }

  async function changeCertificateFile(info) {
    console.log("file", info);
    if (info.file && info.file.status === 'done') {
      // info.file.originFileObj contains the uploaded file
      // const newThumbnail = await fileToDataUrl(info.file.originFileObj);
      // // console.log(newThumbnail);
      setCertificateFile(info.file.name);
    }
  }

  function customRequest({ file, onSuccess }) {
    // console.log('file:', file)
    setTimeout(() => {
      onSuccess();
    }, 200);
  };

  async function uploadProfile(body) {
    const url = "update/profile";
    const token = localStorage.getItem("token");
    const response = await makeRequestWithToken("POST", url, body, token);
    // console.log('profile',response);
    if (response.code === "200") {
      console.log("change profile success");
    } else {
      alert("contain invalid parameter(s)");
    }
  }

  // profile form elements
  function profileForm() {
    return (
      <>
        <Form
          labelCol={{
            span: 4,
          }}
          wrapperCol={{
            span: 14,
          }}
          layout="horizontal"
          disabled={profileFormDisabled}
          style={{
            maxWidth: 600,
          }}
        >
          <div style={{ display: "flex", justifyContent: "center" }}>
            <Form.Item label="">
              <Upload
                customRequest={customRequest}
                onChange={changeProfilePhote}
                maxCount={1}
                showUploadList={false} // You can customize how the uploaded files are displayed
              >
                <Button icon={<UploadOutlined />}>Upload Profile Photo</Button>
              </Upload>
            </Form.Item>
          </div>

          <Form.Item label="username">
            <Input value={username} onChange={(e) => setUsername(e.target.value)} />
          </Form.Item>

          <Form.Item label="address">
            <Input value={address} onChange={(e) => setAddress(e.target.value)} />
          </Form.Item>

          <Form.Item label="phone">
            <Input value={phone} onChange={(e) => setPhone(e.target.value)} />
          </Form.Item>

          <Form.Item label="timezone">
            <Select value={selectedTimezone} onChange={(value) => setSelectedTimezone(value)}>
              <Select.Option value="Macquarie">Macquarie</Select.Option>
              <Select.Option value="Adelaide">Adelaide</Select.Option>
              <Select.Option value="Brisbane">Brisbane</Select.Option>
              <Select.Option value="Broken Hill">Broken Hill</Select.Option>
              <Select.Option value="Darwin">Darwin</Select.Option>
              <Select.Option value="Eucla">Eucla</Select.Option>
              <Select.Option value="Hobart">Hobart</Select.Option>
              <Select.Option value="Lindeman">Lindeman</Select.Option>
              <Select.Option value="Lord Howe">Lord Howe</Select.Option>
              <Select.Option value="Melbourne">Melbourne</Select.Option>
              <Select.Option value="Perth">Perth</Select.Option>
              <Select.Option value="Sydney">Sydney</Select.Option>
            </Select>
          </Form.Item>

          <Form.Item label="bio">
            <TextArea rows={4} value={bio} onChange={(e) => setBio(e.target.value)} />
          </Form.Item>

          <Form.Item label="certificate">
            <Upload
              customRequest={customRequest}
              onChange={changeCertificateFile}
              maxCount={1}
              showUploadList={false} // You can customize how the uploaded files are displayed
            >
              <Button icon={<UploadOutlined />}>Upload Certificate</Button>
              {certificateFile && <p>{certificateFile}</p>}
            </Upload>
          </Form.Item>
        </Form>
      </>
    );
  };

  return (
    <div className="profile-div">
      <TutorNavBar />
      <div className="main-content-div">
        <div className="profile">
          <div className="profileImg">
            <img className="profilePhoto" src={profileImg} alt="" />
          </div>
          <div>
            {profileForm()}
          </div>
         
          <div className="stuProfileButtonEvent">
            <Button type="default" className="stuProfileButton" onClick={editOrSaveButton}>
              {buttonText}
            </Button>
            <Button type="default" className="stuProfileButton" onClick={goBackToTutorDashboard}>
              Back
            </Button>
          </div>
        </div>
      </div>
    </div>
  )
}

export default TutorProfile;