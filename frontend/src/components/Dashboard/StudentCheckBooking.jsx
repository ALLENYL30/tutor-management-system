import { Space, Table, Tag, Modal } from "antd";
import React from "react";
import Button from "@mui/material/Button";
import { useState } from "react";
import Rating from "./Rate";

import "./StudentCheckBooking.css";
import makeRequestWithToken from "../makeRequestWithToken";

function StudentCheckBooking() {
  const [AllBookingData, setAllBookingData] = useState();

  const [isModalOpen, setIsModalOpen] = useState(false);
  const [value, setValue] = useState(3);
  const [getId, setGetId] = useState();
  const columns = [
    {
      title: "BookId",
      dataIndex: "id",
      key: "id",
    },
    {
      title: "Student Name",
      dataIndex: "studentName",
      key: "studentName",
    },
    {
      title: "Tutor Name",
      dataIndex: "tutorName",
      key: "tutorName",
    },
    {
      title: "Time",
      dataIndex: "time",
      key: "time",
    },
    {
      title: "State",
      key: "state",
      dataIndex: "state",
      render: (_, { state }) => (
        <>
          {state.map((tag) => {
            let color = tag.length > 9 ? "geekblue" : "green";
            if (tag === "cancelled") {
              color = "red";
            }
            return (
              <Tag color={color} key={tag}>
                {tag.toLowerCase()}
              </Tag>
            );
          })}
        </>
      ),
    },
    {
      title: "Action",
      key: "IsCancel",
      render: (_, record) => (
        <Space size="middle">
          {actionButton(record)}
          {/* <Button onClick={() => {ButtonTest(record)}}>
            Test
          </Button> */}
        </Space>
      ),
    },
  ];

  // function ButtonTest(record) {
  //   console.log(record);
  // }

  function actionButton(record) {
    const state = record.state[0]
    if (state === "cancelled" || state === "completed") {
      return (
        <></>
      )
    } else if (state === "to be confirmed") {
      return (
        <Button color="error" onClick={() => CancelAppointment(record.id)}>
          Cancel
        </Button>
      )
    } else if (state === "accepted") {
      return (
        <>
          <Button color="success" onClick={() => CompleteAppointment(record.id)}>
            Complete
          </Button>
          <Button color="error" onClick={() => CancelAppointment(record.id)}>
            Cancel
          </Button>
        </>
      )
    }
  }

  React.useEffect(() => {
    studentFetchAllAppointment();
  }, []);

  async function CancelAppointment(id) {
    const url = "delete/book";
    const token = localStorage.getItem("token");
    const body = {
      bookId: id,
    }
    const response = await makeRequestWithToken("POST", url, body, token);
    console.log('cancel appointment', response)
    if (response.code === "200") {
      alert('This appointment has been cancelled');

      changeStateToCancelled(id);

    } else {
      alert('error')
    }
  }

  function changeStateToCancelled(bookId) {
    let tempArray = []
    for (let i=0; i<AllBookingData.length; i++) {
      let oneBooking = AllBookingData[i]
      if (oneBooking.id === bookId) {
        oneBooking.state = ["cancelled"];
      }
      tempArray.push(oneBooking)
    }
    setAllBookingData(tempArray);
  }

  function changeStateToCompleted(bookId) {
    let tempArray = []
    for (let i=0; i<AllBookingData.length; i++) {
      let oneBooking = AllBookingData[i]
      if (oneBooking.id === bookId) {
        oneBooking.state = ["completed"];
      }
      tempArray.push(oneBooking)
    }
    setAllBookingData(tempArray);
  }

  async function CompleteAppointment(id) {

    const url = "update/book/complete";
    const token = localStorage.getItem("token");
    const body = {
      bookId: id,
    }
    const response = await makeRequestWithToken("POST", url, body, token);
    if (response.code === "200") {
      // alert(response.message)
      changeStateToCompleted(id);

      showModal();
      setGetId(id);
      
    } else {
      console.log(response.message)
    }
  }

  // fetch appointments
  async function studentFetchAllAppointment() {
    const url = "pull/book";
    const token = localStorage.getItem("token");

    const response = await makeRequestWithToken("POST", url, undefined, token);
    if (response.code === "200") {
      const bookingData = response.result.list;

      console.log("fetch appointment: ", bookingData);

      let newData = []; //整理过后打在表上面的待预约数据

      let newTime = "";
      for (let i = 0; i < bookingData.length; i++) {

        let obj = {
          "id": bookingData[i].bookId,
          "studentName": bookingData[i].studentName,
          "tutorName": bookingData[i].tutorName,
          "key": i,
          "state": "",
        }

        newTime = "".concat(
          bookingData[i].time.year,
          "-",
          bookingData[i].time.month,
          "-",
          bookingData[i].time.day,
          " ",
          bookingData[i].time.hour,
          ":",
          bookingData[i].time.minute,
          ":00"
        );

        console.log(newTime)

        if (bookingData[i].confirm === false && bookingData[i].complete === false && bookingData[i].delete === false) {
          obj.state = ["to be confirmed"];
        } else if (bookingData[i].confirm === true && bookingData[i].complete === false && bookingData[i].delete === false) {
          obj.state = ["accepted"];
        } else if (bookingData[i].complete === true && bookingData[i].delete === false) {
          obj.state = ["completed"];
        } else if (bookingData[i].delete === true) {
          obj.state = ["cancelled"];
        }

        obj.time = newTime;
        newData.push(obj);
        obj = {};
        newTime = "";
      }

      // console.log(newData)
      setAllBookingData(newData);
    }
  }

  /* Modall functions */

  const getValue = (newValue) => {
    setValue(newValue);
  };

  const showModal = () => {
    setIsModalOpen(true);
  };

  function handleOk() {
    setIsModalOpen(false);
    // console.log("new:", value);
    // console.log("id:", getId);
    rateTutor(value, getId);
    //这里传数据
  }

  async function rateTutor(ratingScore, bookId) {
    const url = "update/score";
    const body = {
      "bookId": bookId,
      "score": ratingScore,
    }
    const token = localStorage.getItem("token");
    const response = await makeRequestWithToken("POST", url, body, token);
    if (response.code === "200") {
      // console.log('rate success, ', response);
      alert("rate tutor successfully");
    } else {
      console.log(response);
    }

  }

  const handleCancel = () => {
    setIsModalOpen(false);
  };

  /* End of modall functions */

  return (
    <div className="showBooking">
      <Table columns={columns} dataSource={AllBookingData} />
      <Modal
        title="Rating"
        open={isModalOpen}
        onOk={handleOk}
        onCancel={handleCancel}
      >
        <Rating onUpdate={getValue}></Rating>
      </Modal>
    </div>
  );
}
export default StudentCheckBooking;
