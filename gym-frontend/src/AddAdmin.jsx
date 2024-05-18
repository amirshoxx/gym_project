import {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import {getAxios} from "./getAxios.jsx";
import {ToastContainer, toast} from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

function AddAdmin() {
    const {id} = useParams()
    const [userId, setId] = useState("")
    const [admin, setAdmin] = useState({
        fullName: "",
        phoneNumber: "",
        password: "",
        gymId: id
    })

    const [admins, setAdmins] = useState([])


    useEffect(() => {
        getAdmin()
    }, [])

    function getAdmin() {
        getAxios({url: "/admin?id=" + id, method: "GET"}).then(({data}) => {
            setAdmins(data)
        })
    }

    function addAdmin() {

        if (userId===""){
            getAxios({url: "/admin", method: "PUT", data: admin}).then(({data}) => {
                setAdmin({...admin, fullName: "", phoneNumber: "", password: "", gymId: ""})
                toast.success(data)
                getAdmin()
            })
        }else {
            getAxios({url: "/admin/edit?id="+id, method: "PUT", data: admin}).then(({data}) => {
                setAdmin({...admin, fullName: "", phoneNumber: "", password: "", gymId: ""})
                toast.success(data)
                getAdmin()
            })
        }

    }

    function editAdmin(id) {

        admin.fullName=admins[0].fullName
        admin.phoneNumber=admins[0].phoneNumber
        admin.password=admins[0].password
        setId(id);
    }


    return (
        <div className={"container"}>
            <ToastContainer/>
            <h1 className={" text-center"}> Add Admin To Gym </h1>
            <div className={"d-flex"}>

                <input  className={"form-control shadow m-1 rounded-0 w-25"} type={"text"} value={admin.fullName}
                       onChange={(e) => setAdmin({...admin, fullName: e.target.value})}
                       placeholder={"Ism..."}/>
                <input className={"form-control shadow m-1 rounded-0 w-25"} type={"text"} value={admin.phoneNumber}
                       onChange={(e) => setAdmin({...admin, phoneNumber: e.target.value})}
                       placeholder={"Telefon raqam..."}/>
                <input className={"form-control shadow m-1 rounded-0 w-25"} type={"text"} value={admin.password}
                       onChange={(e) => setAdmin({...admin, password: e.target.value})}
                       placeholder={"Parol..."}/>
                <button className={"btn btn-success rounded-0 shadow m-1 "} onClick={() => addAdmin()}>Add Admin
                </button>
            </div>
            <hr/>

            <div>
                <ul className={"list-group "}>
                    {admins.map((itm) => <li key={itm.id} className={"list-group-item d-flex justify-content-around"}>
                        <div><b className={"text-primary"}>Name</b> : {itm.fullName}</div>
                        <div><b className={"text-primary"}>Phone</b> : {itm.phoneNumber} </div>
                        <div><b className={"text-primary"}>Gym name</b> : {itm.gym.name}</div>
                        <div>
                            <button onClick={() => editAdmin(itm.id)}
                                    className={"btn btn-warning rounded-0 shadow"}>Edit
                            </button>
                        </div>
                    </li>)}
                </ul>
            </div>

        </div>
    );
}

export default AddAdmin;