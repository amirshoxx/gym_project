import { useNavigate } from "react-router-dom";
import { useEffect } from "react";
import apiCall from "./apicall/apiCall.js";

function SuperAdmin() {
    let navigate = useNavigate();

    useEffect(() => {
        const fetchData = async () => {
            try {
                if (localStorage.getItem("access_token")) {
                    const response = await apiCall("/user/admins", "GET", {}, { Authorization: localStorage.getItem("access_token") });

                    if (response.data === "super_admin") {
                        console.log("User is a super admin");
                    } else {
                        navigate("/login");
                    }
                } else {
                    navigate("/login");
                }
            } catch (error) {
                navigate("/login");
            }
        };

        fetchData();
    }, [navigate]);

    function navigateSettings() {
        navigate("/superSettings");
    }

    function navigateGym() {
        navigate("/gym");
    }

    return (
        <div className={"container d-flex justify-content-center"}>
            <button onClick={() => navigateGym()} className={"btn btn-secondary m-1 rounded-0"}>
                GYMS
            </button>
            <button onClick={() => navigateSettings()} className={"btn btn-secondary m-1 rounded-0"}>
                Sozlamalar
            </button>
        </div>
    );
}

export default SuperAdmin;
