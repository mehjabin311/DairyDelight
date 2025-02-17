import axios from "axios";
import { toast } from "react-toastify";
import { Link, Navigate, useLocation, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";

const ConsumerDasboard = (props) => {

    const { details } = props;
    const navigate = useNavigate();

    const Logout = () => {
        toast.success('Logout Successfull');
        sessionStorage.removeItem('Consumer Email');
        navigate('/consumerLogin');
    }

    // const GetProductDetails = (emailId) => {

    //     if (emailId != null) {

    //         axios.get('http://localhost:9090/consumer/getpurchaserecords', {
    //             params: {
    //                 emailId
    //             }
    //         }).then((response) => {
    //             const result = response.data;
    //             console.log(result)
    //             navigate('/purchaseDetails', { state: { purchaseData: result } });
    //         }).catch((error) => {
    //             toast.error("Something Went Wrong !");
    //         });


    //     }

    // }
    const GetProductDetails = (emailId) => {
        if (emailId != null) {
            axios.get('http://localhost:9090/consumer/getpurchaserecords', {
                params: { emailId },
                headers: {
                    'Cache-Control': 'no-cache'
                }
            }).then((response) => {
                const result = response.data;
                console.log("backend response:", result); // Log the backend response correctly
                navigate('/purchaseDetails', { state: { purchaseData: result } });
            }).catch((error) => {
                console.error("Error fetching purchase details:", error);
                toast.error("Something went wrong!");
            });
        }
    }
    
    const DeleteAccount = (consumerId) => {
        navigate('/deleteConsumerAccount', { state: { consumerId: consumerId } });
    }


    return (
        <div>
            <div className="container-md">
                <h1 style={{ marginBottom: 20, float: "left" }}>Hello, {details.firstName}</h1>
                <div className="d-grid gap-2 d-md-flex justify-content-md-end" style={{ marginTop: 20 }}>
                    <button className="btn btn-outline-primary" onClick={Logout} type="button">Logout</button>
                </div>
                <img src="./images/consumerdashboard.png" className="img-fluid" alt="Consumer Home" style={{ width: 1300, height: 300 }} />
            </div >
            <div className="container-md" style={{ marginTop: 20 }}>
                <div className="row">
                    <div className="col-sm-6">
                        <div className="card">
                            <div className="card-body">
                                <h5 className="card-title">Update Information</h5>
                                <p className="card-text">Update all your profile information such as Name, Address, Phone Number, etc.</p>
                                <Link className="btn btn-outline-success" to="/updateConsumerDetails">Update Profile</Link>
                            </div>
                        </div>
                    </div>
                    <div className="col-sm-6">
                        <div className="card">
                            <div className="card-body">
                                <h5 className="card-title">Change Password</h5>
                                <p className="card-text">It is always better for security to frequently change your password</p>
                                <Link className="btn btn-outline-danger" to="/changeConsumerPassword">Change Password</Link>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div className="container-md" style={{ marginTop: 20 }}>
                <div className="row">
                    <div className="col-sm-6">
                        <div className="card">
                            <div className="card-body">
                                <h5 className="card-title">Purchase Details</h5>
                                <p className="card-text">Here you get to see all your recent purchase records </p>
                                <button className="btn btn-outline-info" onClick={() => GetProductDetails(details.emailId)}>Get Details</button>
                            </div>
                        </div>
                    </div>
                    <div className="col-sm-6">
                        <div className="card">
                            <div className="card-body">
                                <h5 className="card-title">Raise Query</h5>
                                <p className="card-text">Having any issue with our service ?</p>
                                <Link className="btn btn-outline-warning" to="/consumerQuery">Get Query Form</Link>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div className="container-md" style={{ marginTop: 20 }}>
                <div className="row">
                    <div className="col-sm-12">
                        <div className="card">
                            <div className="card-body">
                                <h5 className="card-title">Delete Account</h5>
                                <p className="card-text">Want to discontinue with this account ? <br /> </p>
                                <a className="btn btn-danger" onClick={() => DeleteAccount(details.consumerId)}>Remove Account</a>
                                <p className="card-text">(all information get deleted)</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div style={{ margin: 30 }}>

            </div>
        </div >
    )


}


export default ConsumerDasboard;