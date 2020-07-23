import React, { useState } from "react";
import logo from "../../assets/images/logo.svg";
import logoMini from "../../assets/images/logo-mini.svg";
import Link from "react-router-dom/Link";
import { useLocation } from "react-router-dom";
import "./Sidebar.css";
import UserMenu from "./UserMenu";
const Sidebar = () => {
    let location = useLocation();
    // const [menuState, setMenuState] = useState(false)
    const [basicUiMenuOpenState, setBasicUiMenuOpenState] = useState(false);
    const [userPagesMenuOpen, setUserPagesMenuOpen] = useState(false);

    const isPathActive = (path) => {
        return location && location.pathname.startsWith(path);
    };

    //   componentDidMount() {
    //     onRouteChanged();
    //     // add className 'hover-open' to sidebar navitem while hover in sidebar-icon-only menu
    //     const body = document.querySelector('body');
    //     document.querySelectorAll('.sidebar .nav-item').forEach((el) => {

    //       el.addEventListener('mouseover', function() {
    //         if(body.classList.contains('sidebar-icon-only')) {
    //           el.classList.add('hover-open');
    //         }
    //       });
    //       el.addEventListener('mouseout', function() {
    //         if(body.classList.contains('sidebar-icon-only')) {
    //           el.classList.remove('hover-open');
    //         }
    //       });
    //     });
    //   }

    const toggleMenuState = (menuState) => {
        //   if (state[menuState]) {
        //     setState({[menuState] : false});
        //   } else if(Object.keys(state).length === 0) {
        //     setState({[menuState] : true});
        //   } else {
        //     Object.keys(state).forEach(i => {
        //       setState({[i]: false});
        //     });
        //     setState({[menuState] : true});
        //   }
    };

    // componentDidUpdate(prevProps) {
    //   if (props.location !== prevProps.location) {
    //     onRouteChanged();
    //   }
    // }

    const onRouteChanged = () => {
        //   document.querySelector('#sidebar').classList.remove('active');
        //   Object.keys(state).forEach(i => {
        //     setState({[i]: false});
        //   });
        //   const dropdownPaths = [
        //     {path:'/basic-ui', state: 'basicUiMenuOpen'},
        //     {path:'/form-elements', state: 'formElementsMenuOpen'},
        //     {path:'/tables', state: 'tablesMenuOpen'},
        //     {path:'/icons', state: 'iconsMenuOpen'},
        //     {path:'/charts', state: 'chartsMenuOpen'},
        //     {path:'/user-pages', state: 'userPagesMenuOpen'},
        //   ];
        //   dropdownPaths.forEach((obj => {
        //     if (isPathActive(obj.path)) {
        //       setState({[obj.state] : true})
        //     }
        //   }));
    };

    return (
        <nav className="sidebar sidebar-offcanvas" id="sidebar">
            <div className="text-center sidebar-brand-wrapper d-flex align-items-center">
                <a className="sidebar-brand brand-logo" href="index.html">
                    <img src={logo} alt="logo" />
                </a>
                <a className="sidebar-brand brand-logo-mini pt-3" href="index.html">
                    <img src={logoMini} alt="logo" />
                </a>
            </div>
            <ul className="nav">
                <li className="nav-item nav-profile not-navigation-link">
                    <UserMenu />
                </li>
                <li className={isPathActive("/dashboard") ? "nav-item active" : "nav-item"}>
                    <Link className="nav-link" to="/dashboard">
                        <i className="mdi mdi-television menu-icon"></i>
                        <span className="menu-title">Dashboard</span>
                    </Link>
                </li>
                <li className={isPathActive("/owners") ? "nav-item active" : "nav-item"}>
                    <Link className="nav-link" to="/owners">
                        <i className="mdi mdi-television menu-icon"></i>
                        <span className="menu-title">Owners</span>
                    </Link>
                </li>
                <li className={isPathActive("/pets") ? "nav-item active" : "nav-item"}>
                    <Link className="nav-link" to="/pets">
                        <i className="mdi mdi-format-list-bulleted menu-icon"></i>
                        <span className="menu-title">Pets</span>
                    </Link>
                </li>
                <li className={isPathActive("/vets") ? "nav-item active" : "nav-item"}>
                    <Link className="nav-link" to="/vets">
                        <i className="mdi mdi-table-large menu-icon"></i>
                        <span className="menu-title">Vets</span>
                    </Link>
                </li>
                <li className={isPathActive("/visits") ? "nav-item active" : "nav-item"}>
                    <Link className="nav-link" to="/visits">
                        <i className="mdi mdi-account-box-outline menu-icon"></i>
                        <span className="menu-title">Visits</span>
                    </Link>
                </li>
            </ul>
        </nav>
    );
};

export default Sidebar;
