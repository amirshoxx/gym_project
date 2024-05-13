import {useEffect, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import {getAxios} from "./getAxios.jsx";

function AddAdmin() {
    const {id} = useParams()
    const [admin, setAdmin] = useState({
        fullName: "",
        phoneNumber: "",
        password: "",
        gymId: id
    })

    function addAdmin() {
        getAxios({url: "/admin", method: "PUT", data: admin}).then(({data}) => {
            console.log(data)
            setAdmin({...admin, fullName: "", phoneNumber: "", password: "", gymId: ""})
        })
    }

    const navigets = useNavigate();

    // useEffect(() => {
    //     const fetchData = async () => {
    //         try {
    //             if (localStorage.getItem("access_token")) {
    //                 const response = await apiCall("/user/admin", "GET", {}, { Authorization: localStorage.getItem("access_token") });
    //                 if (response.data === "admin") {
    //                     console.log("User is a super admin");
    //                 } else {
    //                     navigets("/login");
    //                 }
    //             } else {
    //                 navigets("/login");
    //             }
    //         } catch (error) {
    //             navigets("/login");
    //         }
    //     };
    //
    //     fetchData();
    // }, [navigets]);

    return (
        <div className={"container"}>

            <h1 className={" text-center"}> Add Admin To Gym </h1>
            <div className={"d-flex"}>

                <input className={"form-control shadow m-1 rounded-0 w-25"} type={"text"} value={admin.fullName}
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


        </div>
    );
}

export default AddAdmin;