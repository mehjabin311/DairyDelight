import { Link } from "react-router-dom";


const NavBar = () => {

    return (
        <div>
            <nav className="navbar navbar-expand-lg bg-light">
                <div className="container-md">
                    <Link className="navbar-brand" to='/'><strong>DairyDelight</strong></Link>
                    <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                        <span className="navbar-toggler-icon"></span>
                    </button>
                    <div className="collapse navbar-collapse" id="navbarNav">
                        <ul className="navbar-nav">
                            <li className="nav-item">
                                <Link className="nav-link active" aria-current="page" to='/'><button type="button" class="btn btn-light">Home</button></Link>
                            </li>
                            <li className="nav-item">
                                <Link className="nav-link" to='/purchaseProduct'><button type="button" class="btn btn-light">Purchase</button></Link>
                            </li>
                            <li className="nav-item">
                                <Link className="nav-link" to='/consumerSelection'><button type="button" class="btn btn-light">Consumer</button></Link>
                            </li>
                            <li className="nav-item">
                                <Link className="nav-link" to='/sellerSelection'><button type="button" class="btn btn-light">Seller</button></Link>
                            </li>
                            <li className="nav-item">
                                <Link className="nav-link" to='/adminLogin'><button type="button" class="btn btn-light">Administrator</button></Link>
                            </li>
                            <li className="nav-item">
                                <Link className="nav-link" to='/commonLogin'><button type="button" class="btn btn-light" style={{ marginLeft: 530 }}>Login</button></Link>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
        </div >
    )


}




export default NavBar;