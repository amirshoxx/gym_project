import {useNavigate} from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";
import {getAxios} from "./getAxios.jsx";



function Gym() {

    const [gym, setGym] = useState({
        name: "",
        location: ""
    })
    const [gyms, setGyms] = useState([])

    useEffect(() => {
        getGym()
    }, [])


    let navigate = useNavigate();

    function navigateAdmin() {
        navigate("/addAdmin")
    }

    function getGym() {
        getAxios({url: "http://localhost:8080/gym", method: "GET"}).then(({data}) => {
            setGyms(data)
        })
    }

    function postGym() {
        axios({url: "http://localhost:8080/gym", method: "POST", data: gym}).then(()=>{
            getGym()
            setGym({...gym,name:"",location: ""})

        })
    }

    function deleteGym(id) {
        axios({url: "http://localhost:8080/gym?id="+id, method: "DELETE"}).then(()=>{
            getGym()
        })
    }

    return (
        <div className={"container"}>
            <div className={"d-flex py-3 justify-content-center"}>
                <input value={gym.name} onChange={(e) => {
                    setGym({...gym, name: e.target.value})
                }} placeholder={"Zalning nomi"} className={"form-control m-1 w-25 shadow"}/>
                <input value={gym.location} onChange={(e) => {
                    setGym({...gym, location: e.target.value})
                }} placeholder={"Zalning manzili"} className={"form-control m-1 w-25 shadow"}/>
                <button onClick={() => postGym()} className={"btn btn-primary shadow"}>Add GYM</button>
            </div>
            <hr/>
            <div>
                <table className={" table table-striped"}>
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>Zal nomi</th>
                        <th>Zal lokatsiyasi</th>
                        <th>Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    {
                        gyms.map((itm, index) => <tr key={itm.id}>
                            <td>{index + 1}</td>
                            <td>{itm.name}</td>
                            <td>{itm.location}</td>
                            <td>
                                <button onClick={() => navigateAdmin()} className={"btn btn-success rounded-0 shadow"}>Admin
                                </button>
                                <button onClick={()=>deleteGym(itm.id)} className={"btn btn-danger rounded-0 shadow"}>Delete
                                </button>
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