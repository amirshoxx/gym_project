import {useNavigate} from "react-router-dom";

function SuperAdmin() {

    let navigate = useNavigate();


    function navigateSettings() {
        navigate("/superSettings")
    }

    function navigateGym() {
        navigate("/gym")
    }


    return (
        <div className={"d-flex justify-content-center"}>
                <button onClick={()=>navigateGym()} className={"btn btn-secondary m-1 rounded-0"}>GYMS</button>

                <button onClick={()=>navigateSettings()} className={"btn btn-secondary m-1 rounded-0"}>Sozlamalar</button>
        </div>
    );
}

export default SuperAdmin;