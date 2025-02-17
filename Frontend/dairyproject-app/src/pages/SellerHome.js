import axios from "axios";
import { toast } from "react-toastify";
import { Link, useLocation, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import SellerDashboard from './SellerDashboard';

const SellerHome = () => {

    const [sellDetails, setSellDetails] = useState({});


    useEffect(() => {
        const email = sessionStorage.getItem('Seller Email');
        fetchSeller(email);
    }, []);

    const fetchSeller = (emailId) => {

        axios.get('http://localhost:9090/admin/fetchsellerbyemail', {
            params: {
                emailId
            }
        }).then((response) => {
            const result = response.data;
            setSellDetails(result);
        }).catch((error) => {
            console.log(error.message);
        });

    }

    return (

        <div>
            {(sellDetails.sellerId > 0) ? <SellerDashboard details={sellDetails} /> : <h1 style={{ margin: 50 }}>You need to login first </h1>}
        </div>

    )
}

export default SellerHome;