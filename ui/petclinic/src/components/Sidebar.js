import React from "react";
import Nav from "react-bootstrap/Nav";
import "./Sidebar.css";
import { LinkContainer } from "react-router-bootstrap";
const Sidebar = () => {
  return (
    <Nav
      id="sidebarMenu"
      className="col-md-3 col-lg-2 d-md-block bg-light sidebar collapse"
    >
      <div className="sidebar-sticky pt-3">
        <ul className="nav flex-column">
          <li className="nav-item">
            <LinkContainer to={"/"}>
              <Nav.Link className="active">
                <span data-fetcher="home"></span>Dashboard
                <span className="sr-only">(current)</span>
              </Nav.Link>
            </LinkContainer>
          </li>
          <li className="nav-item">
            <LinkContainer to={"/owners"}>
              <Nav.Link>
                <span data-fetcher="file"></span>Owners
              </Nav.Link>
            </LinkContainer>
          </li>
          <li className="nav-item">
            <LinkContainer to="/pets">
              <Nav.Link>
                <span data-fetcher="shopping-cart"></span>Pets
              </Nav.Link>
            </LinkContainer>
          </li>
          <li className="nav-item">
            <LinkContainer to="/vets">
              <Nav.Link>
                <span data-fetcher="users"></span>Vets
              </Nav.Link>
            </LinkContainer>
          </li>
          <li className="nav-item">
            <LinkContainer to="/visits">
              <Nav.Link>
                <span data-fetcher="bar-chart-2"></span>Visits
              </Nav.Link>
            </LinkContainer>
          </li>
        </ul>
      </div>
    </Nav>
  );
};

export default Sidebar;
