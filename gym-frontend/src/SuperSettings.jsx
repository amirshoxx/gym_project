import axios from "axios";
import {useState} from "react";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

function SuperSettings() {
    const [superAdmin, setSuperAdmin] = useState({
        fullName: "",
        password: "",
        phoneNumber: ""
    })

    function editSuperAdmin() {
        const id = localStorage.getItem("userId");
        axios({url: "http://localhost:8080/super_admin?id=" + id, method: "PUT", data: superAdmin}).then(({data}) => {
            setSuperAdmin({...superAdmin, fullName: "", password: "", phoneNumber: ""})
            toast.success('Edited Information')
        })
    }

    return (
        <div>
            <ToastContainer/>
            <div className={"d-flex flex-column align-items-center  "}>
                <h1>settings</h1>
                <input value={superAdmin.fullName}
                       onChange={(e) => setSuperAdmin({...superAdmin, fullName: e.target.value})}
                       placeholder={"Full name"} className={"form-control m-1 w-25 shadow"}/>
                <input value={superAdmin.phoneNumber}
                       onChange={(e) => setSuperAdmin({...superAdmin, phoneNumber: e.target.value})}
                       placeholder={"Phone number"} className={"form-control m-1 w-25 shadow"}/>
                <input value={superAdmin.password}
                       onChange={(e) => setSuperAdmin({...superAdmin, password: e.target.value})}
                       placeholder={"Password"} className={"form-control m-1 w-25 shadow"}/>
                <button onClick={() => editSuperAdmin()} className={"btn btn-warning shadow w-25"}>Edit</button>
            </div>
        </div>
    );
}

export default SuperSettings;