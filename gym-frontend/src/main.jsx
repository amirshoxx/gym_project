import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.jsx'
import {BrowserRouter} from "react-router-dom";
import "bootstrap/dist/css/bootstrap.css"
import store from "./redux/store.jsx";
import { Provider } from 'react-redux';
import './Login.scss';
import './scss/Home.scss'
import './404Page.scss'
ReactDOM.createRoot(document.getElementById('root')).render(
    <React.StrictMode>
        <BrowserRouter>
            <Provider store={store}>
                <App />
            </Provider>
        </BrowserRouter>
    </React.StrictMode>,
)
