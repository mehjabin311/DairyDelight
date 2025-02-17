import axios from "axios"
import { useEffect, useState } from "react"
import { toast } from 'react-toastify';
import { useNavigate } from 'react-router-dom'; 




const ConsumerQueryAdmin = () => {

    const [queryData, setQueryData] = useState([]);
    const navigate = useNavigate();
    useEffect(() => {
        getAllConsumerQuery();
    }, []);

    const getAllConsumerQuery = () => {

        axios.get('http://localhost:9090/admin/consumer/query').then((response) => {
            const result = response.data;
            setQueryData(result);
        }).catch((error) => {
            toast.error('Failed to fecth Consumer Queries right now !');
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
                <h1 style={{ marginTop: 20, marginBottom: 25, color: "darkorange" }}>Query Submitted By Consumer's</h1>
            </div>

            <table className="table table-hover">
                <thead className="table-secondary">
                    <tr>
                        <th>ID</th>
                        <th>Query Message</th>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Phone</th>
                        <th>Date / Time</th>
                    </tr>
                </thead>
                <tbody>
                    {queryData.map((list) => {
                        return (
                            <>
                                <tr>
                                    <th>{list.qid}</th>
                                    <td style={{ width: 600 }}>{list.message}</td>
                                    <td>{list.consumerDetails.firstName} {list.consumerDetails.lastName}</td>
                                    <td>{list.consumerDetails.emailId}</td>
                                    <td>{list.consumerDetails.phoneNumber}</td>
                                    <td>{list.dateTime}</td>
                                </tr>
                            </>
                        )
                    })}
                </tbody>
            </table>
            
        </div>


    )
}


export default ConsumerQueryAdmin;