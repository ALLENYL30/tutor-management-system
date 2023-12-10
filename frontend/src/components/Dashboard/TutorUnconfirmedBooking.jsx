import { Space, Table, Tag } from "antd";
import React from "react";
import Button from "@mui/material/Button";
import { useState } from "react";
import makeRequestWithToken from "../makeRequestWithToken";

function TutorUnconfirmedBooking() {
  const [BookingConfirmedData, setBookingConfirmedData] = useState([]);

  // 待确认的预约
  async function acceptAppointment(id) {
    //接受预约
    // console.log("11111",id);
    const url = "update/book/tutor";
    const token = localStorage.getItem("token");
    const body = {
      bookId: id,
    }
    const response = await makeRequestWithToken("POST", url, body, token);
    if (response.code === "200") {
      alert('This appointment has been confirmed!');
      removeRecord(id);
    } else {
      alert('error')
    }
  }

  async function rejectAppointment(id) {
    //拒绝预约
    // console.log("22222",id);
    const url = "delete/book";
    const token = localStorage.getItem("token");
    const body = {
      bookId: id,
    }
    const response = await makeRequestWithToken("POST", url, body, token);
    if (response.code === "200") {
      alert('This appointment has been cancelled');
      removeRecord(id);
    } else {
      alert('error')
    }
  }

  function removeRecord(bookId) {
    let tempArray = []
    for (let i = 0; i < BookingConfirmedData.length; i++) {
      let oneBooking = BookingConfirmedData[i]
      if (oneBooking.id !== bookId) {
        tempArray.push(oneBooking)
      }
    }
    setBookingConfirmedData(tempArray);
  }

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
            let color = tag.length > 8 ? "geekblue" : "green";
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
      key: "action",
      render: (_, record) => (
        <Space size="middle">
          <Button color="success" onClick={() => acceptAppointment(record.id)}>
            Accept
          </Button>
          <Button color="error" onClick={() => rejectAppointment(record.id)}>
            Reject
          </Button>
        </Space>
      ),
    },
  ];

  async function fetchUnconfirmedAppointment() {
    const url = "pull/tutor/unconfirmed";
    const token = localStorage.getItem("token");

    const response = await makeRequestWithToken("POST", url, undefined, token);
    if (response.code === "200") {

      const bookingConfirmedData = response.result.list;
      console.log('fetch unconfirmed appointment', bookingConfirmedData);

      var newData = []; //整理过后打在表上面的待预约数据
      var obj = {};
      var newTime = "";
      for (let i in bookingConfirmedData) {
        obj.key = i;
        obj.id = bookingConfirmedData[i].id;
        obj.studentName = bookingConfirmedData[i].studentName;
        newTime = "".concat(
          bookingConfirmedData[i].bookingTimeDO.year,
          "-",
          bookingConfirmedData[i].bookingTimeDO.month,
          "-",
          bookingConfirmedData[i].bookingTimeDO.day,
          " ",
          bookingConfirmedData[i].bookingTimeDO.hour,
          ":",
          bookingConfirmedData[i].bookingTimeDO.minute,
          ":00"
        );
        obj.state = ["to be confirmed"];
        obj.time = newTime;
        newData.push(obj);
        obj = {};
        newTime = "";
      }
      setBookingConfirmedData(newData);
    }
  }

  React.useEffect(() => {
    fetchUnconfirmedAppointment();
  }, []);

  return (
    <div className="showBooking">
      <Table columns={columns} dataSource={BookingConfirmedData} />
    </div>
  );
}
export default TutorUnconfirmedBooking;
