import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";

const PurchaseProduct = () => {
    const navigate = useNavigate();
    const [productData, setProductData] = useState([]);
    const [productName, setProductName] = useState('');
    const [productPrice, setProductPrice] = useState('');
    const [quantity, setQuantity] = useState(1); // Set default quantity to 1
    const [paymentMode, setPaymentMode] = useState('');

    useEffect(() => {
        getAllProductList();
    }, []);

    const getAllProductList = () => {
        axios.get('http://localhost:9090/consumer/getallproducts')
            .then((response) => {
                setProductData(response.data);
            })
            .catch(() => {
                toast.error('Error while fetching product details');
            });
    };

    const ProductPrice = (proName) => {
        if (!proName) {
            setProductPrice('');
            return;
        }
        const selectedProduct = productData.find(product => product.name === proName);
        if (selectedProduct) {
            setProductPrice(selectedProduct.price);
        }
    };

    const handleQuantityChange = (event) => {
        let value = parseInt(event.target.value, 10);
        if (isNaN(value) || value < 1) {
            setQuantity(1);
        } else {
            setQuantity(value);
        }
    };

    const SearchSeller = () => {
        const emailId = sessionStorage.getItem('Consumer Email');
        if (!emailId) {
            toast.warning('Please login to continue your purchase!');
            navigate('/consumerLogin');
        } else if (!productName) {
            toast.warning('Select a product to buy');
        } else if (!quantity || quantity < 1) {
            toast.warning('Please enter a valid quantity (minimum 1)');
        } else if (!paymentMode) {
            toast.warning('Please select a payment mode');
        } else {
            navigate('/consumerSellerSelection', {
                state: {
                    ProductName: productName,
                    paymentMode: paymentMode,
                    quantity: quantity
                }
            });
        }
    };

    return (
        <div className="container-md">
            <div className="card mb-3" style={{ marginTop: 50, marginLeft: 245, width: 780 }}>
                <img src="./images/banner.jpg" className="card-img-top" alt="banner" style={{ height: 300 }} />
                <div className="card-body">
                    <h5 className="card-title">Purchase Details</h5>
                    <hr />
                    <div>
                        <div className="row g-3">
                            <div className="col-sm-4">
                                <select
                                    className="form-select"
                                    aria-label="Select Product"
                                    onChange={(event) => setProductName(event.target.value)}
                                    onBlur={() => ProductPrice(productName)}
                                >
                                    <option value="">Choose Product</option>
                                    {productData.map((product) => (
                                        <option key={product.id} value={product.name}>
                                            {product.name} ({product.unit})
                                        </option>
                                    ))}
                                </select>
                            </div>
                            <div className="col-sm-4">
                                <div className="input-group">
                                    <div className="input-group-text">Quantity</div>
                                    <input
                                        type="number"
                                        min={1}
                                        max={100}
                                        className="form-control"
                                        placeholder="Upto 100"
                                        value={quantity}
                                        onChange={handleQuantityChange}
                                    />
                                </div>
                            </div>
                            <div className="col-sm-4">
                                <select
                                    className="form-select"
                                    aria-label="Select Payment Mode"
                                    onChange={(event) => setPaymentMode(event.target.value)}
                                >
                                    <option value="">Select Payment Mode</option>
                                    <option value="UPI">UPI ID</option>
                                    <option value="Credit">Credit Card</option>
                                    <option value="Debit">Debit Card</option>
                                    <option value="COD">COD (Cash on Delivery)</option>
                                </select>
                            </div>
                            <div className="col-12" style={{ marginLeft: 260 }}>
                                <button className="btn btn-outline-primary" onClick={SearchSeller}>
                                    Search Nearby Seller
                                </button>
                            </div>
                            <div className="col-4" style={{ marginTop: -38, marginLeft: 0 }}>
                                <h3>Price - {productPrice * quantity}</h3>
                            </div>
                        </div>
                        <div>
                            <button className="btn btn-outline-success btn-md px-3 py-2 mb-3 mt-5" onClick={() => navigate(-1)}>
                                ← Back
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default PurchaseProduct;
// import axios from "axios";
// import { useEffect, useState } from "react";
// import { useNavigate } from "react-router-dom";
// import { toast } from "react-toastify";


// const PurchaseProduct = () => {
//     const navigate = useNavigate();
//     const [productData, setProductData] = useState([]);
//     const [productName, setProductName] = useState('');
//     const [productPrice, setProductPrice] = useState('');
//     const [quantity, setQuantity] = useState('');
//     const [paymentMode, setPaymentMode] = useState('');

//     useEffect(() => {
//         getAllProductList();
//     }, []);

//     const getAllProductList = () => {
//         axios.get('http://localhost:9090/consumer/getallproducts').then((response) => {
//             const result = response.data;
//             setProductData(result);
//             console.log(productData)
//         }).catch((error) => {
//             toast.error('Error while fetching product details');
//         });
//     }

//     const ProductPrice = (proName) => {
//         if (proName == "") {
//             setProductPrice('');
//         }
//         for (let i = 0; i < productData.length; i++) {
//             if (productData[i].name == proName) {
//                 setProductPrice(productData[i].price);
//             }
//         }
//     }

//     const SearchSeller = () => {
//         const emailId = sessionStorage.getItem('Consumer Email');
//         if (emailId == null) {
//             toast.warning('Please login to continue your purchase !');
//             navigate('/consumerLogin');
//         } else if (productName === '') {
//             toast.warning('Select Product to Buy');
//         } else if (quantity == '') {
//             toast.warning('Please Enter Quantity');
//         } else if (parseInt(quantity) < 1) {
//             toast.warning('Please Enter Correct Quantity');
//         } else if (paymentMode === '') {
//             toast.warning('Please Select Payment Mode');
//         } else {
//             console.log({ ProductName: productName, paymentMode: paymentMode, quantity: quantity });
//             navigate('/consumerSellerSelection', {
//                 state: {
//                     ProductName: productName,
//                     paymentMode: paymentMode,
//                     quantity: quantity
//                 }
//             });
//         }
//     }



//     return (

//         <div className="container-md">

//             <div className="card mb-3" style={{ marginTop: 50, marginLeft: 245, width: 780 }}>
//                 <img src="./images/banner.jpg" className="card-img-top" alt="banner" style={{ height: 300 }} />
//                 <div className="card-body">
//                     <h5 className="card-title">Purchase Details </h5><hr />
//                     <div>
//                         <div className="row g-3">
//                             <div className="col-sm-4">
//                                 <select className="form-select" aria-label="Default select example" onChange={(event) => setProductName(event.target.value)} onBlur={() => ProductPrice(productName)}>
//                                     <option selected Value="">Choose Product</option>
//                                     {productData.map((product) => {
//                                         return (
//                                             <>
//                                                 <option key={product.id} value={product.name} >{product.name} ({product.unit})</option>
//                                             </>
//                                         )
//                                     })}
//                                 </select>
//                             </div>
//                             <div className="col-sm-4">
//                                 <div className="input-group">
//                                     <div className="input-group-text">Quantity</div>
//                                     <input type="number"  min={1} max={100} className="form-control" placeholder="Upto 100" onChange={(event) => setQuantity(event.target.value)} />
//                                 </div>
//                             </div>
//                             <div className="col-sm-4">
//                                 <select className="form-select" aria-label="Default select example" onChange={(event) => setPaymentMode(event.target.value)}>
//                                     <option selected defaultValue="">Select Payment Mode</option>
//                                     <option value="UPI">UPI ID</option>
//                                     <option value="Credit">Credit Card</option>
//                                     <option value="Debit">Debit Card</option>
//                                     <option value="COD">COD (Cash on Delivery)</option>
//                                 </select>
//                             </div>
//                             <div className="col-12" style={{ marginLeft: 260 }}>
//                                 <button className="btn btn-outline-primary" onClick={SearchSeller}>Search Nearby Seller</button>
//                             </div>
//                             <div className="col-4" style={{ marginTop: -38, marginLeft: 0 }}>
//                                 <h3>Price - {productPrice * quantity}</h3>
//                             </div>

//                         </div>
//                         <div><button className="btn btn-outline-success btn-md px-3 py-2 mb-3 mt-5" onClick={() => navigate(-1)}>
//                             ← Back
//                         </button></div>
//                     </div>
//                 </div>
//             </div>


//         </div>
//     )
// }


// export default PurchaseProduct;