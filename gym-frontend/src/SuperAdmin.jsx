import {useNavigate} from "react-router-dom";
import {useEffect} from "react";
import apiCall from "./apicall/apiCall.js";

function SuperAdmin() {

        const naviget = useNavigate();

    useEffect(()=>{


        if (localStorage.getItem("access_token")!=null){
            apiCall(`/user/super_admin`, "GET",{}, { Authorization:localStorage.getItem("access_token") })
                .then(() => {
                })
                .catch(() => {
                    naviget("/admin_page");
                });

        }else {
            naviget("/404")
        }

    },[])

    let navigate = useNavigate();


    function navigateSettings() {
        navigate("/superSettings")
    }

    function navigateGym() {
        navigate("/gym")
    }


    return (
        <div className={"container d-flex justify-content-center"}>
                <button onClick={()=>navigateGym()} className={"btn btn-secondary m-1 rounded-0"}>GYMS</button>

                <button onClick={()=>navigateSettings()} className={"btn btn-secondary m-1 rounded-0"}>Sozlamalar</button>
        </div>
    );
}

export default SuperAdmin;