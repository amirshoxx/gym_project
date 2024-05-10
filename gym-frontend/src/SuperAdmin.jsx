import {useNavigate} from "react-router-dom";
import {useEffect} from "react";
import apiCall from "./apicall/apiCall.js";

function SuperAdmin() {


    let navigate = useNavigate();



    useEffect(() => {
        const fetchData = async () => {
                if (localStorage.getItem("access_token")) {
                    await apiCall(`/user/admins`, "GET", {}, { Authorization: localStorage.getItem("access_token") }).then((res)=>{
                        if (res.data){
                            alert("salom admin")
                        }else {
                            navigate("/login")
                        }
                    });

                } else {
                    navigate("/404");
                }
        };

        fetchData();

    }, [navigate]);




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