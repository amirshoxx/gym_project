import  'react';
import {useEffect, useState} from "react";
import apiCall from "../apicall/apiCall.js";
import {useNavigate} from "react-router-dom";
import {toast, ToastContainer} from "react-toastify";
import "bootstrap/js/src/button.js";
import img from "../users/photo_2024-05-13_13-34-55.jpg"
import "../users/User.css"
import Rodal from "rodal";

function Subscription() {
    useEffect(()=>{
     getSubscription()
    },[])
    const [subscription,setSubscription] = useState([])
    const [visable,setVisable] = useState([])

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
    function openModal(){
        setVisable(!visable)
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

    return (
        <div style={{backgroundImage: `url(${img})`}} className={"svg"} >
            <ToastContainer/>
            <Rodal visible={visable} onClose={openModal}>

            </Rodal>
            <div className="container">
                <table className={"table table-bordered table-hover table-responsive table-dark"}>
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
                            <tr key={u.id}>
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
                                            <button onClick={() => isCome(u.id)}
                                                    className={"btn btn-outline-info"}>Tasdiqlash</button> :
                                            <button onClick={() => editeSubscription(u.id)}
                                                    className={"btn btn-outline-success"}>Start
                                            </button>
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

export default Subscription;