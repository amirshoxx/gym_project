import {useNavigate} from "react-router-dom";

function Gym() {

    let navigate = useNavigate();

    function navigateAdmin() {
        navigate("/add_admin")
    }


    return (
        <div>
             <div>
<h1>GYM</h1>
             </div>
        </div>
    );
}

export default Gym;