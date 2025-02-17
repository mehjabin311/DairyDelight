import axios from "axios";
import { useEffect, useState } from "react";
import { Link, useNavigate } from 'react-router-dom';
import { toast } from "react-toastify";
import ConsumerDashboard from './ConsumerDashboard';  // Adjust import based on your app structure

const ConsumerSelection = () => {

    const [consumerPresent, setConsumerPresent] = useState({});
    const navigate = useNavigate();

    useEffect(() => {
        const email = sessionStorage.getItem('Consumer Email');
        if (email != null) {
            navigate('/consumerHome');
        }
    }, []);

    return (
        <div>
            {(consumerPresent.consumerId > 0) ? <ConsumerDashboard details={consumerPresent} /> : <><div className="container-md">
                <h1 style={{ margin: 30 }}>Join Our Premium Dairy Experience</h1>
                <img src="./images/becomeseller.webp" className="img-fluid" alt="Consumer Home" style={{ width: 1300, height: 320 }} />
            </div><div className="container-sm">
                    <div className="row">
                        <div className="col-sm-6">
                            <div className="card">
                                <div className="card-body">
                                    <h5 className="card-title">Start Your Premium Dairy Journey</h5>
                                    <p className="card-text">Just click the button below, fill the information, and explore products !!</p>
                                    <Link to='/consumerRegistration' className="btn btn-outline-primary">Goto Consumer Registration</Link>
                                </div>
                            </div>
                        </div>
                        <div className="col-sm-6">
                            <div className="card">
                                <div className="card-body">
                                    <h5 className="card-title">Already a Member?</h5>
                                    <p className="card-text">Click the button below to proceed to the login page.</p>
                                    <Link to='/consumerLogin' className="btn btn-outline-success">Goto Consumer Login</Link>
                                </div>
                            </div>
                        </div>
                    </div>
                </div></>}
        </div>
    )
}

export default ConsumerSelection;
