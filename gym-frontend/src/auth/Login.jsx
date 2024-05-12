import  { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import "react-phone-input-2/lib/style.css"
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import apiCall from "../apicall/apiCall.js";
import PhoneInput from "react-phone-input-2";
function Login() {
    const [user, setUser] = useState({ phoneNumber: '', password: '' });
    const navigate = useNavigate();



    function loginUser() {
        if (!user.phoneNumber || !user.password) {
            toast.error("Iltimos, telefon raqam va parolni to'liq kiriting.");
            return;
        }

        axios({

            url: 'http://localhost:8080/user/login',
            method: 'post',
            data: user
        })
            .then((res) => {
                if (res.data) {

                    apiCall(`/user/super_admins`, "GET",{}, { Authorization:res.data.access_token })
                        .then((resNotUser) => {
                          if (resNotUser){
                              localStorage.setItem('access_token', res.data.access_token);
                              localStorage.setItem('refresh_token', res.data.refresh_token);
                              apiCall(`/user/admins`, "GET",{}, { Authorization:res.data.access_token })
                                  .then(() => {
                                      setUser({ phoneNumber: '', password: '' });
                                      navigate("/superAdmin");
                                  })
                                  .catch(() => {
                                      setUser({ phoneNumber: '', password: '' });
                                      navigate("/addAdmin");
                                  });
                          }

                        })
                        .catch(() => {
                            toast.error("Kirish muvaffaqiyatsiz. Iltimos, tekshiring va qayta urinib ko'ring.");
                        });
                } else {
                    toast.error("Kirish muvaffaqiyatsiz. Iltimos, tekshiring va qayta urinib ko'ring.");
                }
            }).catch(() => toast.error("Xatolik yuz berdi. Iltimos, qayta urinib ko'ring."));
    }

    return (

            <div className={"big_mean"} >
                <div className="card">
                    <ToastContainer/>
                    <h1 style={{textAlign: "center", fontSize: "40px", marginBottom: "10px"}}>Login</h1>
                    <div className="input-field">
                        <PhoneInput

                            country={"uz"}

                            onChange={(e) => setUser({...user, phoneNumber: e})}
                            value={user.phoneNumber}
                        />
                    </div>
                    <div className="input-field">
                        <input
                            onChange={(e) => setUser({...user, password: e.target.value})}
                            value={user.password}
                            placeholder="Parol..."
                            type="password"
                        />
                    </div>

                    <button onClick={loginUser} className="buttonS">
                        Login
                    </button>
                </div>
            </div>
    );
}

export default Login;
