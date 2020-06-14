import React from 'react'
import Nav from 'react-bootstrap/Nav'
import './Sidebar.css';
const Sidebar = () => {
   return (
       <Nav id="sidebarMenu" className="col-md-3 col-lg-2 d-md-block bg-light sidebar collapse">
           <div className="sidebar-sticky pt-3">
                <ul className="nav flex-column">
                    <li className="nav-item">
                        <Nav.Link className="active">
                            <span data-fetcher="home"></span>Dashboard
                            <span className="sr-only">(current)</span>
                        </Nav.Link>
                    </li>
                    <li className="nav-item">
                        <Nav.Link>
                            <span data-fetcher="file"></span>Owners
                        </Nav.Link>
                    </li>
                    <li className="nav-item">
                        <Nav.Link>
                            <span data-fetcher="shopping-cart"></span>Pets
                        </Nav.Link>
                    </li>
                    <li className="nav-item">
                        <Nav.Link>
                            <span data-fetcher="users"></span>Vets
                        </Nav.Link>
                    </li>
                    <li className="nav-item">
                        <Nav.Link>
                            <span data-fetcher="bar-chart-2"></span>Visits
                        </Nav.Link>
                    </li>
                    <li className="nav-item">
                        <Nav.Link>
                            <span data-fetcher="layers"></span>Integrations
                        </Nav.Link>
                    </li>
                </ul>
           </div>
       </Nav>
   ) 
}

export default Sidebar;