import React from 'react';
import StudentNavBar from '../NavBar/StudentNavBar';
import "./StudentCoursePage.css";
import { Button } from 'antd';
import AspectRatio from '@mui/joy/AspectRatio';
import Card from '@mui/joy/Card';
import CardContent from '@mui/joy/CardContent';
import IconButton from '@mui/joy/IconButton';
import Typography from '@mui/joy/Typography';
import BookmarkAdd from '@mui/icons-material/BookmarkAddOutlined';

import makeRequestWithToken from "../makeRequestWithToken";

function StudentCoursePage() {
  const [showAllCourses, setShowAllCourses] = React.useState(true);
  const [showEnrolledCourses, setShowEnrolledCourses] = React.useState(false);
  const [coursesArray, setCoursesArray] = React.useState([])
  const [enrolledCoursesArray, setEnrolledCoursesArray] = React.useState([])


  React.useEffect(() => {
    fetchAllCourses();

  }, []);

  async function fetchAllCourses() {
    const url = "pull/course";
    const token = localStorage.getItem("token");
    const response = await makeRequestWithToken('POST', url, undefined, token);
    if (response.code === "200") {
      // console.log('pull courses', response.result.list);
      const backEndCourses = response.result.list;
      // get courses
      let courses = []
      for (let i = 0; i < backEndCourses.length; i++) {
        let course = backEndCourses[i];
        if (course.image === null) {
          course.image = "https://gw.alipayobjects.com/zos/rmsportal/JiqGstEfoWAOHiTxclqi.png";
        }
        courses.push(course);
      }
      setCoursesArray(courses);
    } else {
      console.log(response);
    }
  }

  async function fetchEnrolledCourses() {
    const url = "pull/courses/common";
    const token = localStorage.getItem("token");
    const response = await makeRequestWithToken('POST', url, undefined, token);
    if (response.code === "200") {
      // console.log('enrolled courses', response.result.list);
      const enrolledCourseArray = response.result.list;
      // get courses
      let courses = [];
      for (let i = 0; i < enrolledCourseArray.length; i++) {
        let course = enrolledCourseArray[i];
        if (course.image === null) {
          course.image = "https://gw.alipayobjects.com/zos/rmsportal/JiqGstEfoWAOHiTxclqi.png";
        }
        course.choose = true;
        courses.push(course);
      }
      setEnrolledCoursesArray(courses)

    } else {
      console.log("fecth enrolled courses fail", response);
    }
  }

  // select courses and unselect courses
  function clickIconButton(isChosen, courseName) {
    // console.log('click icon');
    // console.log(isChosen);
    // console.log(courseName);

    if (isChosen) {
      unEnrolCourse(courseName)
    } else {
      enrolCourse(courseName)
    }
  }

  async function enrolCourse(courseName) {
    const url = "update/student/course";
    const token = localStorage.getItem("token");
    const body = {
      "courseName": courseName,
    }
    const response = await makeRequestWithToken('POST', url, body, token);
    if (response.code === "200") {
      // console.log('enrolled success', response);
      fetchAllCourses();
      fetchEnrolledCourses();
    } else {
      console.log(response.message);
    }
  }

  async function unEnrolCourse(courseName) {
    const url = "delete/student/course";
    const token = localStorage.getItem("token");
    const body = {
      "courseName": courseName,
    }
    const response = await makeRequestWithToken('POST', url, body, token);
    if (response.code === "200") {
      // console.log('unenrolled success', response);
      fetchAllCourses();
      fetchEnrolledCourses();
    } else {
      console.log(response);
    }
  }

  // course card
  function courseCard(course) {
    return (
      <Card sx={{ width: 330 }}>
        <div>
          <Typography level="h4">{course.name}</Typography>
          <IconButton
            aria-label="bookmark Bahamas Islands"
            variant="plain"
            color={course.choose ? "success" : "neutral"}
            size="sm"
            sx={{ position: 'absolute', top: '0.875rem', right: '0.5rem' }}
            onClick={() => { clickIconButton(course.choose, course.name) }}
          >
            <BookmarkAdd />
          </IconButton>
        </div>
        <AspectRatio minHeight="130px" maxHeight="210px">
          <img
            src={course.image}
            alt="course"
          />
        </AspectRatio>
        <CardContent orientation="horizontal">
          <div>
            <Typography level='title-md'>Course type:</Typography>
            <Typography level="p">
              {course.courseType}
            </Typography>
          </div>
        </CardContent>
        <CardContent orientation="horizontal">
          <div style={{height: "96px"}}>
            <Typography level='title-md'>Description:</Typography>
            <Typography level="p">
              {course.description}
            </Typography>
          </div>
        </CardContent>
      </Card>
    );
  }
  function SwitchToAllCourses() {
    fetchAllCourses();
    setShowAllCourses(true);
    setShowEnrolledCourses(false);
  }

  function SwitchToEnrolledCourses() {
    fetchEnrolledCourses();
    setShowAllCourses(false);
    setShowEnrolledCourses(true);
  }

  function allCoursesContent() {
    let allCoursesDiv = []

    for (let i = 0; i < coursesArray.length; i++) {
      allCoursesDiv.push(
        (
          <div className='student-course-course-div' key={i}>
            {courseCard(coursesArray[i])}
          </div>
        )
      )
    }

    return (
      <>
        <div className='student-course-title'>All courses</div>
        <div className='student-course-main-content'>
          {allCoursesDiv}
        </div>
      </>
    );
  }

  function enrolledCoursesContent() {
    let allCoursesDiv = []

    for (let i = 0; i < enrolledCoursesArray.length; i++) {
      allCoursesDiv.push(
        (
          <div className='student-course-course-div' key={i}>
            {courseCard(enrolledCoursesArray[i])}
          </div>
        )
      )
    }

    return (
      <>
        <div className='student-course-title'>Enrolled courses</div>
        {allCoursesDiv.length > 0 && <div className='student-course-main-content'>
          {allCoursesDiv}
        </div>}
      </>
    );
  }

  return (
    <div className='student-course-page-div'>
      <StudentNavBar />
      <div className='student-course-main-div'>
        <div className='student-course-sidebar'>
          <div className='student-course-inner-sidebar'>
            <div className='student-course-button-div'>
              <Button type="text" block className='student-course-button' onClick={SwitchToAllCourses}>All courses</Button>
            </div>
            <div className='student-course-button-div'>
              <Button type="text" block className='student-course-button' onClick={SwitchToEnrolledCourses}>Enrolled courses</Button>
            </div>
          </div>
        </div>
        <div className='student-course-main-content-div'>
          {showAllCourses && <>
            {allCoursesContent()}
          </>}
          {showEnrolledCourses && <>
            {enrolledCoursesContent()}
          </>}
        </div>
      </div>
    </div>
  );
}

export default StudentCoursePage;