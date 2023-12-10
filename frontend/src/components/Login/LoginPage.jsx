import "./LoginPage.css";
import Login from "./Login";
import img from "./loginImg.jpg";
import Header from "../Homepage/Header";
function LoginPage() {
 
  return (
    <div>
      <Header />
      <div className="LoginPage">
        <img className="loginImg" src={img} alt=""></img>
        <Login></Login>
      </div>
    </div>
  );
}

export default LoginPage;
