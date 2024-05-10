import axios from "axios";

export function getAxios({url, method, data,headers}) {
    return axios({
        baseURL: "http://localhost:8080",
        url,
        method,
        data,
        headers,
    })
}