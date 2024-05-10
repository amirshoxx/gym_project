// import React, {useEffect} from 'react';
// import {useNavigate} from "react-router-dom";
// import apiCall from "../apicall/apiCall.js";
//
// function SuperAdmin() {
//
//     const naviget = useNavigate();
//
//     useEffect(()=>{
//
//
//         if (localStorage.getItem("access_token")!=null){
//             apiCall(`/user/super_admin`, "GET",{}, { Authorization:localStorage.getItem("access_token") })
//                 .then(() => {
//                 })
//                 .catch(() => {
//                     naviget("/admin_page");
//                 });
//
//         }else {
//             naviget("/404")
//         }
//
//     },[])
//     return (
//         <div>
//             salom
//         </div>
//     );
// }
//
// export default SuperAdmin;