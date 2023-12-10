// import logo from './logo.svg';
import "./App.css";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";

// import 'bootstrap/dist/css/bootstrap.min.css';
import LoginPage from "./components/Login/LoginPage";
import RegisterPage from "./components/Register/RegisterPage";
import StudentDashboard from "./components/Dashboard/StudentDashboard";
import TutorDashboard from "./components/Dashboard/TutorDashboard";
import StudentProfile from "./components/Profile/StudentProfile";
import TutorProfile from "./components/Profile/TutorProfile";
import StudentCoursePage from "./components/Courses/StudentCoursePage";
import TutorCoursePage from "./components/Courses/TutorCoursePage";
import HomePage from "./components/Homepage/HomePage";
import AdminLogin from "./components/Admin/AdminLogin";
import AdminPage from "./components/Admin/AdminPage";
import SearchAndFilter from "./components/SearchTutors/SearchAndFilter";
import StudentChat from "./components/Chat/StudentChat";
import TutorChat from "./components/Chat/TutorChat";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="" element={<Navigate to="/home" />} />
        <Route path="home" element={<HomePage />} />
        <Route path="login" element={<LoginPage />} />
        <Route path="register" element={<RegisterPage />} />
        <Route path="student/chat" element={<StudentChat />} />
        <Route path="student/dashboard" element={<StudentDashboard />} />
        <Route path="student/profile" element={<StudentProfile />} />
        <Route path="student/course" element={<StudentCoursePage />} />
        <Route path="student/tutors" element={<SearchAndFilter />} />
        <Route path="tutor/chat" element={<TutorChat />} />

        <Route path="tutor/dashboard" element={<TutorDashboard />} />
        <Route path="tutor/profile" element={<TutorProfile />} />
        <Route path="tutor/course" element={<TutorCoursePage />} />

        <Route path="admin" element={<Navigate to="/admin/login" />} />
        <Route path="admin/login" element={<AdminLogin />} />
        <Route path="admin/dashboard" element={<AdminPage />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
