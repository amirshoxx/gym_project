import "react"
import {useNavigate} from "react-router-dom";
import apiCall from "../apicall/apiCall.js";
import {useEffect, useState} from "react";
import "./User.css"
import user from"./user.png"
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import "bootstrap/dist/css/bootstrap.css"
import Rodal from 'rodal';
import 'rodal/lib/rodal.css';
function User() {
    useEffect(()=>{
        getSubscriptionType()
        getSubscription()
    },[])
    const [fullName, setFullName] = useState('')
    const [userId, setUserId] = useState('')
    const [selectFullName, setSelectFullName] = useState('')
    const [phone, setPhone] = useState('')
    const [selectPhone, setSelectPhone] = useState('')
    const [selectedFile, setSelectedFile] = useState("");
    const [selectSubsc,setSelectSubsc] = useState("")
    const [subsc, setSubsc] = useState([]);
    const [subscription,setSubscription] = useState([])
    const [visable,setVisable] = useState(false)
    const [subjectId,setSubjectId] = useState("")
    function getSubscription(){
        apiCall("/subscription","GET",{},{Authorization:localStorage.getItem("access_token")}).then(res=>{
            setSubscription(res.data)
        }).catch(()=>{
            apiCall("/user/refresh", "POST", {}, { refreshToken : localStorage.getItem("refresh_token") })
                .then(res => {
                    localStorage.setItem("access_token", res.data);
                    getSubscription()
                })
        })
    }
    const navigets = useNavigate();

    function openModal(){
        setVisable(!visable)
    }

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



    function searchs(value) {
        setPhone(value)
       if (value==""){
           getSubscription()
       }else {
           apiCall("/subscription/search?phoneNumber="+phone,"GET",{},{Authorization:localStorage.getItem("access_token")}).then(res=>{
               setSubscription(res.data)
           })  .catch(()=>
               apiCall("/user/refresh", "POST", {}, { refreshToken : localStorage.getItem("refresh_token") })
                   .then(res => {
                       localStorage.setItem("access_token", res.data);
                   }))
       }
    }
    function selectUser(u) {
        setUserId(u.userId)
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
            userId:  userId && userId,
            image:selectedFile?selectedFile:user,
            fullName:selectFullName?selectFullName:fullName,
            phoneNumber:selectPhone?selectPhone:phone,
            subscriptionId:selectSubsc
        },{Authorization:localStorage.getItem("access_token")})
            .then(() =>
                getSubscription(),
                toast.success("Add Successfully"),
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

    function isCome(id) {
        apiCall("/subscription?id="+id,"PATCH",{},{Authorization:localStorage.getItem("access_token")}).then(res=>{
            getSubscription()
            if(res.data.status === false){
                toast.error("Tarifingiz tugadi")
            }else {
                toast.success(res.data.dayCount +" kun qoldi")
            }
        }).catch(()=>{
            apiCall("/user/refresh", "POST", {}, { refreshToken : localStorage.getItem("refresh_token") })
                .then(res => {
                    localStorage.setItem("access_token", res.data);
                    getSubscription()
                })
        })
    }

    function editeSubscription(id) {
        apiCall("/subscription?id="+id,"PUT",{},{Authorization:localStorage.getItem("access_token")}).then(res=>{
            getSubscription()
        }).catch(()=>{
            apiCall("/user/refresh", "POST", {}, { refreshToken : localStorage.getItem("refresh_token") })
                .then(res => {
                    localStorage.setItem("access_token", res.data);
                    getSubscription()
                })
        })
    }

    function selectSubject(id) {
            openModal()
        setSubjectId(id)
        setSelectSubsc("")
        setSelectedFile("")
        setSelectFullName("")
        setSelectPhone("")
    }

    function subscriptionEdite() {
        apiCall("/subscription/select/subscription?id="+subjectId,"POST",{
             subscriptionId:selectSubsc
        },{Authorization:localStorage.getItem("access_token")}).then(res=>{
            getSubscription()
        }).catch(()=>{
            apiCall("/user/refresh", "POST", {}, { refreshToken : localStorage.getItem("refresh_token") })
                .then(res => {
                    localStorage.setItem("access_token", res.data);
                    getSubscription()
                })
        })
    }

    return (
        <div className={"user_sv"} >
            <Rodal visible={visable} onClose={openModal}>
                    <select onChange={(e) => setSelectSubsc(e.target.value)}
                            className={"form-control text-success bg-transparent"}>
                        <option className={"bg-transparent "} value="0">Select subscription_type
                        </option>
                        {
                            subsc.map((s) => <option key={s.id} value={s.id}>{s.name}</option>)
                        }
                    </select>
                <button onClick={()=>subscriptionEdite()}>save</button>
            </Rodal>
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
                                <select onChange={(e) => setSelectSubsc(e.target.value)}
                                        className={"form-control text-success bg-transparent"}>
                                    <option className={"bg-transparent "} value="0">Select subscription_type
                                    </option>
                                    {
                                        subsc.map((s) => <option key={s.id} value={s.id}>{s.name}</option>)
                                    }
                                </select>
                            }
                            <button onClick={saveSubscription} className={"btn btn-dark mx-3"}>
                                Tasdiqlash
                            </button>
                        </div>
                    </div>
                    <div>
                        ds
                    </div>
                </div>
                <table className={"text-light table-dark table w-100"}>
                    <thead>
                    <tr>
                        <th>N/O</th>
                        <th>IMAGE</th>
                        <th>FULLNAME</th>
                        <th>PHONENUMBER</th>
                        <th>SUBSCRIPTION</th>
                        <th>PRICE</th>
                        <th>START_TIME</th>
                        <th>STATUS</th>
                        <th>Limited</th>
                        <th>END_TIME</th>
                        <th>K/Q</th>
                        <th>START</th>
                    </tr>
                    </thead>
                    <tbody>
                    {
                        subscription && subscription.map((u, i) => (
                            <tr key={u.id} onClick={()=>selectUser(u)}>
                                <td>{i + 1}</td>
                                <td>
                                    <img src={`http://localhost:8080/fileController?image=${u.image}`} alt=""/>
                                </td>
                                <td>{u.fullName}</td>
                                <td>{u.phoneNumber}</td>
                                <td>{u.name}</td>
                                <td>{u.price}</td>
                                <td>{u.startTime}</td>
                                <td>{u.status ? "QATNASHYAPTI" : "KETGAN"}</td>
                                <td>{u.limited ? "HARKUNLIK" : "KUN ORA"}</td>
                                <td>{u.endTime}</td>
                                <td>{u.dayCount}</td>
                                <td>
                                    {
                                        u.startTime ?
                                            <div>
                                                {
                                                    u.dayCount <= 0 ? <button onClick={()=>selectSubject(u.id)} className={"btn btn-outline-success"}>Select</button> :
                                                        <button onClick={() => isCome(u.id)}
                                                                className={"btn btn-outline-info"}>Tasdiqlash</button>

                                                }
                                            </div>
                                            :
                                            <button onClick={() => editeSubscription(u.id)} className={"btn btn-outline-success"}>Start</button>
                                    }

                                </td>
                            </tr>
                        ))
                    }
                    </tbody>
                </table>
            </div>
        </div>
    );
}

export default User;