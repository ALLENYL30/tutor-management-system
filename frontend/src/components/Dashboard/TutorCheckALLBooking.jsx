import { Table, Tag } from "antd";
import React from "react";
import { useState } from "react";
// import Button from "@mui/material/Button";
import makeRequestWithToken from "../makeRequestWithToken";

const columns2 = [
  {
    title: "Tutor Name",
    dataIndex: "tutorName",
    key: "tutorName",
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
          let color = tag.length > 9 ? "geekblue" : "green";
          if (tag === "cancelled") {
            color = "red";
          }
          if (tag === "completed") {
            color = "gold";
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
];

const defaultAllBookingData = [
  {
    tutorName: "tutor3",
    studentName: "小小明",
    time: {
      year: 2023,
      month: 10,
      day: 11,
      hour: 15,
      minute: 30,
    },
    createTime: "2023-11-11T06:29:16.032+00:00",
    complete: false,
    delete: false,
    confirm: true,
  },
  {
    tutorName: "tutor2",
    studentName: "小小明",
    time: {
      year: 2023,
      month: 10,
      day: 11,
      hour: 15,
      minute: 30,
    },
    createTime: "2023-11-11T06:29:19.983+00:00",
    complete: true,
    delete: false,
    confirm: false,
  },
  {
    tutorName: "tutor2",
    studentName: "小小明",
    time: {
      year: 2023,
      month: 10,
      day: 11,
      hour: 16,
      minute: 30,
    },
    createTime: "2023-11-11T06:29:25.464+00:00",
    complete: false,
    delete: true,
    confirm: false,
  },
];

function TutorCheckALLBooking() {
  var newDataAll = []; //整理过后打在表上面的所有预约数据
  var objAll = {};
  var newTimeAll = "";
  for (let j in defaultAllBookingData) {
    objAll.key = j;
    objAll.studentName = defaultAllBookingData[j].studentName;
    objAll.tutorName = defaultAllBookingData[j].tutorName;
    newTimeAll = "".concat(
      defaultAllBookingData[j].time.year,
      "-",
      defaultAllBookingData[j].time.month,
      "-",
      defaultAllBookingData[j].time.day,
      " ",
      defaultAllBookingData[j].time.hour,
      ":",
      defaultAllBookingData[j].time.minute,
      ":00"
    );
    objAll.time = newTimeAll;
    if (defaultAllBookingData[j].confirm === true) {
      objAll.state = ["accepted"];
    } else {
      //confirm 为 false
      if (defaultAllBookingData[j].delete === true) {
        objAll.state = ["cancelled"];
      } else {
        //delete && confirm 为 false
        if (defaultAllBookingData[j].complete === true) {
          objAll.state = ["completed"];
        } else {
          //全是false
          objAll.state = ["to be confirmed"];
        }
      }
    }
    newDataAll.push(objAll);
    objAll = {};
    newTimeAll = "";
  }
  const [AllBookingData, setAllBookingData] = useState(newDataAll);

  async function tutorFetchAllAppointment() {
    const url = "pull/book";
    const token = localStorage.getItem("token");

    const response = await makeRequestWithToken("POST", url, undefined, token);
    if (response.code === "200") {
      const bookingData = response.result.list;

      console.log(bookingData)
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
      setAllBookingData(newData);
    }
  }

  React.useEffect(() => {
    tutorFetchAllAppointment();
  }, []);

  return (
    <div className="showBooking">
      <Table columns={columns2} dataSource={AllBookingData} />
    </div>
  );
}
export default TutorCheckALLBooking;
