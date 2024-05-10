import "react"
import {useNavigate} from "react-router-dom";
import apiCall from "../apicall/apiCall.js";
import {useEffect, useState} from "react";
import "./User.css"
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
function User() {
    useEffect(()=>{
        getUsers()
    },[])
    const navigate = useNavigate();
    const [password, setPassword] = useState('')
    const [users, setUsers] = useState([])
    const [fullName, setFullName] = useState('')
    const [phone, setPhone] = useState('')
    const [selectedFile, setSelectedFile] = useState("");
    function getUsers(){
        apiCall("/user","GET",{},{Authorization:localStorage.getItem("access_token")}).then(res=>{
            setUsers(res.data.body)

        }).catch(()=>{
            apiCall("/user/refresh", "POST", {}, { refreshToken : localStorage.getItem("refresh_token") })
                .then(res => {
                    localStorage.setItem("access_token", res.data);
                })
        })
    }

    function submit() {
        const formData = new FormData();
        formData.append('file', selectedFile);
        if (fullName&&password&&selectedFile&&phone) {
            apiCall("/fileController", "POST", formData)
                .then(res => {
                    apiCall("/user", "POST", {
                        phone,
                        password,
                        image:res.data,
                        fullName
                    },{Authorization:localStorage.getItem("access_token")})
                        .then(() =>
                            getUsers(),
                            toast.success('User added')
                        )
                        .catch(()=>
                    apiCall("/user/refresh", "POST", {}, { refreshToken : localStorage.getItem("refresh_token") })
                        .then(res => {
                            localStorage.setItem("access_token", res.data);
                            getUsers()
                        }))
                })
        }
    }

    return (
        <div >
            <ToastContainer/>
            <div className={"register-header"}>
                Users
            </div>
            <div className={"card-login"}>
                <p className={"text-black"}>Phone:</p>
                <input
                    value={phone}
                    onChange={(e)=>setPhone(e.target.value)}
                    className={"form-control my-2"}
                    placeholder={"+998*******"}
                    type={"text"}
                />
                <p className={"text-black"}> FullName:</p>
                <input
                    value={fullName}
                    onChange={(e)=>setFullName(e.target.value)}
                    className={"form-control my-2"}
                    placeholder={"firstname... and lastname..."}
                    type={"text"}
                />
                <p className={"text-black"}> Password:</p>
                <input
                    value={password}
                    onChange={(e)=>setPassword(e.target.value)}
                    className={"form-control my-2"}
                    placeholder={"password...."}
                    type={"password"}
                />
                <label  className={"color-dark"} htmlFor="fileUpload">Rasmingizni kiriting:</label>
                    <span className={"color-dark"} id="buttonText">________________________________</span>
                    <input type="file" id="fileUpload" onChange={(e)=>setSelectedFile(e.target.files[0])}  name="photos" accept="image/*"/>
                <button onClick={submit} className={"register-button"}>+</button>


                <table>
                    <thead>
                    <tr>
                        <th>N/O</th>
                        <th>FULLNAME</th>
                        <th>PHONENUMBER</th>
                        <th>IMAGE</th>
                    </tr>
                    </thead>
                    <tbody>
                    {
                 users &&   users.map((u,i)=> <tr key={u.id}>
                            <td>{i+1}</td>
                            <td>{u.fullName}</td>
                            <td>{u.phoneNumber}</td>
                            <td><img src={`http://localhost:8080/fileController?image=${u.image}`} alt=""/></td>
                        </tr>)
                    }
                    </tbody>
                </table>
            </div>
        </div>
    );
}

export default User;