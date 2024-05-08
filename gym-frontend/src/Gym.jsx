import {FaHome} from "react-icons/fa"
import {useNavigate} from "react-router-dom";

function Gym() {

    let navigate = useNavigate();


    function navigateLogin() {
        navigate("/login")
    }

    function navigateRegister() {
        navigate("/add_admin")
    }


    return (
        <div className={"bg-secondary-subtle w-100 d-flex align-items-center justify-content-between py-1 px-4"}>



        </div>
    );
}

export default Gym;