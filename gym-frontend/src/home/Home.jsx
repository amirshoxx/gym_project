import "react"
import {useNavigate} from "react-router-dom";
import {useEffect, useState} from "react";
function Home() {
    const navigate = useNavigate();
    const [activeButton, setActiveButton] = useState(null);
    const handleNavigation = (path) => {
        navigate(path);
        setActiveButton(path);
    };
    let key = localStorage.getItem("key")

    useEffect(() => {

    }, [key]);
    return (
        <div className={"menu"}>

            {/*<div>*/}
            {/*  <img src={} alt=""/>*/}
            {/*</div>*/}
            <div>

                <button
                    className={activeButton === "/" ? "active" : ""}
                    onClick={() => handleNavigation("/")}
                >
                    Home
                </button>


            </div>
            <div>
                <button
                    className={activeButton === "/login" ? "active" : ""}
                    onClick={() => handleNavigation("/login")}
                >
                    Login
                </button>

                <button
                    className={activeButton === "/finding" ? "active" : ""}
                    onClick={() => handleNavigation("/finding")}
                >
                    Finding
                </button>
            </div>


        </div>
    );
}

export default Home;