import {FaHome} from "react-icons/fa"
import {useNavigate} from "react-router-dom";

function Gym() {

    let navigate = useNavigate();

    function navigateAdmin() {
        navigate("/add_admin")
    }


    return (
        <div>
             <div>
                 
             </div>
        </div>
    );
}

export default Gym;