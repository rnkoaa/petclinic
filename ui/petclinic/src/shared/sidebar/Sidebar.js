import React, { useState } from "react";
import logo from "../../assets/images/logo.svg"
import logoMini from "../../assets/images/logo-mini.svg"
import face8 from "../../assets/images/faces/face8.jpg"
import  Dropdown  from "react-bootstrap/Dropdown";
import Link from 'react-router-dom/Link'
import Collapse from 'react-collapse'
import './Sidebar.css'
function Sidebar() {
    // state = {};
    // const [menuState, setMenuState] = useState(false)
    const [basicUiMenuOpenState, setBasicUiMenuOpenState] = useState(false)
    const [userPagesMenuOpen, setUserPagesMenuOpen ] = useState(false)


    const isPathActive = (path) => {
        // return props.location.pathname.startsWith(path);
        return true
      }
    
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

    return ( <nav className="sidebar sidebar-offcanvas" id="sidebar">
    <div className="text-center sidebar-brand-wrapper d-flex align-items-center">
      <a className="sidebar-brand brand-logo" href="index.html"><img src={logo} alt="logo" /></a>
      <a className="sidebar-brand brand-logo-mini pt-3" href="index.html"><img src={logoMini} alt="logo" /></a>
    </div>
    <ul className="nav">
      <li className="nav-item nav-profile not-navigation-link">
        <div className="nav-link">
          <Dropdown>
            <Dropdown.Toggle className="nav-link user-switch-dropdown-toggler p-0 toggle-arrow-hide bg-transparent border-0 w-100">
              <div className="d-flex justify-content-between align-items-start">
                <div className="profile-image">
                  <img src={face8} alt="profile" />
                </div>
                <div className="text-left ml-3">
                  <p className="profile-name">Richard V.Welsh</p>
                  <small className="designation text-muted text-small">Manager</small>
                  <span className="status-indicator online"></span>
                </div>
              </div>
            </Dropdown.Toggle>
            <Dropdown.Menu className="preview-list navbar-dropdown">
              <Dropdown.Item className="dropdown-item p-0 preview-item d-flex align-items-center" href="!#" onClick={evt =>evt.preventDefault()}>
                <div className="d-flex">
                  <div className="py-3 px-4 d-flex align-items-center justify-content-center">
                    <i className="mdi mdi-bookmark-plus-outline mr-0"></i>
                  </div>
                  <div className="py-3 px-4 d-flex align-items-center justify-content-center border-left border-right">
                    <i className="mdi mdi-account-outline mr-0"></i>
                  </div>
                  <div className="py-3 px-4 d-flex align-items-center justify-content-center">
                    <i className="mdi mdi-alarm-check mr-0"></i>
                  </div>
                </div>
              </Dropdown.Item>
              <Dropdown.Item className="dropdown-item preview-item d-flex align-items-center text-small" onClick={evt =>evt.preventDefault()}>
                Manage Accounts
              </Dropdown.Item>
              <Dropdown.Item className="dropdown-item preview-item d-flex align-items-center text-small" onClick={evt =>evt.preventDefault()}>
              Change Password 
              </Dropdown.Item>
              <Dropdown.Item className="dropdown-item preview-item d-flex align-items-center text-small" onClick={evt =>evt.preventDefault()}>
                Check Inbox
              </Dropdown.Item>
              <Dropdown.Item className="dropdown-item preview-item d-flex align-items-center text-small" onClick={evt =>evt.preventDefault()}>
                Sign Out
              </Dropdown.Item>
            </Dropdown.Menu>
          </Dropdown>
          <button className="btn btn-success btn-block">New Project <i className="mdi mdi-plus"></i></button>
        </div>
      </li>
      <li className={ isPathActive('/dashboard') ? 'nav-item active' : 'nav-item' }>
        <Link className="nav-link" to="/dashboard">
          <i className="mdi mdi-television menu-icon"></i>
          <span className="menu-title">Dashboard</span>
        </Link>
      </li>
      <li className={ isPathActive('/basic-ui') ? 'nav-item active' : 'nav-item' }>
        <div className={ basicUiMenuOpenState? 'nav-link menu-expanded' : 'nav-link' } onClick={ () => toggleMenuState('basicUiMenuOpen') } data-toggle="collapse">
          <i className="mdi mdi-crosshairs-gps menu-icon"></i>
          <span className="menu-title">Basic UI Elements</span>
          <i className="menu-arrow"></i>
        </div>
        <Collapse in={ basicUiMenuOpenState }>
          <ul className="nav flex-column sub-menu">
            <li className="nav-item"> <Link className={ isPathActive('/basic-ui/buttons') ? 'nav-link active' : 'nav-link' } to="/basic-ui/buttons">Buttons</Link></li>
            <li className="nav-item"> <Link className={ isPathActive('/basic-ui/dropdowns') ? 'nav-link active' : 'nav-link' } to="/basic-ui/dropdowns">Dropdowns</Link></li>
            <li className="nav-item"> <Link className={ isPathActive('/basic-ui/typography') ? 'nav-link active' : 'nav-link' } to="/basic-ui/typography">Typography</Link></li>
          </ul>
        </Collapse>
      </li>
      <li className={ isPathActive('/form-elements') ? 'nav-item active' : 'nav-item' }>
        <Link className="nav-link" to="/form-elements/basic-elements">
          <i className="mdi mdi-format-list-bulleted menu-icon"></i>
          <span className="menu-title">Form Elements</span>
        </Link>
      </li>
      <li className={ isPathActive('/tables') ? 'nav-item active' : 'nav-item' }>
        <Link className="nav-link" to="/tables/basic-table">
          <i className="mdi mdi-table-large menu-icon"></i>
          <span className="menu-title">Tables</span>
        </Link>
      </li>
      <li className={ isPathActive('/icons') ? 'nav-item active' : 'nav-item' }>
        <Link className="nav-link" to="/icons/font-awesome">
          <i className="mdi mdi-account-box-outline menu-icon"></i>
          <span className="menu-title">Icons</span>
        </Link>
      </li>
      <li className={ isPathActive('/charts') ? 'nav-item active' : 'nav-item' }>
        <Link className="nav-link" to="/charts/chart-js">
          <i className="mdi mdi-chart-line menu-icon"></i>
          <span className="menu-title">Charts</span>
        </Link>
      </li>
      <li className={ isPathActive('/user-pages') ? 'nav-item active' : 'nav-item' }>
        <div className={ userPagesMenuOpen ? 'nav-link menu-expanded' : 'nav-link' } onClick={ () => toggleMenuState('userPagesMenuOpen') } data-toggle="collapse">
          <i className="mdi mdi-lock-outline menu-icon"></i>
          <span className="menu-title">User Pages</span>
          <i className="menu-arrow"></i>
        </div>
        <Collapse in={ userPagesMenuOpen }>
          <ul className="nav flex-column sub-menu">
            <li className="nav-item"> <Link className={ isPathActive('/user-pages/blank-page') ? 'nav-link active' : 'nav-link' } to="/user-pages/blank-page">Blank Page</Link></li>
            <li className="nav-item"> <Link className={ isPathActive('/user-pages/login-1') ? 'nav-link active' : 'nav-link' } to="/user-pages/login-1">Login</Link></li>
            <li className="nav-item"> <Link className={ isPathActive('/user-pages/register-1') ? 'nav-link active' : 'nav-link' } to="/user-pages/register-1">Register</Link></li>
            <li className="nav-item"> <Link className={ isPathActive('/user-pages/error-404') ? 'nav-link active' : 'nav-link' } to="/user-pages/error-404">404</Link></li>
            <li className="nav-item"> <Link className={ isPathActive('/user-pages/error-500') ? 'nav-link active' : 'nav-link' } to="/user-pages/error-500">500</Link></li>
          </ul>
        </Collapse>
      </li>
      <li className="nav-item">
        <a className="nav-link" href="http://www.bootstrapdash.com/demo/star-admin-pro-react/documentation/documentation.html" rel="noopener noreferrer" target="_blank">
          <i className="mdi mdi-file-outline menu-icon"></i>
          <span className="menu-title">Documentation</span>
        </a>
      </li>
    </ul>
  </nav>);
}

export default Sidebar;
