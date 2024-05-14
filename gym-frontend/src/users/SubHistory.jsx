import React, {useEffect} from "react";
import apiCall from "../apicall/apiCall.js";

function SubHistory() {

    useEffect(() => {
        getSubscription()
    }, []);

    function getSubscription(){
        apiCall("/subscription","GET",{},{Authorization:localStorage.getItem("access_token")}).then(res=>{
            setSubscription(res.data)
        }).catch(()=>{
            apiCall("/user/refresh", "POST", {}, { refreshToken : localStorage.getItem("refresh_token") })
                .then(res => {
                    localStorage.setItem("access_token", res.data);
                    getSubscription()
                })
        })
    }
    return (
        <div>
            <table className={"text-light table-dark table w-100"}>
                <thead>
                <tr>
                    <th>N/O</th>
                    <th>IMAGE</th>
                    <th>FULLNAME</th>
                    <th>PHONENUMBER</th>
                    <th>SUBSCRIPTION</th>
                    <th>PRICE</th>
                    <th>START_TIME</th>
                    <th>STATUS</th>
                    <th>Limited</th>
                    <th>END_TIME</th>
                    <th>K/Q</th>
                    <th>START</th>
                </tr>
                </thead>
                <tbody>
                {
                    subscription && subscription.map((u, i) => (
                        <tr key={u.id} onClick={() => selectUser(u)}>
                            <td>{i + 1}</td>
                            <td>
                                <img src={`http://localhost:8080/fileController?image=${u.image}`} alt=""/>
                            </td>
                            <td>{u.fullName}</td>
                            <td>{u.phoneNumber}</td>
                            <td>{u.name}</td>
                            <td>{u.price}</td>
                            <td>{u.startTime}</td>
                            <td>{u.status ? "QATNASHYAPTI" : "KETGAN"}</td>
                            <td>{u.limited ? "HARKUNLIK" : "KUN ORA"}</td>
                            <td>{u.endTime}</td>
                            <td>{u.dayCount}</td>
                            <td>
                              <button>
                                  history
                              </button>
                            </td>
                        </tr>
                    ))
                }
                </tbody>
            </table>

        </div>
    );
}

export default SubHistory;