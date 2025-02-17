import axios from "axios";
import { useEffect, useState } from 'react';
import { toast } from "react-toastify";
import { useLocation, useNavigate } from 'react-router-dom';


const UpdateConsumerDetails = () => {

    const [conDetails, setConDetails] = useState({});
    const [conAddress, setConAddress] = useState({});
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [phoneNumber, setPhoneNumber] = useState('');
    const [street, setStreet] = useState('');
    const [district, setDistrict] = useState('');
    const [town, setTown] = useState('');
    const [pincode, setPincode] = useState('');
    const [country, setCountry] = useState('India');
    const [state, setState] = useState('');
    const navigate = useNavigate();

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
            setConAddress(result.address);
            setConDetails(result);
        }).catch((error) => {
            console.log(error.message);
        });

    }

    const updateDetails = () => {

        if (firstName == '') {
            toast.warning("Enter First Name");
        } else if (phoneNumber == '') {
            toast.warning("Enter Phone Number");
        } else if (district == '') {
            toast.warning("Enter District");
        } else if (town == '') {
            toast.warning("Enter Town");
        } else if (pincode == '') {
            toast.warning("Enter Pincode");
        } else if (state == '') {
            toast.warning("Enter State");
        } else {

            axios.put('http://localhost:9090/consumer/updatedetails', {
                firstName, lastName, gender: conDetails.gender, emailId: conDetails.emailId, password: conDetails.password, street, username: conDetails.username, phoneNumber, address: {
                    district, town, pincode, state, country
                }
            }).then((response) => {
                const result = response.data;
                toast.success('Profile Information Updated');
                navigate("/consumerHome");
            }).catch((error) => {
                toast.error('Error while updating profile information ! Try again later');
            });
        }
    }

    return (

        <div className="container-md">

            <div>
                <h1 style={{ marginTop: 30, marginBottom: 35, color: "darkblue" }}>Update Profile Information</h1>
            </div>

            <div className="container-md w-50">

                <div className="row row-cols-2 text-start">
                    <div className="col-md-6">
                        <label htmlFor="inputfirstname" class="form-label mb-1">First Name</label>
                        <input type="text" className="form-control" placeholder="Enter First Name" defaultValue={conDetails.firstName} onMouseMove={(event) => setFirstName(event.target.value)} />
                    </div>
                    <div className="col-md-6">
                        <label htmlFor="inputlastname" class="form-label mb-1">Last Name</label>
                        <input type="text" className="form-control" placeholder="Enter Last Name" defaultValue={conDetails.lastName} onMouseMove={(event) => setLastName(event.target.value)} />
                    </div>
                </div>

                <div className="row row-cols-2 mt-3 text-start">
                    <div className="col-md-6">
                        <label htmlFor="inputphonenumber" class="form-label mb-1">Phone Number</label>
                        <input type="text" className="form-control" placeholder="Enter Phone number" defaultValue={conDetails.phoneNumber} onMouseMove={(event) => setPhoneNumber(event.target.value)} />
                    </div>
                    <div className="col-md-6">
                        <label htmlFor="inputstreet" class="form-label mb-1">Street</label>
                        <input type="text" className="form-control" placeholder="Enter street/locality" defaultValue={conDetails.street} onMouseMove={(event) => setStreet(event.target.value)} />
                    </div>
                </div>
                <div className="row row-cols-2 mt-3 text-start">
                    <div className="col-md-6">
                        <label htmlFor="inputDistrict" class="form-label mb-1">District</label>
                        <input type="text" className="form-control" placeholder="Enter District" defaultValue={conAddress.district} onMouseMove={(event) => setDistrict(event.target.value)} />
                    </div>
                    <div className="col-md-6">
                        <label htmlFor="inputTown" class="form-label mb-1">Town</label>
                        <input type="text" className="form-control" placeholder="Enter Town" defaultValue={conAddress.town} onMouseMove={(event) => setTown(event.target.value)} />
                    </div>
                </div>
                <div className="row row-cols-2 mt-3 text-start">
                    <div className="col-md-6">
                        <label htmlFor="inputPincode" class="form-label mb-1">Pincode</label>
                        <input type="text" className="form-control" placeholder="Enter Pincode" defaultValue={conAddress.pincode} onMouseMove={(event) => setPincode(event.target.value)} />
                    </div>
                    <div class="col-md-6">
                        <label htmlFor="inputState" class="form-label mb-1">State</label>
                        <select id="inputState" class="form-select" defaultValue={conAddress.state} onMouseMove={(event) => setState(event.target.value)}>
                            <option disabled selected >Choose State</option>
                            <option value="Maharashtra">Maharashtra</option>
                            <option value="Delhi">Delhi</option>
                            <option value="Goa">Goa</option>
                            <option value="Haryana">Haryana</option>
                            <option value="Uttar Pradesh">Uttar Pradesh</option>
                            <option value="Madhya Pradesh">Madhya Pradesh</option>
                            <option value="Odisha">Odisha</option>
                            <option value="Telangana">Telangana</option>
                            <option value="Rajasthan">Rajasthan</option>
                        </select>
                    </div>

                    <div className="col-md-12">
                        <label htmlFor="validationCustom04" className="form-label row g-0">Country*</label>
                        <select className="form-select" id="validationCustom04" required disabled >
                            <option value="India">India</option>
                        </select>
                        <div className="invalid-feedback">
                            Please select your country.
                        </div>
                    </div>

                </div>
                <div className="d-flex justify-content-center gap-3 mt-4">
                    <button className="btn btn-outline-success btn-md px-3 py-2" onClick={() => navigate(-1)}>
                        ← Back
                    </button>
                    <button type="submit" className="btn btn-outline-primary px-4 py-2" onClick={updateDetails}>
                        Update Details
                    </button>
                </div>
              
            </div >
        </div >

    );

}

export default UpdateConsumerDetails







