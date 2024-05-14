import "react"
import {Route, Routes} from "react-router-dom";
import Login from "./auth/Login.jsx";
import Home from "./home/Home.jsx";
import Gym from "./Gym.jsx";
import SuperAdmin from "./SuperAdmin.jsx";
import SuperSettings from "./SuperSettings.jsx";
import AddAdmin from "./AddAdmin.jsx";
import User from "./users/User.jsx";
import ErrorPage from "./404/ErrorPage.jsx";
import Subscription from "./subscription/Subscription.jsx";

function App() {

  return (
        <Routes>
          <Route path={"/login"} element={<Login/>}/>
          <Route path={"/"} element={<Home />} />
          <Route path={"/gym"} element={<Gym/>} />
          <Route path={"/superAdmin"} element={<SuperAdmin/>} />
          <Route path={"/superSettings"} element={<SuperSettings/>} />
          <Route path={"/addAdmin"} element={<AddAdmin/>} />
          <Route path={"/subscriptions"} element={<Subscription/>} />
          <Route path={"/users"} element={<User/>} />
            <Route path={"/404"} element={<ErrorPage/>} />


        </Routes>
  );
}

export default App
