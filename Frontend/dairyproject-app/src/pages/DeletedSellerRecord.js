import axios from "axios";
import { useEffect, useState } from "react";
import { toast } from 'react-toastify';
import { useNavigate } from 'react-router-dom';

const DeletedSellerRecord = () => {

    const [deletedSellerRecord, setDeletedSellerRecord] = useState([]);
    const navigate = useNavigate();
    useEffect(() => {
        fetchDeletedSellerRecord();
    }, []);

    const fetchDeletedSellerRecord = () => {
        axios.get('http://localhost:9090/admin/getdeletedsellers').then((response) => {
            const result = response.data;
            setDeletedSellerRecord(result);
            console.log(result);
        }).catch((error) => {
            toast.error('Error while fetching deleted consumer records');
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
                <h1 style={{ marginTop: 20, marginBottom: 25, color: "blue" }}>Deleted Sellers Detail</h1>
            </div>
            <div className="container-md">
                <table className="table table-hover">
                    <thead>
                        <tr className="table-secondary">
                            <th>ID</th>
                            <th>Name</th>
                            <th>Gender</th>
                            <th>Username</th>
                            <th>Contact</th>
                        </tr>
                    </thead>
                    <tbody>
                        {deletedSellerRecord.map((list) => {
                            return (
                                <>
                                    <tr>
                                        <th>{list.sellerId}</th>
                                        <td>{list.firstName} {list.lastName}</td>
                                        <td>{list.gender}</td>
                                        <td>{list.username}</td>
                                        <td>{list.phoneNumber}</td>
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


export default DeletedSellerRecord;