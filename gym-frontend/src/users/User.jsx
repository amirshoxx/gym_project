import "react"
import {useNavigate} from "react-router-dom";
import apiCall from "../apicall/apiCall.js";
import {useEffect, useState} from "react";
import "./User.css"
import user from"./user.png"
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import "bootstrap/dist/css/bootstrap.css"
function User() {
    useEffect(()=>{
        getUsers()
        getSubscriptionType()
    },[])
    const [users, setUsers] = useState([])
    const [fullName, setFullName] = useState('')
    const [selectFullName, setSelectFullName] = useState('')
    const [phone, setPhone] = useState('')
    const [selectPhone, setSelectPhone] = useState('')
    const [selectedFile, setSelectedFile] = useState("");
    const [selectSubsc,setSelectSubsc] = useState("")
    const [subsc, setSubsc] = useState([]);

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
        if (fullName&&selectedFile&&phone) {
                    apiCall("/user", "POST", {
                        phone,
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
    function getSubscriptionType(){
        apiCall("/subscriptionType", "GET", {},{Authorization:localStorage.getItem("access_token")})
            .then((res) =>
                    setSubsc(res.data),
            )
            .catch(()=>
                apiCall("/user/refresh", "POST", {}, { refreshToken : localStorage.getItem("refresh_token") })
                    .then(res => {
                        localStorage.setItem("access_token", res.data);
                    }))
    }


    function saveSubscription() {
        apiCall("/subscription", "POST", {
            image:selectedFile?selectedFile:user,
            fullName:selectFullName?selectFullName:fullName,
            phoneNumber:selectPhone?selectPhone:phone,
            subscriptionId:selectSubsc
        },{Authorization:localStorage.getItem("access_token")})
            .then((res) =>
                toast.success("Added Successfully"),
            )
            .catch(()=>
                apiCall("/user/refresh", "POST", {}, { refreshToken : localStorage.getItem("refresh_token") })
                    .then(res => {
                        localStorage.setItem("access_token", res.data);
                    }))
        setSelectSubsc("")
        setSelectedFile("")
        setSelectFullName("")
        setSelectPhone("")
    }

    return (
        <div className={"user_sv"} >
            <ToastContainer/>
            <div className={"register-header"}>
                Users
            </div>
            <div className={"card-login"}>
                <p className={"text-info"}>Qidiruv:</p>
                <input
                    value={phone}
                    onChange={(e)=>searchs(e.target.value)}
                    className={"form-control my-2 bg-transparent text-info"}
                    placeholder={"+998*******"}
                    type={"text"}
                />
                <div className={"d-flex justify-content-between"}>
                    <div style={{width:300+"px"}}>
                        {
                            selectedFile ?  <img className={"w-100 h-100"}
                                 src={`http://localhost:8080/fileController?image=${selectedFile}`} alt=""/> :<h1 className={"text-info"}>PHOTO</h1>
                        }
                    </div>
                    <div className={"w-50 d-flex flex-column m-2"}>
                        <div className={"d-flex"}>
                            <h3 className={"text-info"}>Name: </h3>
                            {
                                selectFullName ?
                                    <input value={selectFullName} className={"form-control text-light bg-transparent "}
                                           type="text"/> :
                                    <input className={"form-control text-light bg-transparent"} value={fullName}
                                           onChange={(e) => setFullName(e.target.value)} type="text"/>
                            }


                        </div>
                        <div className={"d-flex"}>
                            <h3 className={"text-info"}>Phone: </h3>
                            {
                                selectPhone ?
                                    <input value={selectPhone} className={"form-control text-light bg-transparent"}
                                           type="text"/> :
                                    <input className={"form-control text-light  bg-transparent"} value={phone}
                                           onChange={(e) => setPhone(e.target.value)} type="text"/>
                            }
                        </div>
                        <div className={"d-flex"}>
                            <h3 className={"text-info"}>Subsc: </h3>
                            {
                                    <select onChange={(e)=>setSelectSubsc(e.target.value)} className={"form-control text-success bg-transparent"}>
                                        <option className={"bg-transparent "} value="0">Select subscription_type
                                        </option>
                                        {
                                            subsc.map((s) => <option key={s.id} value={s.id}>{s.name}</option>)
                                        }
                                    </select>
                            }
                            <button onClick={saveSubscription}  className={"btn btn-dark mx-3"}>
                                Tasdiqlash
                            </button>
                        </div>
                    </div>
                    <div>
                        ds
                    </div>
                </div>
                <button onClick={submit} className={"register-button"}>+</button>
                <table className={"table table-bordered table-responsive table-hover bg-transparent"}>
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
                 users &&   users.map((u,i)=> <tr   key={u.id} onClick={()=>selectUser(u)}>
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