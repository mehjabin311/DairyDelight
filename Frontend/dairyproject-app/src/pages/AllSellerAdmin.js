import axios from "axios";
import { toast } from 'react-toastify';
import { useState } from 'react';
import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';


const AllSellerAdmin = () => {

    const [sellerDetails, setSellerDetails] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        fetchAllSellerDetails();
    }, []);

    const fetchAllSellerDetails = () => {

        axios.get('http://localhost:9090/admin/fetchallsellers').then((response) => {
            const result = response.data;
            setSellerDetails(result);
            
        }).catch((error) => {
            toast.error('Error fetching registered sellers');
        });

    }

    const getSellerProducts = (sellerEmail) => {
        navigate('/sellerProductsDetailsForAdmin', { state: { emailId: sellerEmail } });
    }

    const DeleteSeller = (sellerId) => {
        console.log(sellerId)
        axios.get('http://localhost:9090/admin/removeselleraccount', {
            params: {
                sellerId
            }
        }).then((response) => {
            const result = response.data;
            console.log(result)
            toast.success(result);
            fetchAllSellerDetails();
        }).catch((error) => {
            toast.error('Error while deleting seller account !');
        });

    };

    return (
        <div className="container-md">
            {/* Back Button Positioned to the Left */}
            <div className="d-flex justify-content-start">
                <button className="btn btn-success btn-md px-3 py-2 mb-3 mt-5" onClick={() => navigate(-1)}>
                    ← Back
                </button>
            </div>

        
            <div>
                <h1 style={{ marginTop: 20, marginBottom: 25, color: "darkorange" }}>Seller Detail</h1>
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
                            <th>Products</th>
                            <th>Remove</th>
                        </tr>
                    </thead>
                    <tbody>
                        {sellerDetails.map((list) => {
                            return (
                                <>
                                    <tr>
                                        <th>{list.sellerId}</th>
                                        <td>{list.firstName} {list.lastName}</td>
                                        <td>{list.emailId}</td>
                                        <td>{list.gender}</td>
                                        <td>{list.phoneNumber}</td>
                                        <td>{list.street}</td>
                                        <td>{list.address.town}</td>
                                        <td>{list.address.district}</td>
                                        <td>{list.address.pincode}</td>
                                        <td>{list.address.state}</td>
                                        <td>
                                            <div>
                                                <button className="btn btn-outline-success btn-sm" style={{ width: 70 }} onClick={() => getSellerProducts(list.emailId)}>Get List</button>
                                            </div>
                                        </td>
                                        <td>
                                            <div>
                                                <button className="btn btn-outline-danger btn-sm" onClick={() => DeleteSeller(list.sellerId)}>Remove</button>
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



export default AllSellerAdmin;