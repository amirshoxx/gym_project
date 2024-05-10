import React, {useEffect} from 'react';
import {useNavigate} from "react-router-dom";
import apiCall from "../apicall/apiCall.js";

function Admin() {
    const navigets = useNavigate();
    useEffect(()=>{


        if (localStorage.getItem("access_token")!=null){
            apiCall(`/user/super_admin`, "GET",{}, { Authorization:localStorage.getItem("access_token") })
                .then(() => {
                })
                .catch(() => {
                    navigets("/admin_page");
                });

        }else {
            navigets("/404")
        }

    },[])
    return (
        <div>
            admin
        </div>
    );
}

export default Admin;