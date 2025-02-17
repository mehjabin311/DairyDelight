import axios from "axios";
import { toast } from 'react-toastify';
import { useState } from 'react';
import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';


const AllConsumerAdmin = () => {

    const [consumerDetails, setConsumerDetails] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        fetchAllConsumerDetails();
    }, []);

    const fetchAllConsumerDetails = () => {

        axios.get('http://localhost:9090/admin/fetchallconsumers').then((response) => {
            const result = response.data;
            setConsumerDetails(result);
        }).catch((error) => {
            toast.error('Error fetching registered consumer');
        });

    }

    const DeleteConsumer = (consumerId) => {

        axios.get('http://localhost:9090/admin/removeconsumeraccount', {
            params: {
                consumerId
            }
        }).then((response) => {
            const result = response.data;
            toast.success(result);
            fetchAllConsumerDetails();
        }).catch((error) => {
            toast.error('Error while deleting consumer account !');
        });

    }

    return (

        <div className="container-md">
            <div className="d-flex justify-content-start">
                <button className="btn btn-success btn-md px-3 py-2 mb-3 mt-5" onClick={() => navigate(-1)}>
                    ← Back
                </button>
            </div>
            <div>
                <h1 style={{ marginTop: 20, marginBottom: 25, color: "blue" }}>Consumers Detail</h1>
            </div>
            <div className="container">
                <table className="table table-hover">
                    <thead>
                        <tr className="table-light">
                            <th>ID</th>
                            <th>Name</th>
                            <th>Email</th>
                            <th>Gender</th>
                            <th>Phone</th>
                            <th>Street</th>
                            <th>Town</th>
                            <th>District</th>
                            <th>Pincode</th>
                            <th>State</th>
                            <th>Country</th>
                            <th>Remove</th>
                        </tr>
                    </thead>
                    <tbody>
                        {consumerDetails.map((list) => {
                            return (
                                <>
                                    <tr>
                                        <th>{list.consumerId}</th>
                                        <td>{list.firstName} {list.lastName}</td>
                                        <td>{list.emailId}</td>
                                        <td>{list.gender}</td>
                                        <td>{list.phoneNumber}</td>
                                        <td>{list.street}</td>
                                        <td>{list.address.town}</td>
                                        <td>{list.address.district}</td>
                                        <td>{list.address.pincode}</td>
                                        <td>{list.address.state}</td>
                                        <td>{list.address.country}</td>
                                        <td>
                                            <div>
                                                <button className="btn btn-outline-danger btn-sm" onClick={() => DeleteConsumer(list.consumerId)}>Remove</button>
                                            </div>
                                        </td>
                                    </tr>
                                </>
                            )
                        })}
                    </tbody>
                </table>
            </div>
        </div>

    )
}


export default AllConsumerAdmin;