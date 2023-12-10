import "./RegisterPage.css";
import Register from "./Register";
import img from "../Login/loginImg.jpg";
import Header from "../Homepage/Header";

function RegisterPage() {
  return (  
     <div>
      <Header />
      <div className="RegisterPage">
        <img className="loginImg" src={img} alt=""></img>
        <Register></Register>
      </div>
     </div>
  );
}

export default RegisterPage;
