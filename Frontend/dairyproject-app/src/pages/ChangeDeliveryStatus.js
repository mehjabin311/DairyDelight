// import axios from "axios";
// import { useEffect, useState } from "react";
// import { useLocation, useNavigate } from "react-router-dom";
// import { toast } from "react-toastify";

// const ChangeDeliveryStatus = () => {
//     const location = useLocation();
//     const [details, setDetails] = useState([]);
//     const [status, setStatus] = useState('');
//     const navigate = useNavigate();

//     useEffect(() => {
//         const { proDetails } = location.state;
//         setDetails(proDetails);
//     }, []);

//     const UpdateStatus = (purchaseId) => {
//         axios.put(`http://localhost:9090/seller/changedeliverystatus/${purchaseId}/${status}` 
//         // {
//             // params: {
//             //     purchaseId, status
//             // }
//         ).then((response) => {
//             const result = response.data;
//             toast.success('Status Updated');
//             navigate('/sellerHome');
//         }).catch((error) => {
//             toast.error('Select Status to update !');
//         });
//     }

//     return (
//         <div className="container-md">

//             <div>
//                 <h1 style={{ marginTop: 20, marginBottom: 25, color: "darkgreen" }}>Change Delivery Status</h1>
//             </div>

//             <table className="table table-hover">
//                 <thead>
//                     <tr className="table-dark">
//                         <th>Purchase ID</th>
//                         <th>Product Name</th>
//                         <th>Quantity</th>
//                         <th>Total Amount (Rs.)</th>
//                         <th>Consumer Name</th>
//                         <th>Delivery Address</th>
//                         <th>Current Status</th>
//                         <th>Update Status</th>
//                         <th>Submit</th>
//                     </tr>
//                 </thead>
//                 <tbody>
//                     {details.map((details, index) => {
//                         return (
//                             <>
//                                 <tr>
//                                     <th>{index + 1}</th>
//                                     <td>{details.productDetails.name}</td>
//                                     <td>{details.quantity}</td>
//                                     <td>{details.totalPrice}</td>
//                                     <td>{details.consumerDetails.firstName} {details.consumerDetails.lastName}</td>
//                                     <td>{details.consumerDetails.street}, {details.consumerDetails.address.town}, {details.consumerDetails.address.pincode}</td>
//                                     <td>{details.status}</td>
//                                     <td>
//                                         <div style={{ width: 110, marginLeft: 22 }}>
//                                             <select className="form-select form-select-sm" aria-label=".form-select-sm example" onClick={(event) => setStatus(event.target.value)}>
//                                                 <option selected value=''>Choose</option>
//                                                 {/* <option value="Placed">Placed</option> */}
//                                                 <option value="Shipped">Shipped</option>
//                                                 <option value="Delivered">Delivered</option>
//                                             </select>
//                                         </div>
//                                     </td>
//                                     <td>
//                                         <div>
//                                             <button className="btn btn-success btn-sm" onClick={() => UpdateStatus(details.purchaseId)}>Update</button>
//                                         </div>
//                                     </td>
//                                 </tr>
//                             </>
//                         )
//                     })}
//                 </tbody>
//             </table>
//         </div>
//     )
// }

// export default ChangeDeliveryStatus;
import axios from "axios";
import { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { toast } from "react-toastify";

const ChangeDeliveryStatus = () => {
    const location = useLocation();
    const [details, setDetails] = useState([]);
    const [status, setStatus] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        const { proDetails } = location.state;
        setDetails(proDetails);
    }, [location.state]);

    const fetchUpdatedDetails = () => {
        const emailId = sessionStorage.getItem('Seller Email'); // Assuming you have seller email stored in session
        axios.get(`http://localhost:9090/seller/getpurchaserecords`, {
            params: { emailId }
        }).then((response) => {
            const result = response.data;
            console.log("Updated purchase details:", result); // Log the updated data
            setDetails(result);
        }).catch((error) => {
            console.log("Error fetching updated details:", error);
        });
    };

    const UpdateStatus = (purchaseId) => {
        if (status) {
            axios.put(`http://localhost:9090/seller/changedeliverystatus/${purchaseId}/${status}`)
                .then((response) => {
                    const result = response.data;
                    toast.success('Status Updated');
                    fetchUpdatedDetails(); // Fetch the updated details
                }).catch((error) => {
                    toast.error('Error updating status. Please try again.');
                });
        } else {
            toast.warning('Select Status to update!');
        }
    };

    return (
        <div className="container-md">
            <div className="d-flex justify-content-start">
                <button className="btn btn-success btn-md px-3 py-2 mb-3 mt-5" onClick={() => navigate(-1)}>
                    ← Back
                </button>
            </div>

            <div>
                <h1 style={{ marginTop: 20, marginBottom: 25, color: "darkgreen" }}>Change Delivery Status</h1>
            </div>
            <table className="table table-hover">
                <thead>
                    <tr className="table-dark">
                        <th>Purchase ID</th>
                        <th>Product Name</th>
                        <th>Quantity</th>
                        <th>Total Amount (Rs.)</th>
                        <th>Consumer Name</th>
                        <th>Delivery Address</th>
                        <th>Current Status</th>
                        <th>Update Status</th>
                        <th>Submit</th>
                    </tr>
                </thead>
                <tbody>
                    {details.map((detail, index) => (
                        <tr key={index}>
                            <th>{index + 1}</th>
                            <td>{detail.productDetails?.name}</td>
                            <td>{detail.quantity}</td>
                            <td>{detail.totalPrice}</td>
                            <td>{detail.consumerDetails?.firstName} {detail.consumerDetails?.lastName}</td>
                            <td>{detail.consumerDetails?.street}, {detail.consumerDetails?.address.town}, {detail.consumerDetails?.address.pincode}</td>
                            <td>{detail.status}</td>
                            <td>
                                <div style={{ width: 110, marginLeft: 22 }}>
                                    <select className="form-select form-select-sm" aria-label=".form-select-sm example" onChange={(event) => setStatus(event.target.value)}>
                                        <option selected value=''>Choose</option>
                                        <option value="Shipped">Shipped</option>
                                        <option value="Delivered">Delivered</option>
                                    </select>
                                </div>
                            </td>
                            <td>
                                <div className="d-flex justify-content-start">

                                </div>
                                <div>
                                    <button className="btn btn-success btn-sm" onClick={() => UpdateStatus(detail.purchaseId)}>Update</button>
                                </div>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default ChangeDeliveryStatus;
