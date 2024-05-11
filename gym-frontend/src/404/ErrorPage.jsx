import  'react';
import img from "../404-not.jpg";

function ErrorPage() {
    return (
        <div className="img-container">
            <img src={img} alt="err"/>
        </div>
    );
}

export default ErrorPage;
