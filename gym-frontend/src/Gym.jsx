import {useNavigate} from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";
import {getAxios} from "./getAxios.jsx";
import Rodal from 'rodal';
import 'rodal/lib/rodal.css';

function Gym() {

    const [gym, setGym] = useState({
        name: "",
        location: ""
    })
    const [gymid, setgymid] = useState("")
    const [gyms, setGyms] = useState([])
    const [visible, setVisible] = useState(false);
    const showModal = (id) => {
        setgymid(id)
        setVisible(true);
    };

    const hideModal = () => {
        setVisible(false);
    };

    useEffect(() => {
        getGym()
    }, [])
    let navigate = useNavigate();

    function navigateAdmin(id) {
        navigate(`/addAdmin/${id}`)
    }

    function getGym() {
        getAxios({url: "http://localhost:8080/gym", method: "GET"}).then(({data}) => {
            setGyms(data)
        })
    }

    function postGym() {
        axios({url: "http://localhost:8080/gym", method: "POST", data: gym}).then(() => {
            getGym()
            setGym({...gym, name: "", location: ""})

        })
    }


    const deleteContent = () => {
        axios({url: "http://localhost:8080/gym?id=" + gymid, method: "DELETE"}).then(() => {
            getGym()
        })
        setVisible(false);
    };


    return (
        <div className={"container"}>


            <Rodal visible={visible} onClose={hideModal}>
                <div className={"text-center py-4"}>
                    <p>Siz haqiqatdan ham ushbu gymni ochurasizmi?</p>
                    <button className={"btn btn-danger rounded-0 mx-1"} onClick={deleteContent}>Ha</button>
                    <button className={"btn btn-warning rounded-0 mx-1"} onClick={hideModal}>yoq</button>
                </div>
            </Rodal>


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
                                <button onClick={() => navigateAdmin(itm.id)}
                                        className={"btn btn-success rounded-0 shadow"}>Admin
                                </button>
                                <button onClick={() => showModal(itm.id)}
                                        className={"btn btn-danger rounded-0 shadow"}>Delete
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