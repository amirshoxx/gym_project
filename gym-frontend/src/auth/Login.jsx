import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
// import apiCall from "../apicall/apiCall.js";

function Login() {
    const [user, setUser] = useState({ phoneNumber: '', password: '' });
    const navigate = useNavigate();

    function loginUser() {
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
                    // apiCall("/user/getId","GET", {}, {refreshToken:localStorage.getItem("refresh_token")}).then(({data})=>{
                    //     localStorage.setItem("userId", data);
                    // })
                    navigate("/keyingiPage")
                }else {
                    alert("err")
                }
            }).catch(()=>alert("xatolik"))
    }

    return (
        <div className="row" style={{width:'1310px'}}>
            <div className="card">
                <h2>Login</h2>
                <div className="input-field">
                    <input
                        onChange={(e) => setUser({ ...user, phoneNumber: e.target.value })}
                        value={user.phoneNumber}
                        placeholder="PhoneNumber..."
                        type="text"
                    />
                </div>
                <div className="input-field">
                    <input
                        onChange={(e) => setUser({ ...user, password: e.target.value })}
                        value={user.password}
                        placeholder="Password..."
                        type="password"
                    />
                </div>
                <button onClick={loginUser} className="buttonS">
                    Login
                </button>
                <button onClick={() => navigate('/register')} className="button">Register</button>
            </div>
        </div>
    );
}

export default Login;
