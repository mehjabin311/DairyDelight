import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";

const PurchaseDetails = () => {
    const [details, setDetails] = useState([]);
    const [sellerCache, setSellerCache] = useState(new Map()); // Store unique seller details
    const navigate = useNavigate();

    useEffect(() => {
        const emailId = sessionStorage.getItem("Consumer Email"); // Retrieve email from session storage
        if (emailId) {
            fetchPurchaseDetails(emailId);
        } else {
            toast.error("Consumer email not found.");
            navigate("/consumerLogin"); // Redirect to login if email is not found
        }
    }, [navigate]);

    const fetchPurchaseDetails = async (emailId) => {
        try {
            const response = await axios.get("http://localhost:9090/consumer/getpurchaserecords", {
                params: { emailId },
                headers: { "Cache-Control": "no-cache" },
            });

            const result = response.data;
            console.log("Backend response:", result);

            // Use a Map to store unique seller details
            const tempSellerCache = new Map(sellerCache); // Keep previous cache to prevent data loss

            result.forEach((item) => {
                if (item.sellerDetails && typeof item.sellerDetails === "object") {
                    tempSellerCache.set(item.sellerDetails.sellerId, item.sellerDetails);
                }
            });

            setSellerCache(new Map(tempSellerCache)); // Ensure React recognizes state update

            // Attach correct seller details to each record
            const updatedDetails = result.map((item) => {
                let sellerDetails = item.sellerDetails;

                if (typeof sellerDetails === "number") {
                    // If sellerDetails is an ID, fetch from cache
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

            setDetails(updatedDetails);
        } catch (error) {
            console.error("Error fetching purchase details:", error);
            toast.error("Something went wrong!");
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
                <h1 style={{ marginTop: 10, marginBottom: 25, color: "darkblue" }}>Purchase Details</h1>
            </div>

            <table className="table table-hover">
                <thead>
                    <tr className="table-success">
                        <th>Order No</th>
                        <th>Product Name</th>
                        <th>Quantity</th>
                        <th>Total Amount (Rs.)</th>
                        <th>Seller Name</th>
                        <th>Payment Mode</th>
                        <th>Status</th>
                        <th>Transaction ID</th>
                        <th>Date/Time</th>
                    </tr>
                </thead>
                <tbody>
                    {details.map((detail, index) => (
                        <tr key={index}>
                            <th>{index + 1}</th>
                            <td>{detail.productDetails?.name || "N/A"}</td>
                            <td>{detail.quantity}</td>
                            <td>{detail.totalPrice}</td>
                            <td>{`${detail.sellerDetails?.firstName || "Unknown"} ${detail.sellerDetails?.lastName || ""}`}</td>
                            <td>{detail.paymentMode}</td>
                            <th>{detail.status}</th>
                            <td>{detail.transactionId}</td>
                            <td>{detail.dateTime}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default PurchaseDetails;





// // import axios from "axios";
// // import { useEffect, useState } from "react";
// // import { useLocation, useNavigate } from "react-router-dom";
// // import { toast } from "react-toastify";


// // const PurchaseDetails = () => {
// //     const location = useLocation();
// //     const [details, setDetails] = useState([]);
// //     const navigate = useNavigate();

// //     useEffect(() => {
// //         const { purchaseData } = location.state;
// //         setDetails(purchaseData);
// //     }, [location.state]);

// //     return (
// //         <div className="container-md">

// //             <div>
// //                 <h1 style={{ marginTop: 20, marginBottom: 25, color: "darkblue" }}>Purchase Details</h1>
// //             </div>


// //             <table className="table table-hover">
// //                 <thead>
// //                     <tr className="table-success">
// //                         <th>Order No</th>
// //                         <th>Product Name</th>
// //                         <th>Quantity</th>
// //                         <th>Total Amount (Rs.)</th>
// //                         <th>Seller Name</th>
// //                         <th>Payment Mode</th>
// //                         <th>Status</th>
// //                         <th>Transaction ID</th>
// //                         <th>Date/Time</th>
// //                     </tr>
// //                 </thead>
// //                 <tbody>
// //                     {details.map((details, index) => {
// //                         return (
// //                             <>
// //                                 <tr key={index}>
// //                                     <th>{index + 1}</th>
// //                                     <td>{details.productDetails.name}</td>
// //                                     <td>{details.quantity}</td>
// //                                     <td>{details.totalPrice}</td>
// //                                     <td>{details.sellerDetails.firstName} {details.sellerDetails.lastName}</td>
// //                                     <td>{details.paymentMode}</td>
// //                                     <th>{details.status}</th>
// //                                     <td>{details.transactionId}</td>
// //                                     <td>{details.dateTime}</td>
// //                                 </tr>
// //                             </>
// //                         )
// //                     })}
// //                 </tbody>
// //             </table>
// //         </div>
// //     )
// // }


// export default PurchaseDetails;

