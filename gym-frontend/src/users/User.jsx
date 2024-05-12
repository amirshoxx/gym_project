import "react"
import {useNavigate} from "react-router-dom";
import apiCall from "../apicall/apiCall.js";
import {useEffect, useState} from "react";
import "./User.css"
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import "bootstrap/dist/css/bootstrap.css"
function User() {
    useEffect(()=>{
        getUsers()
    },[])
    const [password, setPassword] = useState('')
    const [users, setUsers] = useState([])
    const [fullName, setFullName] = useState('')
    const [selectFullName, setSelectFullName] = useState('')
    const [phone, setPhone] = useState('')
    const [selectPhone, setSelectPhone] = useState('')
    const [selectedFile, setSelectedFile] = useState("");

    function getUsers(){
        apiCall("/user","GET",{},{Authorization:localStorage.getItem("access_token")}).then(res=>{
            setUsers(res.data)

        }).catch(()=>{
            apiCall("/user/refresh", "POST", {}, { refreshToken : localStorage.getItem("refresh_token") })
                .then(res => {
                    localStorage.setItem("access_token", res.data);
                    getUsers()
                })
        })
    }


    const navigets = useNavigate();

    useEffect(() => {
        const fetchData = async () => {
            try {
                if (localStorage.getItem("access_token")) {
                    const response = await apiCall("/user/admin", "GET", {}, { Authorization: localStorage.getItem("access_token") });

                    if (response.data === "admin") {
                        console.log("User is a super admin");
                    } else {
                        navigets("/login");
                    }
                } else {
                    navigets("/login");
                }
            } catch (error) {
                navigets("/login");
            }
        };

        fetchData();
    }, [navigets]);



    function submit() {
        const formData = new FormData();
        formData.append('file', selectedFile);
        if (fullName&&password&&selectedFile&&phone) {
            apiCall("/fileController", "POST", formData)
                .then(res => {
                    apiCall("/user", "POST", {
                        phone,
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

    function searchs(value) {
        setPhone(value)
        apiCall("subscription/search?phoneNumber="+phone,"GET").then(res=>{
            setUsers(res.data)
        })
    }

    function selectUser(u) {
        let image = u.image;
        let fullName = u.fullName;
         let phoneNumber = u.phoneNumber;
         setSelectedFile(image)
        setSelectFullName(fullName)
        setSelectPhone(phoneNumber)
    }

    return (
        <div >
            <ToastContainer/>
            <div className={"register-header"}>
                Users
            </div>
            <div className={"card-login"}>
                <p className={"text-black"}>Qidiruv:</p>
                <input
                    value={phone}
                    onChange={(e)=>searchs(e.target.value)}
                    className={"form-control my-2"}
                    placeholder={"+998*******"}
                    type={"text"}
                />
                <div className={"d-flex justify-content-between"}>
                    <div style={{width:400+"px"}}>
                        {
                            selectedFile ?  <img className={"w-100 h-100"}
                                 src={`http://localhost:8080/fileController?image=${selectedFile}`} alt=""/> :<h1>PHOTO</h1>

                        }
                    </div>
                    <div>
                        <div className={"d-flex"}>
                        <h1>FullName:</h1>
                            {
                                selectFullName ?
                                    <input value={selectFullName} className={"form-control"} type="text"/> :
                                    <input value={fullName} onChange={(e)=>setFullName(e.target.value)} type="text"/>
                            }


                        </div>
                        <div className={"d-flex"}>
                        <h1>Phone:</h1>
                            {
                                selectPhone ?
                                    <input value={selectPhone} className={"form-control"} type="text"/> :
                                    <input value={phone} onChange={(e)=>setPhone(e.target.value)} type="text"/>
                            }
                        </div>
                    </div>
<div></div>
                </div>
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
                 users &&   users.map((u,i)=> <tr key={u.id} onClick={()=>selectUser(u)}>
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