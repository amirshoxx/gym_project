import {FaHome} from "react-icons/fa"
import {useNavigate} from "react-router-dom";

function SuperAdmin() {

    let navigate = useNavigate();


    function navigateSettings() {
        navigate("/settings")
    }

    function navigateGym() {
        navigate("/gym")
    }


    return (
        <div>
            <div>
                <button onClick={()=>navigateGym()} className={"buttonS"}>GYMS</button>
                <button onClick={()=>navigateSettings()} className={"buttonS"}>Sozlamalar</button>
            </div>
        </div>
    );
}

export default SuperAdmin;