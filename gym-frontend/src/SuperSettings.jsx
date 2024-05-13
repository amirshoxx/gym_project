import axios from "axios";
import {useState} from "react";

function SuperSettings() {
    const [superAdmin, setSuperAdmin] = useState({
        fullName: "",
        password: "",
        phoneNumber: ""
    })

    function editSuperAdmin() {
        axios({url: "http://localhost:8080/super_admin", method: "PATCH", data: superAdmin}).then(({data})=>{
            console.log(data)
        })
    }

    return (
        <div>
            <div className={"d-flex flex-column align-items-center  "}>
                <h1>settings</h1>
                    <input onChange={(e)=>setSuperAdmin({...superAdmin,fullName:e.target.value })} placeholder={"Full name"} className={"form-control m-1 w-25 shadow"}/>
                    <input onChange={(e)=>setSuperAdmin({...superAdmin,phoneNumber:e.target.value })} placeholder={"Phone number"} className={"form-control m-1 w-25 shadow"}/>
                    <input onChange={(e)=>setSuperAdmin({...superAdmin,password:e.target.value })} placeholder={"Password"} className={"form-control m-1 w-25 shadow"}/>
                    <button onClick={()=>editSuperAdmin()} className={"btn btn-warning shadow w-25"}>Edit</button>
            </div>
        </div>
    );
}

export default SuperSettings;