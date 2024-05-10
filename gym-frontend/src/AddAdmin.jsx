import {useEffect, useState} from "react";
import apiCall from "./apicall/apiCall.js";
import {useNavigate} from "react-router-dom";

function AddAdmin() {
    const [fullName, setFullName] = useState("")
    const [phoneNumber, setPhoneNumber] = useState("")
    const [password, setPassword] = useState("")

    function addAdmin() {
        apiCall("/admin", "POST",{fullName, phoneNumber, password})
    }

    const navigets = useNavigate();

    useEffect(() => {
        const fetchData = async () => {
            try {
                if (localStorage.getItem("access_token")) {
                    await apiCall(`/user/admin`, "GET", {}, { Authorization: localStorage.getItem("access_token") });
                    console.log("salom");
                } else {
                    navigets("/login");
                }
            } catch (error) {
                navigets("/404");
            }
        };

        fetchData();

    }, [navigets]);

    return (
        <div>
            <div>
                <input type={"text"} value={fullName} onChange={(e)=>setFullName(e.target.value)} placeholder={"Ism..."}/>
                <input type={"text"} value={phoneNumber} onChange={(e)=>setPhoneNumber(e.target.value)} placeholder={"Telefon raqam..."}/>
                w            <input type={"text"} value={password} onChange={(e)=>setPassword(e.target.value)} placeholder={"Parol..."}/>
                <button onClick={()=>addAdmin()}>{"Qo'shish"}</button>
            </div>
        </div>
    );
}

export default AddAdmin;