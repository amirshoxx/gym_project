import axios from "axios";

export default function (url,method="GET",data,headers){
    return axios({
        baseURL:"http://localhost:8081",
        url,
        method,
        data,
        headers
    })
}