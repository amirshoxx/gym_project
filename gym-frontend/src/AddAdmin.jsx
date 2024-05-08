import {useState} from "react";
import apiCall from "./apicall/apiCall.js";

function AddAdmin() {
    const [fullName, setFullName] = useState("")
    const [phoneNumber, setPhoneNumber] = useState("")
    const [password, setPassword] = useState("")

    function addAdmin() {
        apiCall("/admin", "POST",{fullName, phoneNumber, password})
    }

    return (
        <div>
            <div>
                <input type={"text"} value={fullName} onChange={(e)=>setFullName(e.target.value)} placeholder={"Ism..."}/>
                <input type={"text"} value={phoneNumber} onChange={(e)=>setPhoneNumber(e.target.value)} placeholder={"Telefon raqam..."}/>
                <input type={"text"} value={password} onChange={(e)=>setPassword(e.target.value)} placeholder={"Parol..."}/>
                <button onClick={()=>addAdmin()}>{"Qo'shish"}</button>
            </div>
        </div>
    );
}

export default AddAdmin;