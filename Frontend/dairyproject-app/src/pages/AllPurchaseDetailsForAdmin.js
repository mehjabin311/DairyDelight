import axios from "axios";
import { useEffect, useState } from "react";
import { toast } from "react-toastify";
import { useNavigate } from 'react-router-dom';

const AllPurchaseDetailsForAdmin = () => {
    const [purchaseDetails, setPurchaseDetails] = useState([]);
    const [sellerCache, setSellerCache] = useState(new Map()); // Store unique seller details
    const navigate = useNavigate();
    useEffect(() => {
        fetchAllPurchaseDetails();
    }, []);

    const fetchAllPurchaseDetails = async () => {
        try {
            const response = await axios.get("http://localhost:9090/admin/products/getallpurchasedetails");
            const result = response.data;
            console.log("Backend response:", result);

            const tempSellerCache = new Map(sellerCache);

            // Extract unique seller details
            result.forEach((item) => {
                if (item.sellerDetails && typeof item.sellerDetails === "object") {
                    tempSellerCache.set(item.sellerDetails.sellerId, item.sellerDetails);
                }
            });

            setSellerCache(new Map(tempSellerCache));

            // Assign correct seller details
            const updatedDetails = result.map((item) => {
                let sellerDetails = item.sellerDetails;

                if (typeof sellerDetails === "number") {
                    // If only sellerId is given, fetch from cache
                    sellerDetails = tempSellerCache.get(sellerDetails) || {
                        sellerId: sellerDetails,
                        firstName: "Unknown",
                        lastName: "",
                        emailId: "",
                        phoneNumber: "",
                        street: "",
                        username: "",
                        address: {},
                    };
                }

                return { ...item, sellerDetails };
            });

            setPurchaseDetails(updatedDetails);
        } catch (error) {
            console.error("Error fetching purchase details:", error);
            toast.error("Error while fetching purchase details!");
        }
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
                <h1 style={{ marginTop: 20, marginBottom: 25, color: "black" }}>Purchase Details</h1>
            </div>

            <div className="container-md">
                <table className="table table-hover">
                    <thead className="table-success">
                        <tr>
                            <th>ID</th>
                            <th>Consumer Name</th>
                            <th>Product Name</th>
                            <th>Quantity</th>
                            <th>Total Price</th>
                            <th>Payment Mode</th>
                            <th>Seller Name</th>
                            <th>Status</th>
                            <th>Transaction ID</th>
                            <th>Date / Time</th>
                        </tr>
                    </thead>
                    <tbody>
                        {purchaseDetails.map((list, index) => (
                            <tr key={index}>
                                <th>{list.purchaseId}</th>
                                <td>{list.consumerDetails.firstName} {list.consumerDetails.lastName}</td>
                                <td>{list.productDetails.name}</td>
                                <td>{list.quantity}</td>
                                <td>{list.totalPrice}</td>
                                <td>{list.paymentMode}</td>
                                <td>{`${list.sellerDetails?.firstName || "Unknown"} ${list.sellerDetails?.lastName || ""}`}</td>
                                <th>{list.status}</th>
                                <td>{list.transactionId}</td>
                                <td>{list.dateTime}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default AllPurchaseDetailsForAdmin;
// import axios from "axios";
// import { useEffect, useState } from 'react';
// import { toast } from "react-toastify";

// const AllPurchaseDetailsForAdmin = () => {

//     const [purchaseDetails, setPurchaseDetails] = useState([]);

//     useEffect(() => {
//         fetchAllPurchaseDetails();
//     }, []);

//     const fetchAllPurchaseDetails = () => {

//         axios.get('http://localhost:9090/admin/products/getallpurchasedetails').then((response) => {
//             const result = response.data;
//             setPurchaseDetails(result);
//         }).catch((error) => {
//             toast.error('Error while fetching purchase details !');
//         });

//     }

//     return (

//         <div className="container-md">
//             <div>
//                 <h1 style={{ marginTop: 20, marginBottom: 25, color: "black" }}>Purchase Details</h1>
//             </div>
//             <div className="container-md">
//                 <table className="table table-hover">
//                     <thead className="table-secondary">
//                         <tr>
//                             <th>ID</th>
//                             <th>Consumer Name</th>
//                             <th>Product Name</th>
//                             <th>Quantity</th>
//                             <th>Total Price</th>
//                             <th>Payment Mode</th>
//                             <th>Seller Name</th>
//                             <th>Status</th>
//                             <th>Transaction ID</th>
//                             <th>Date / Time</th>
//                         </tr>
//                     </thead>
//                     <tbody>
//                         {purchaseDetails.map((list) => {
//                             return (
//                                 <>
//                                     <tr>
//                                         <th>{list.purchaseId}</th>
//                                         <td>{list.consumerDetails.firstName} {list.consumerDetails.lastName}</td>
//                                         <td>{list.productDetails.name}</td>
//                                         <td>{list.quantity}</td>
//                                         <td>{list.totalPrice}</td>
//                                         <td>{list.paymentMode}</td>
//                                         <td>{list.sellerDetails.firstName} {list.sellerDetails.lastName}</td>
//                                         <th>{list.status}</th>
//                                         <td>{list.transactionId}</td>
//                                         <td>{list.dateTime}</td>
//                                     </tr>
//                                 </>
//                             )
//                         })}
//                     </tbody>
//                 </table>
//             </div>

//         </div>
//     )


// }


// export default AllPurchaseDetailsForAdmin;