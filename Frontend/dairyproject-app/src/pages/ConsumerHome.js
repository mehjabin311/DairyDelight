import axios from "axios";
import { toast } from "react-toastify";
import { Link, useLocation, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import Login from './Login';
import ConsumerDasboard from "./ConsumerDashboard";

const ConsumerHome = () => {

    const [conDetails, setConDetails] = useState({});


    useEffect(() => {
        const email = sessionStorage.getItem('Consumer Email');
        fetchConsumer(email);
    }, []);

    const fetchConsumer = (emailId) => {

        axios.get('http://localhost:9090/admin/fetchconsumerbyemail', {
            params: {
                emailId
            }
        }).then((response) => {
            const result = response.data;
            setConDetails(result);
        }).catch((error) => {
            console.log(error.message);
        });

    }

    return (

        <div>
            {(conDetails.consumerId > 0) ? <ConsumerDasboard details={conDetails} /> : <h1 style={{ margin: 50 }}>You need to login first </h1>}
        </div>

    )
}

export default ConsumerHome;