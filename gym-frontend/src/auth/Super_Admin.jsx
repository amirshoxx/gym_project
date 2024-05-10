import React, {useEffect} from 'react';
import {useNavigate} from "react-router-dom";

function SuperAdmin() {
    
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
            salom
        </div>
    );
}

export default SuperAdmin;