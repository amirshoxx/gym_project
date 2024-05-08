import "react"
import {Route, Routes} from "react-router-dom";
import Login from "./auth/Login.jsx";
import Home from "./home/Home.jsx";
function App() {

  return (
        <Routes>
          <Route path={"/login"} element={<Login/>}/>
          <Route path={"/"} element={<Home />} />
          {/*<Route path={"/register"} element={<Regis />} />*/}
        </Routes>
  );
}

export default App
