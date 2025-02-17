import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import axios from "axios";

const CommonLogin = () => {
    const [role, setRole] = useState("Seller");
    const [emailId, setEmailId] = useState("");
    const [password, setPassword] = useState("");
    const navigate = useNavigate();

    const handleLogin = async () => {
        if (!emailId || !password) {
            toast.error("Please enter email and password");
            return;
        }
        
        try {
            const response = await axios.post(`http://localhost:9090/${role.toLowerCase()}/fetchdetailsbyemail`, { emailId, password });
            toast.success(`Welcome ${role}! Login Successful`);
            sessionStorage.setItem(`${role} Email`, emailId);  // Store email by role name
            navigate(`/${role.toLowerCase()}Home`);
        } catch (error) {
            toast.error(error.response?.data?.message || "Login Failed");
        }
    };

    return (
        <div style={styles.container}>
            <div style={styles.card}>
                <h2 style={styles.title}>Login</h2>
                
                <div style={styles.inputGroup}>
                    <label style={styles.label}>Select Role</label>
                    <select 
                        style={styles.input} 
                        value={role} 
                        onChange={(e) => setRole(e.target.value)}
                    >
                        <option value="Seller">Seller</option>
                        <option value="Consumer">Consumer</option>
                        <option value="Admin">Admin</option>
                    </select>
                </div>

                <div style={styles.inputGroup}>
                    <label style={styles.label}>Email</label>
                    <input 
                        type="email" 
                        style={styles.input} 
                        placeholder="Enter your email" 
                        value={emailId} 
                        onChange={(e) => setEmailId(e.target.value)}
                    />
                </div>

                <div style={styles.inputGroup}>
                    <label style={styles.label}>Password</label>
                    <input 
                        type="password" 
                        style={styles.input} 
                        placeholder="Enter your password" 
                        value={password} 
                        onChange={(e) => setPassword(e.target.value)}
                    />
                </div>

                <button 
                    style={styles.button}
                    onClick={handleLogin}
                >
                    Login
                </button>
            </div>
        </div>
    );
};

const styles = {
    container: {
        display: "flex",
        justifyContent: "center",
        alignItems: "flex-start", // Move box towards the top
        height: "100vh",
        backgroundColor: "#f4f4f4",
        paddingTop: "10vh" // Moves the box higher up the screen
    },
    card: {
        width: "400px",
        padding: "25px",
        backgroundColor: "#fff",
        boxShadow: "0px 4px 15px rgba(0, 0, 0, 0.1)",
        borderRadius: "10px",
        textAlign: "center",
        border: "1px solid #ddd"
    },
    title: {
        fontSize: "24px",
        fontWeight: "bold",
        color: "#333",
        marginBottom: "20px"
    },
    inputGroup: {
        marginBottom: "15px",
        textAlign: "left"
    },
    label: {
        fontSize: "14px",
        fontWeight: "bold",
        color: "#555",
        marginBottom: "5px",
        display: "block"
    },
    input: {
        width: "100%",
        padding: "10px",
        borderRadius: "5px",
        border: "1px solid #ccc",
        fontSize: "16px"
    },
    button: {
        width: "100%",
        padding: "12px",
        backgroundColor: "#6dbf80", // Success green
        color: "white",
        fontSize: "16px",
        border: "none",
        borderRadius: "5px",
        cursor: "pointer",
        transition: "background 0.3s",
        marginTop: "10px"
    }
};

export default CommonLogin;
