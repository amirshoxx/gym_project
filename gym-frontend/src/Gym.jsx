import {useNavigate} from "react-router-dom";
import {useEffect, useState} from "react";

function Gym() {

    const [gym, setGym] = useState({
        name: "",
        location: ""
    })
    const [gyms, setGyms] = useState([])

    useEffect(() => {

    }, [])


    let navigate = useNavigate();

    function navigateAdmin() {
        navigate("/add_admin")
    }

    function getGym() {
        axios({url: "http://localhost:8081/gym", method: "GET"}).then(({data}) => {
            setGyms(data)
        })
    }

    function postGym() {
        axios({url: "http://localhost:8081/gym", method: "POST", data: gym})
    }

    return (
        <div className={"container"}>
            <div className={"d-flex py-3 justify-content-center"}>
                <input onChange={(e) => {
                    setGym({...gym, name: e.target.value})
                }} placeholder={"Zalning nomi"} className={"form-control m-1 w-25 shadow"}/>
                <input onChange={(e) => {
                    setGym({...gym, location: e.target.value})
                }} placeholder={"Zalning manzili"} className={"form-control m-1 w-25 shadow"}/>
                <button onClick={() => postGym()} className={"btn btn-primary shadow"}>Add GYM</button>
            </div>
            <hr/>
            <div>
                <table className={" table table-striped"}>
                    <thead>
                    <tr>
                        <th>Zal nomi</th>
                        <th>Zal lokatsiyasi</th>
                    </tr>
                    </thead>
                    <tbody>
                    {
                        gyms.map((itm, index) => <tr key={itm.id}>
                            <td>{index + 1}</td>
                            <td>{itm.name}</td>
                            <td>{itm.location}</td>
                            <td>
                                <button onClick={()=>navigateAdmin()} className={"btn btn-success rounded-0"}>Admin</button>
                            </td>
                        </tr>)
                    }
                    </tbody>
                </table>
            </div>
        </div>
    );
}

export default Gym;