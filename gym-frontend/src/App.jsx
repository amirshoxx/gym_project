import "react"
import {Route, Routes} from "react-router-dom";
import Login from "./auth/Login.jsx";
import Home from "./home/Home.jsx";
// import SuperAdmin from "./auth/Super_Admin.jsx";
import Admin from "./auth/Admin.jsx";
function App() {

  return (
        <Routes>
          <Route path={"/login"} element={<Login/>}/>
            <Route path={"/"} element={<Home />} />
            {/*<Route path={"/super_admin_page"} element={<SuperAdmin />} />*/}
            <Route path={"/admin_page"} element={<Admin />} />
          {/*<Route path={"/register"} element={<Regis />} />*/}
        </Routes>
  );
}

export default App
