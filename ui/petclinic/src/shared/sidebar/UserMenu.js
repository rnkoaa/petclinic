import React from "react";
import face8 from "../../assets/images/faces/face8.jpg";
import Dropdown from "react-bootstrap/Dropdown";
const UserMenu = () => {
    return (
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
                    <Dropdown.Item
                        className="dropdown-item p-0 preview-item d-flex align-items-center"
                        href="!#"
                        onClick={(evt) => evt.preventDefault()}
                    >
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
                    <Dropdown.Item
                        className="dropdown-item preview-item d-flex align-items-center text-small"
                        onClick={(evt) => evt.preventDefault()}
                    >
                        Manage Accounts
                    </Dropdown.Item>
                    <Dropdown.Item
                        className="dropdown-item preview-item d-flex align-items-center text-small"
                        onClick={(evt) => evt.preventDefault()}
                    >
                        Change Password
                    </Dropdown.Item>
                    <Dropdown.Item
                        className="dropdown-item preview-item d-flex align-items-center text-small"
                        onClick={(evt) => evt.preventDefault()}
                    >
                        Check Inbox
                    </Dropdown.Item>
                    <Dropdown.Item
                        className="dropdown-item preview-item d-flex align-items-center text-small"
                        onClick={(evt) => evt.preventDefault()}
                    >
                        Sign Out
                    </Dropdown.Item>
                </Dropdown.Menu>
            </Dropdown>
            <button className="btn btn-success btn-block">
                New Project <i className="mdi mdi-plus"></i>
            </button>
        </div>
    );
};

export default UserMenu;
