import  { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

function Login() {
    const [user, setUser] = useState({ phoneNumber: '', password: '' });
    const navigate = useNavigate();

    function loginUser() {
        if (!user.phoneNumber || !user.password) {
            toast.error("Iltimos, telefon raqam va parolni to'liq kiriting.");
            return;
        }

        axios({
            url: 'http://localhost:8081/login',
            method: 'post',
            data: user,
        })
            .then((res) => {
                if (res.data) {
                    console.log(res.data);
                    localStorage.setItem('access_token', res.data.token1);
                    localStorage.setItem('refresh_token', res.data.token2);
                    setUser({ phoneNumber: '', password: '' });
                    navigate("/keyingiPage");
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
                        <input
                            onChange={(e) => setUser({...user, phoneNumber: e.target.value})}
                            value={user.phoneNumber}
                            placeholder="PhoneNumber..."
                            type="text"
                        />
                    </div>
                    <div className="input-field">
                        <input
                            onChange={(e) => setUser({...user, password: e.target.value})}
                            value={user.password}
                            placeholder="Password..."
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