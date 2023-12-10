// External dependencies
import React, { useState } from "react";
import { message, Popconfirm } from "antd";

// Ant Design icons
import {
  MenuFoldOutlined,
  MenuUnfoldOutlined,
  UnorderedListOutlined,
  UserOutlined,
  DeleteOutlined,
  UploadOutlined,
  CloudUploadOutlined,
} from "@ant-design/icons";

// Ant Design components
import { Layout, Menu, Upload, Col, Drawer, Form, Input, Row, Image } from "antd";
import { Space, Table, Tag, Card, Button, Popover } from "antd";

// Custom functions
import makeRequestWithToken from "../makeRequestWithToken";
import { fileToDataUrl } from "../readImage";

// CSS
import "./AdminPage.css";

const { Meta } = Card;
const { Header, Sider, Content } = Layout;

const columns = [
  {
    title: "Name",
    dataIndex: "userName",
    key: "userName",
    render: (text) => <a>{text}</a>,
  },
  {
    title: "Account",
    dataIndex: "account",
    key: "account",
  },
  {
    title: "Password",
    dataIndex: "passwordMd5",
    key: "passwordMd5",
  },
  {
    title: "Action",
    key: "action",
    render: (_, record) => (
      <Space size="middle">
        <a style={{ color: "red" }}>Delete</a>
      </Space>
    ),
  },
];
const defaultStudentData = [
  //学生信息
  {
    key: "1",
    userName: "student1",
    account: "student1@qq.com",
    passwordMd5: "e10adc3949ba59abbe56e057f20f883e",
  },
  {
    key: "2",
    userName: "student2",
    account: "student2@qq.com",
    passwordMd5: "e80b5017098950fc58aad83c8c14978e",
  },
  {
    key: "3",
    userName: "student3",
    account: "student 3@qq.com",
    passwordMd5: "d8578edf8458ce06fbc5bb76a58c5ca4",
  },
];
const defaultTutorData = [
  //tutor信息
  {
    key: "0",
    userName: "tutor1",
    account: "tutor1@qq.com",
    passwordMd5: "e10adc3949ba59abbe56e057f20f883e",
  },
  {
    key: "1",
    userName: "tutor2",
    account: "tutor2@qq.com",
    passwordMd5: "e80b5017098950fc58aad83c8c14978e",
  },
  {
    key: "2",
    userName: "tutor3",
    account: "tutor3@qq.com",
    passwordMd5: "d8578edf8458ce06fbc5bb76a58c5ca4",
  },
];

const defaultCourseInfo = [//课程信息
  {
    name: "Business English",
    description: "This is the description",
    courseType: "CSE,Exam",
    image:
      "https://gw.alipayobjects.com/zos/rmsportal/JiqGstEfoWAOHiTxclqi.png",
  },
  {
    name: "Academic English",
    description: "This is the description",
    courseType: "CSE,Exam",
    image:
      "https://gw.alipayobjects.com/zos/rmsportal/JiqGstEfoWAOHiTxclqi.png",
  },
  {
    name: "IELTS-listening",
    description: "This is the description",
    courseType: "CSE,Exam",
    image:
      "https://gw.alipayobjects.com/zos/rmsportal/JiqGstEfoWAOHiTxclqi.png",
  },
];

function AdminPage() {
  const [collapsed, setCollapsed] = useState(false);
  const [size, setSize] = useState("large");
  const [open, setOpen] = useState(false);
  const [courseName, setCourseName] = useState("");
  const [courseType, setCourseType] = useState("");
  const [description, setDescription] = useState("");
  const [courseImage, setCourseImage] = useState("");

  const [studentData, setStudentData] = useState(defaultStudentData);
  const [tutorData, setTutorData] = useState(defaultTutorData);
  const [courseData, setCourseData] = useState(defaultCourseInfo);

  // control which content to show in the page
  const [showStudentInfo, setShowStudentInfo] = useState(true);
  const [showTutorInfo, setShowTutorInfo] = useState(false);
  const [showAllCourses, setshowAllCourses] = useState(false);

  const [form] = Form.useForm();

  React.useEffect(() => {
    console.log("reload admin page");
    fetchStudent();
  }, [])

  /*******************************************************************************
  *                                Student functions                               *
  *******************************************************************************/

  function allStudentInfo() {
    return (
      <div className="allStudentInfo">
        <Table columns={columns} dataSource={studentData} />
      </div>
    )
  }

  async function fetchStudent() {
    const url = "pull/student";
    const token = localStorage.getItem("token");

    const response = await makeRequestWithToken('POST', url, undefined, token);
    if (response.code === "200") {
      const studentBackendArray = response.result.list;
      // console.log(tutorBackendArray);
      let tempStudentArray = []
      for (let i = 0; i < studentBackendArray.length; i++) {
        const student = studentBackendArray[i]
        // console.log(student);
        const studentInfo = {
          key: i,
          userName: student.userName,
          account: student.account,
          passwordMd5: student.passwordMd5,
        }
        tempStudentArray.push(studentInfo);
      }
      console.log(tempStudentArray);
      setStudentData(tempStudentArray);

    } else {
      console.log(response);
    }
  }
  /* End of student functions */

  /*******************************************************************************
 *                                Tutor functions                               *
 *******************************************************************************/

  async function fetchTutor() {
    const url = "pull/account";
    const token = localStorage.getItem("token");

    const response = await makeRequestWithToken('POST', url, undefined, token);
    if (response.code === "200") {
      const tutorBackendArray = response.result.tutorAccountDOList;
      // console.log(tutorBackendArray);
      let tempTutorArray = []
      for (let i = 0; i < tutorBackendArray.length; i++) {
        const tutor = tutorBackendArray[i]
        const tutorInfo = {
          key: i,
          userName: tutor.userName,
          account: tutor.account,
          passwordMd5: tutor.passwordMd5,
        }
        tempTutorArray.push(tutorInfo);
      }
      setTutorData(tempTutorArray);
      console.log('courseData:',courseData);

    } else {
      console.log(response);
    }
  }

  function allTutorInfo() {
    return (
      <div className="allTutorInfo">
        <Table columns={columns} dataSource={tutorData} />
      </div>
    )
  }
  /* End of tutor functions */


  /*******************************************************************************
 *                                 Course functions                              *
 *******************************************************************************/

  const confirm = (courseName) => {//删除课程成功
    message.success("Delete Success");
    deleteCourse(courseName)
  };
  const cancel = (e) => {//删除课程失败
    console.log(e);
    message.error("Cancel Delete");
  };

  const resetForm = () => {
    setCourseName("");
    setCourseType("");
    setDescription("");
    setCourseImage("");
    form.resetFields();
    // console.log(courseName);
  };

  const showDrawer = () => {
    setOpen(true);
  };
  const onClose = () => {
    resetForm();
    setOpen(false);
  };

  // press submit button
  function addCourseSubmit() {
    // console.log(courseName);
    // console.log(courseType);
    // console.log(description);
    // console.log(courseImage);
    uploadCourse();
    onClose();
  }

  // upload course to backend 
  async function uploadCourse() {
    const url = "upload/course";
    const token = localStorage.getItem("token");
    const body = {
      "name": courseName,
      "description": description,
      "courseType": courseType,
      "image": courseImage.slice(22),
    }
    const response = await makeRequestWithToken("POST", url, body, token);
    // console.log('profile',response);
    if (response.code === "200") {
      console.log("upload course success");
      fetchCourse();
    } else {
      alert("Please fill in all the items!");
    }
  }

  function customRequest({ file, onSuccess }) {
    setTimeout(() => {
      onSuccess();
    }, 100);
  };

  async function changeCoursePhote(info) {
    if (info.file && info.file.status === 'done') {
      // info.file.originFileObj contains the uploaded file
      const newThumbnail = await fileToDataUrl(info.file.originFileObj);
      setCourseImage(newThumbnail);
    }
  }

  function addCourseDrawer() {
    return (
      <Drawer
        title="Create a new course"
        width={600}
        onClose={onClose}
        open={open}
        drawerStyle={{ paddingBottom: 80 }}
        extra={
          <Space>
            <Button onClick={onClose}>Cancel</Button>
            <Button onClick={addCourseSubmit} type="primary">
              Submit
            </Button>
          </Space>
        }
      >
        <Form layout="vertical"
          form={form}
          requiredMark={true}
        >
          <Row gutter={16}>
            <Col span={12}>
              <Form.Item
                name="name"
                label="Course Name"
                rules={[{ required: true, message: 'Please enter course name' }]}
              >
                <Input placeholder="Please enter course name" value={courseName} onChange={(e) => setCourseName(e.target.value)} />
              </Form.Item>
            </Col>
          </Row>
          <Row gutter={16}>
            <Col span={12}>
              <Form.Item
                name="type"
                label="Course Type"
                rules={[{ required: true, message: 'Please enter course type' }]}
              >
                <Input placeholder="Please enter course type" value={courseType} onChange={(e) => setCourseType(e.target.value)} />
              </Form.Item>
            </Col>
          </Row>
          <Row gutter={16}>
            <Col span={24}>
              <Form.Item
                name="description"
                label="Description"
                rules={[{ required: true, message: 'Please enter description' }]}
              >
                <Input.TextArea
                  rows={4}
                  placeholder="please enter description"
                  value={description}
                  onChange={(e) => setDescription(e.target.value)}
                />
              </Form.Item>
            </Col>
          </Row>
          <Form.Item
            label="upload image"
            name="image"
            rules={[{ required: true, message: 'Please upload an image' }]}
          >
            <Upload
              customRequest={customRequest}
              onChange={changeCoursePhote}
              showUploadList={false}
              maxCount={1}
            >
              <Button icon={<UploadOutlined />}>Upload Profile Photo</Button>
            </Upload>
          </Form.Item>
        </Form>
        {courseImage && <Image src={courseImage} alt=""></Image>}
      </Drawer>
    )
  }

  async function fetchCourse() {
    console.log("fetch courses");
    const url = "pull/courses";
    const token = localStorage.getItem("token");

    const response = await makeRequestWithToken('POST', url, undefined, token);
    if (response.code === "200") {
      const courseBackendArray = response.result.list;
      console.log(courseBackendArray);
      let tempCourseArray = []
      for (let i = 0; i < courseBackendArray.length; i++) {
        const course = courseBackendArray[i]
        if (course.isDelete !== "TRUE") {
          const courseInfo = {
            name: course.name,
            description: course.description,
            courseType: course.courseType,
            image: course.image === null ? "https://gw.alipayobjects.com/zos/rmsportal/JiqGstEfoWAOHiTxclqi.png" : course.image,
          }
          tempCourseArray.push(courseInfo);
        }
      }
      // console.log(tempCourseArray)
      setCourseData(tempCourseArray);

    } else {
      console.log(response);
    }
  }

  // delete a course  
  async function deleteCourse(courseName) {
    const url = "delete/course";
    const token = localStorage.getItem("token");
    const body = {
      "name": courseName,
      "type": 0,
    }
    const response = await makeRequestWithToken('POST', url, body, token);
    if (response.code === "200") {
      console.log("delete course success");
      fetchCourse();
    } else {
      console.log("delete course fails")
    }
  }

  function coursePart() {
    return (
      <div className="coursePart">
        <Button
          style={{ width: 200 }}
          type="primary"
          shape="round"
          icon={<CloudUploadOutlined />}
          size={size}
          onClick={showDrawer}
        >
          Add
        </Button>
        {addCourseDrawer()}
        <div className="showCourse">
          {courseData.map((item, index) => (
            <Card
              key={index}
              className="courseCard"
              hoverable={true}
              cover={<Image width={300} height={190} alt="example" src={item.image} />}
              actions={[
                <Popconfirm
                  title="Are you sure to delete this course?"
                  onConfirm={() => { confirm(item.name) }}
                  onCancel={cancel}
                  okText="Yes"
                  cancelText="No"
                >
                  <Popover content={"delete"} trigger="hover">
                    <Button
                      style={{ border: "none" }}
                      icon={<DeleteOutlined />}
                    ></Button>
                  </Popover>
                </Popconfirm>,
              ]}
            >
              <Meta title={item.name} description={item.description} />
              <Tag style={{ margin: 10 }} color="magenta">
                {item.courseType}
              </Tag>
            </Card>
          ))}
        </div>
      </div>
    )
  }

  /*   End of course functions   */

  /*******************************************************************************
 *                                 Sidebar functions                              *
 *******************************************************************************/
  async function change(e) {
    // console.log(e.key);
    // student
    if (e.key === "1") {
      fetchStudent();
      setShowStudentInfo(true);
      setShowTutorInfo(false);
      setshowAllCourses(false);
    }

    // tutor
    if (e.key === "2") {
      fetchTutor();
      setShowStudentInfo(false);
      setShowTutorInfo(true);
      setshowAllCourses(false);
    }

    // course
    if (e.key === "3") {
      fetchCourse();
      setShowStudentInfo(false);
      setShowTutorInfo(false);
      setshowAllCourses(true);
    }
  };

  function siderBar() {
    return (
      <Sider trigger={null} collapsible collapsed={collapsed}
        style={{ position: 'sticky', top: 0, height: '100vh' }}
      >
        <div className="logo" />
        <Menu
          theme="dark"
          mode="inline"
          defaultSelectedKeys={["1"]}
          onClick={change}
          items={[
            {
              key: "1",
              icon: <UserOutlined />,
              label: "Student Information",
            },
            {
              key: "2",
              icon: <UserOutlined />,
              label: "Tutor Information",
            },
            {
              key: "3",
              icon: <UnorderedListOutlined />,
              label: "All Courses",
            },
          ]}
        />
      </Sider>
    )
  }

  /*   End of sidebar functions   */

  function adminHeader() {
    return (
      <Header
        className="site-layout-background"
        style={{
          padding: 0,
        }}
      >
        {React.createElement(
          collapsed ? MenuUnfoldOutlined : MenuFoldOutlined,
          {
            className: "trigger",
            onClick: () => setCollapsed(!collapsed),
          }
        )}
      </Header>
    )
  }

  return (
    <div style={{ display: "flex" }}>
      <Layout>
        {siderBar()}
        <Layout className="site-layout">
          {adminHeader()}
          <Content
            className="site-layout-background"
            style={{
              margin: "24px 16px",
              padding: 24,
              minHeight: 280,
            }}
          >
            {showStudentInfo && allStudentInfo()}
            {showTutorInfo && allTutorInfo()}
            {showAllCourses && coursePart()}
          </Content>
        </Layout>
      </Layout>
    </div>
  );
}
export default AdminPage;
