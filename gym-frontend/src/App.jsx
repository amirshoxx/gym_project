import "react"
import {Route, Routes} from "react-router-dom";
import Login from "./auth/Login.jsx";
import Home from "./home/Home.jsx";
import Gym from "./Gym.jsx";
import SuperAdmin from "./SuperAdmin.jsx";
import SuperSettings from "./SuperSettings.jsx";
import AddAdmin from "./AddAdmin.jsx";

function App() {

  return (
        <Routes>
          <Route path={"/login"} element={<Login/>}/>
          <Route path={"/"} element={<Home />} />
          <Route path={"/gym"} element={<Gym/>} />
          <Route path={"/superAdmin"} element={<SuperAdmin/>} />
          <Route path={"/superSettings"} element={<SuperSettings/>} />
          <Route path={"/addAdmin"} element={<AddAdmin/>} />

          {/*<Route path={"/register"} element={<Regis />} />*/}
        </Routes>
  );
}

export default App
