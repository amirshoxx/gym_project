import React, {useEffect} from 'react';
import {useNavigate} from "react-router-dom";

function Admin() {
    const naviget = useNavigate()

    useEffect(()=>{
        if (localStorage.getItem("access_token")!=null){
            console.log("rol")
        }else {
            naviget("/404")
        }

    },[])
    return (
        <div>
            admin
        </div>
    );
}

export default Admin;