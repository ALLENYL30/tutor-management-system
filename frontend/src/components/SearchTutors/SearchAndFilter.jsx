import React, { useState } from "react";

// Ant Design Components
import { Input, Select, Space, Rate, Tooltip, Button, Divider, Drawer } from "antd";
import { ControlOutlined } from "@ant-design/icons";
import { message, Popconfirm } from "antd";

// import from "@mui/material
import { DemoContainer, DemoItem } from "@mui/x-date-pickers/internals/demo";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { StaticDatePicker } from "@mui/x-date-pickers/StaticDatePicker";

// other imports
import dayjs from "dayjs";
import Stack from "@mui/material/Stack";
import moment from "moment";

// Local Imports
import StudentNavBar from "../NavBar/StudentNavBar";
import profileImg from "../Login/loginImg.jpg";
import makeRequest from "../makeRequest";
import makeRequestWithToken from "../makeRequestWithToken";
import "./SearchAndFilter.css";

const { Search } = Input;
const desc = ["terrible", "bad", "normal", "good", "wonderful"];

function SearchAndFilter() {
  // value of filters
  const [selectedCourse, setSelectedCourse] = useState("");
  const [selectTimezone, setSelectTimezone] = useState("");

  // store the options for courses
  const [courseOptions, setCourseOptions] = useState([]);

  const [chosenTutorName, setChosenTutorName] = useState("")

  const [tutorBackendArray, setTutorBackendArray] = useState([]);
  const [tutorDisplayArray, setTutorDisplayArray] = useState([]);

  // calendar variable
  const [dataValue, setDataValue] = useState(dayjs("2023-11-17"));
  const [timeValue, setTimeValue] = useState("");
  const [newList, setNewList] = useState([]);

  React.useEffect(() => {
    fetchCourses();
    fetchTutors();
    // get all the array from backend
  }, []);

  function handleSearch(keyword) {
    let myKeyword = keyword ? keyword.toLowerCase() : "";
    let tempSelectCourse = selectedCourse ? selectedCourse.toLowerCase() : "";
    let tempSelectTimezone = selectTimezone ? selectTimezone.toLowerCase() : "";


    console.log(keyword)
    let filteredTutors = []
    for (let i = 0; i < tutorBackendArray.length; i++) {
      const tutor = tutorBackendArray[i]
      const name = tutor.name ? tutor.name.toLowerCase() : "";
      const course = tutor.course ? tutor.course.toLowerCase() : "";
      const timezone = tutor.timezone ? tutor.timezone.toLowerCase() : "";
      const bio = tutor.bio ? tutor.bio.toLowerCase() : "";
      let matchesKeyword = myKeyword === "" || name.includes(myKeyword) || course.includes(myKeyword) || timezone.includes(myKeyword) || bio.includes(myKeyword);
      let matchesCourse = tempSelectCourse === "" || course === tempSelectCourse.toLowerCase();
      let matchesTimezone = tempSelectTimezone === "" || timezone === tempSelectTimezone.toLowerCase();

      if (matchesKeyword && matchesCourse && matchesTimezone) {
        filteredTutors.push(tutor);
      }
    }

    // Update the displayed tutors
    setTutorDisplayArray(filteredTutors);
  }

  async function fetchCourses() {
    // console.log("fetch courses");
    const url = "pull/courseInfo";
    const response = await makeRequest('POST', url, undefined)
    if (response.code === "200") {
      // console.log(response.result.list)
      const backendCourseArray = response.result.list;
      let tempArray = []
      for (let i = 0; i < backendCourseArray.length; i++) {
        const option = {
          value: backendCourseArray[i].name,
          label: backendCourseArray[i].name,
        }
        tempArray.push(option)
      }
      // console.log(tempArray)
      setCourseOptions(tempArray)

    } else {
      console.log("invalid input")
    }
  }

  async function fetchTutors() {
    console.log("fetch tutors");
    const url = "pull/tutor";
    const response = await makeRequest('POST', url, undefined);
    if (response.code === "200") {
      // console.log(response.result.list)
      const backendArray = response.result.list
      let tempArray = []
      for (let i = 0; i < backendArray.length; i++) {
        const tutor = {
          name: backendArray[i].userName,
          timezone: backendArray[i].timeZone,
          course: backendArray[i].courseName,
          rating: backendArray[i].score,
          rating_number: backendArray[i].number,
          bio: backendArray[i].bio,
          image: backendArray[i].image,
        }
        if (tutor.image === "data:image/png;base64" || tutor.image === null || tutor.image === "") {
          tutor.image = profileImg;
        }

        if (tutor.course === null || tutor.course === "undifined") {
          tutor.course = ""
        }

        // console.log(tutor)
        tempArray.push(tutor)
      }
      // set variables
      setTutorBackendArray(tempArray);
      setTutorDisplayArray(tempArray)

    } else {
      console.log("internet error")
    }
  }

  // test only
  function getFilterValue() {
    console.log('course', selectedCourse);
    console.log('timezone', selectTimezone);
  }

  /*******************************************************************************
  *                                 searchbar and filter functions                              *
  *******************************************************************************/

  function searchBarAndFilter() {
    return (
      <>
        <div className="search">
          <Search
            placeholder="input search text"
            onSearch={handleSearch}
            style={{
              width: "65vw",
            }}
          />
        </div>
        <Space wrap>
          Course:{" "}
          <Select
            value={selectedCourse}
            style={{
              width: 120,
            }}
            onChange={setSelectedCourse}
            allowClear
            options={courseOptions}
          />
          Time zone:{" "}
          <Select
            value={selectTimezone}
            style={{
              width: 120,
            }}
            onChange={setSelectTimezone}
            allowClear
            options={[
              {
                value: "Sydney",
                label: "Sydney",
              },
              {
                value: "Melbourne",
                label: "Melbourne",
              },
              {
                value: "Brisbane",
                label: "Brisbane",
              },
              {
                value: "Macquarie",
                label: "Macquarie",
              },
              {
                value: "Adelaide",
                label: "Adelaide",
              },
              {
                value: "Broken Hill",
                label: "Broken Hill",
              },
              {
                value: "Darwin",
                label: "Darwin",
              },
              {
                value: "Eucla",
                label: "Eucla",
              },
              {
                value: "Hobart",
                label: "Hobart",
              },
              {
                value: "Lindeman",
                label: "Lindeman",
              },
              {
                value: "Lord Howe",
                label: "Lord Howe",
              },
              {
                value: "Perth",
                label: "Perth",
              },
            ]}
          />
          <Tooltip title="filter">
            <Button
              shape="circle"
              icon={<ControlOutlined />}
              onClick={getFilterValue}
            />
          </Tooltip>
        </Space>
      </>
    )
  }

  /* End of search bar and tutor functions */

  /*******************************************************************************
  *                                 Tutor functions                              *
  *******************************************************************************/
  // variables to control the drawer
  const [open, setOpen] = useState(false);
  function showDrawer(tutorName) {
    setChosenTutorName(tutorName);
    setOpen(true);
    fetchConfirmedList(tutorName);
  };
  function onClose() {
    setOpen(false);
  };

  function myDivider() {
    return (
      <Divider />
    )
  }

  function tutorCard(tutor) {
    return (
      <div className="tutorInfo">
        <div className="tutorCardHead">
          <img src={profileImg} alt="tutor"></img>

          <div className="tutorCardHeadRight">
            <div>
              <strong>Name:</strong> {tutor.name}
            </div>
            <div>
              <strong>Time zone:</strong> {tutor.timezone}
            </div>

            <div className="tutorRateDiv">
              <strong className="ratingText">Rating:</strong>{" "}
              <div style={{ paddingTop: "2.5px" }}>
                <Rate allowHalf={true} disabled tooltips={desc} value={Number(tutor.rating).toFixed(1)} />
              </div>
              <div className="ratingNumberText">{Number(tutor.rating).toFixed(1)} ({tutor.rating_number})</div>
            </div>

            <div className="bookButtonDiv">
              <Button
                type="primary"
                size="default"
                onClick={() => { showDrawer(tutor.name) }}
              >
                Book an appointment
              </Button>
            </div>
          </div>

        </div>
        {myDivider()}
        <div>
          <div>
            <div>bio:</div>
            {tutor.bio}
          </div>
        </div>

        {myDivider()}

        <div style={{ marginBottom: "20px" }}>
          <div>Tutor {tutor.name} teaches these course(s):</div>
          {tutor.course}
        </div>
      </div>
    );
  }

  function showAllTutors() {
    let allTutorsDiv = []

    for (let i = 0; i < tutorDisplayArray.length; i++) {
      allTutorsDiv.push(
        (
          <div key={i}>
            {tutorCard(tutorDisplayArray[i])}
          </div>
        )
      )
    }

    return (
      <>
        {allTutorsDiv}
      </>
    );
  }

  // fetch confirmed time
  async function fetchConfirmedList(tutorName) {
    const url = "pull/tutor/confirmed";
    const token = localStorage.getItem("token");
    const body = {
      tutorName: tutorName,
    };
    const response = await makeRequestWithToken("POST", url, body, token);
    // console.log("fetchConfirmedList", tempList);
    if (response.code === "200") {
      const BackendConfrimedList = response.result.list;
      // console.log("fetch confirmed list", tempList);

      let tempList = [];
      for (let i in BackendConfrimedList) {
        const oneTime = BackendConfrimedList[i]
        let obj = {};
        obj.date = "".concat(oneTime.year, "-", oneTime.month, "-", oneTime.day);
        // obj.time = "".concat(oneTime.hour, ":", oneTime.minute, ":00");
        obj.hour = oneTime.hour;
        tempList.push(obj);
      }
      setNewList(tempList);
    }
    if (response.code === "400") {
      // alert("Error! This time slot has been booked!");
    }
  }

  function appointmentDrawer() {
    return (
      <Drawer
        title={`Schedule a lesson with ${chosenTutorName}`}
        placement={'top'}
        width={500}
        height={650}
        onClose={onClose}
        open={open}
        extra={
          <Space>
            <Button onClick={onClose}>Cancel</Button>
            <Button type="primary" onClick={onClose}>
              OK
            </Button>
          </Space>
        }
      >
        <ResponsiveDatePickers />
      </Drawer>
    );
  }

  /*******************************************************************************
  *                                 ResponsiveDatePickers functions                              *
  *******************************************************************************/

  function ResponsiveDatePickers() {


    const handleClick = (event) => {
      let theText = event.currentTarget.textContent;
      theText = theText.substring(0, 5);
      const Time = "".concat(theText, ":00");
      // console.log('time =',Time)
      setTimeValue(Time);
    };

    const confirm = (e) => {
      var data = dataValue["$d"].toString();
      data = data.substring(4, 16);
      var date = moment(data).format("YYYY-MM-DD");
      var appointmentTime = "".concat(date, " ", timeValue);
      console.log("appointmentTime, i am here", appointmentTime);
      submitAppointmentToBackEnd(appointmentTime);
      message.success("Click on Yes");
    };

    const cancel = (e) => {
      // console.log(e);
      message.error("Click on No");
    };

    //直接用鼠标悬停在time的div上面去触发
    function checkBookedTime() {
      // console.log(dataValue)
      var data = dataValue["$d"].toString();
      data = data.substring(4, 16);
      var date = moment(data).format("YYYY-MM-DD");
      const temp = document.querySelectorAll(".timeButton");
      for (let i in newList) {
        // console.log(newList[i].date);
        // console.log(newList[i].time);
        if (newList[i].date === date) {
          console.log(newList[i])
          console.log("111111"); //日期匹配成功
          for (var j = 0; j < temp.length; j++) {
            // console.log('11111',typeof(temp[j].innerText))
            var text = temp[j].innerText;
            text = text.substring(0, 2);
            if (
              newList[i].hour === parseInt(text)
            ) {
              // console.log('日期对上了，证明被预约了，变红')
              temp[j].style.color = "red";
              temp[j].style.borderColor = "red";
            } else {
              temp[j].style.color = "green";
              temp[j].style.borderColor = "#33ff99";
            }
          }
        } else {
          //日期匹配失败
          console.log("22222");
          for (let a = 0; a < temp.length; a++) {

            if (temp[a].style.color === "red") {
              temp[a].style.color = "green";
              temp[a].style.borderColor = "#33ff99";
            }
          }
        }
      }
    }

    async function submitAppointmentToBackEnd(time) {
      const url = "update/book/student";
      const token = localStorage.getItem("token");
      const body = {
        tutorName: chosenTutorName,
        time: time,
      };
      const response = await makeRequestWithToken("POST", url, body, token);
      console.log(response)

      if (response.code === "200") {
        alert("make appointment successfully!!!");
      }
      if (response.code === "400") {
        alert("Error! This time slot has been booked!");
      }
    }

    return (
      <div className="studentBooking">
        <div className="calendar">
          <LocalizationProvider dateAdapter={AdapterDayjs}>
            <DemoContainer components={["MobileDatePicker", "StaticDatePicker"]}>
              <DemoItem>
                <StaticDatePicker
                  value={dataValue}
                  onChange={(newValue) => setDataValue(newValue)}
                />
              </DemoItem>
            </DemoContainer>
          </LocalizationProvider>
          <div className="selectTime" onMouseEnter={checkBookedTime}>
            <h2>Select time:</h2>
            <Stack direction="column" spacing={2}>
              <Popconfirm
                title="Are you sure to book this time?"
                onConfirm={confirm}
                onCancel={cancel}
                okText="Yes"
                cancelText="No"
              >
                <button
                  className="timeButton"
                  variant="outlined"
                  style={{ color: "green", borderColor: "#33ff99" }}
                  onClick={handleClick}
                >
                  09:00 AM
                </button>
              </Popconfirm>
              <Popconfirm
                title="Are you sure to book this time?"
                onConfirm={confirm}
                onCancel={cancel}
                okText="Yes"
                cancelText="No"
              >
                <button
                  className="timeButton"
                  variant="outlined"
                  style={{ color: "green", borderColor: "#33ff99" }}
                  onClick={handleClick}
                >
                  10:00 AM
                </button>
              </Popconfirm>
              <Popconfirm
                title="Are you sure to book this time?"
                onConfirm={confirm}
                onCancel={cancel}
                okText="Yes"
                cancelText="No"
              >
                <button
                  className="timeButton"
                  variant="outlined"
                  style={{ color: "green", borderColor: "#33ff99" }}
                  onClick={handleClick}
                >
                  11:00 AM
                </button>
              </Popconfirm>
              <Popconfirm
                title="Are you sure to book this time?"
                onConfirm={confirm}
                onCancel={cancel}
                okText="Yes"
                cancelText="No"
              >
                <button
                  className="timeButton"
                  variant="outlined"
                  style={{ color: "green", borderColor: "#33ff99" }}
                  onClick={handleClick}
                >
                  14:00 PM
                </button>
              </Popconfirm>
              <Popconfirm
                title="Are you sure to book this time?"
                onConfirm={confirm}
                onCancel={cancel}
                okText="Yes"
                cancelText="No"
              >
                <button
                  className="timeButton"
                  variant="outlined"
                  style={{ color: "green", borderColor: "#33ff99" }}
                  onClick={handleClick}
                >
                  15:00 PM
                </button>
              </Popconfirm>
              <Popconfirm
                title="Are you sure to book this time?"
                onConfirm={confirm}
                onCancel={cancel}
                okText="Yes"
                cancelText="No"
              >
                <button
                  className="timeButton"
                  variant="outlined"
                  style={{ color: "green", borderColor: "#33ff99" }}
                  onClick={handleClick}
                >
                  16:00 PM
                </button>
              </Popconfirm>
            </Stack>
          </div>
        </div>
      </div>
    );
  }

  return (
    <>
      <StudentNavBar />
      <div className="searchComponent">
        <h2>ALL tutors</h2>
        {searchBarAndFilter()}

        <div className="showTutors">
          {appointmentDrawer()}
          {showAllTutors()}
        </div >
      </div >
    </>
  );
}

export default SearchAndFilter;