import React, { useEffect } from "react";
// import Dashboard from "../../components/Dashboard";

// import { selectedOwnerIDState, selectedOwnerState } from "../../store";
// import { useRecoilState, useRecoilValue } from "recoil";

import "./OwnerProfileDetail.css";

import ProgressBar from "react-bootstrap/ProgressBar";
import face1 from "../../assets/images/faces/face1.jpg";
import face2 from "../../assets/images/faces/face2.jpg";
import face3 from "../../assets/images/faces/face3.jpg";
import face4 from "../../assets/images/faces/face4.jpg";
import face5 from "../../assets/images/faces/face5.jpg";
import face6 from "../../assets/images/faces/face6.jpg";
import face7 from "../../assets/images/faces/face7.jpg";

import profileImage from "../../assets/images/faces/profile/profile.jpg";
import profileLogo1 from "../../assets/images/samples/profile_page/logo/01.png";
import profileLogo2 from "../../assets/images/samples/profile_page/logo/02.png";

const OwnerDetail = ({ match }) => {
    const { id } = match.params;
    // const [selectedOwnerId, setSelectedOwnerId] = useRecoilState(selectedOwnerIDState)

    // useEffect(() => {
    //     // Run! Like go get some data from an API.
    //     setSelectedOwnerId(id)
    //   }, [id]);
    // const selectedOwner = useRecoilValue(selectedOwnerState)
    // console.log(selectedOwner)
    return (
        <div class="row profile-page">
            <div class="col-12">
                <div class="card">
                    <div class="card-body">
                        <OwnerProfileHeader />
                        <OwnerProfileBody />
                        <div className="row">
                            <div class="col-12 justify-content-end d-flex">
                                <button class="btn btn-outline-primary">Add New Pet</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};
export default OwnerDetail;

const OwnerProfileBody = () => {
    return (
        <div class="profile-body">
            <OwnerProfileBodyTabs />
            <div class="row">
                <OwnerNameHeader />
                <OwnerTableDashboard />
            </div>
        </div>
    );
};

const OwnerNameHeader = () => {
    return (
        <div class="col-md-12">
            <div class="tab-pane fade pr-3 active show" id="user-profile-info" role="tabpanel" aria-labelledby="user-profile-info-tab">
                <div class="table-responsive">
                    <table class="table table-borderless w-100 mt-4">
                        <tbody>
                            <tr>
                                <td>
                                    <strong>Full Name :</strong> Johnathan Deo
                                </td>
                                <td>
                                    <strong>Website :</strong> staradmin.com
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <strong>Location :</strong> USA
                                </td>
                                <td>
                                    <strong>Email :</strong> Richard@staradmin.com
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <strong>Languages :</strong> English, German, Spanish.
                                </td>
                                <td>
                                    <strong>Phone :</strong> +73646 4563
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    );
};

const OwnerProfileBodyTabs = () => {
    return (
        <ul class="nav tab-switch" role="tablist">
            <li class="nav-item">
                <a
                    class="nav-link active"
                    id="user-profile-info-tab"
                    data-toggle="pill"
                    href="#user-profile-info"
                    role="tab"
                    aria-controls="user-profile-info"
                    aria-selected="true"
                >
                    Profile
                </a>
            </li>
            <li class="nav-item">
                <a
                    class="nav-link"
                    id="user-profile-activity-tab"
                    data-toggle="pill"
                    href="#user-profile-activity"
                    role="tab"
                    aria-controls="user-profile-activity"
                    aria-selected="false"
                >
                    Activity
                </a>
            </li>
        </ul>
    );
};

const OwnerProfileHeader = () => {
    return (
        <div class="profile-header text-white">
            <div class="d-flex justify-content-center justify-content-md-between mx-4 mx-xl-5 px-xl-5 flex-wrap">
                <div class="profile-info d-flex align-items-center justify-content-center flex-wrap mr-sm-3">
                    <img class="rounded-circle img-lg mb-3 mb-sm-0" src={profileImage} alt="profile image" />
                    <div class="wrapper pl-sm-4">
                        <p class="profile-user-name text-center text-sm-left">Richard V.Welsh (UI/UX Designer)</p>
                        <div class="wrapper d-flex align-items-center justify-content-center flex-wrap">
                            <p class="profile-user-designation text-center text-md-left my-2 my-md-0">User Experience Specialist</p>
                            <div class="br-wrapper br-theme-css-stars">
                                <select id="example-css" name="rating" autocomplete="off" displayNone>
                                    <option value="1">1</option>
                                    <option value="2">2</option>
                                    <option value="3">3</option>
                                    <option value="4">4</option>
                                    <option value="5">5</option>
                                </select>
                                <div class="br-widget">
                                    <a href="#" data-rating-value="1" data-rating-text="1" class="br-selected br-current"></a>
                                    <a href="#" data-rating-value="2" data-rating-text="2"></a>
                                    <a href="#" data-rating-value="3" data-rating-text="3"></a>
                                    <a href="#" data-rating-value="4" data-rating-text="4"></a>
                                    <a href="#" data-rating-value="5" data-rating-text="5"></a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="details mt-2 mt-md-0">
                    <div class="detail-col pr-3 mr-3">
                        <p>Projects</p>
                        <p>130</p>
                    </div>
                    <div class="detail-col">
                        <p>Projects</p>
                        <p>130</p>
                    </div>
                </div>
            </div>
        </div>
    );
};

const OwnerTableDashboard = () => {
    const id = 1;
    return (
        <div className="col-md-12 mt-5 pb-4">
            <h2 class="mb-3 text-underline">Pets</h2>
            <div className="table-responsive">
                <table className="table table-striped table-bordered">
                    <thead>
                        <tr>
                            <th> User </th>
                            <th> First name </th>
                            <th> Progress </th>
                            <th> Amount </th>
                            <th> Deadline </th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td className="py-1">
                                <img src={face1} alt="user icon" />
                            </td>
                            <td> Herman Beck </td>
                            <td>
                                <ProgressBar variant="success" now={25} />
                            </td>
                            <td> $ 77.99 </td>
                            <td> May 15, 2015 </td>
                        </tr>
                        <tr>
                            <td className="py-1">
                                <img src={face2} alt="user icon" />
                            </td>
                            <td> Messsy Adam </td>
                            <td>
                                <ProgressBar variant="danger" now={75} />
                            </td>
                            <td> $245.30 </td>
                            <td> July 1, 2015 </td>
                        </tr>
                        <tr>
                            <td className="py-1">
                                <img src={face3} alt="user icon" />
                            </td>
                            <td> John Richards </td>
                            <td>
                                <ProgressBar variant="warning" now={90} />
                            </td>
                            <td> $138.00 </td>
                            <td> Apr 12, 2015 </td>
                        </tr>
                        <tr>
                            <td className="py-1">
                                <img src={face4} alt="user icon" />
                            </td>
                            <td> Peter Meggik </td>
                            <td>
                                <ProgressBar variant="primary" now={50} />
                            </td>
                            <td> $ 77.99 </td>
                            <td> May 15, 2015 </td>
                        </tr>
                        <tr>
                            <td className="py-1">
                                <img src={face5} alt="user icon" />
                            </td>
                            <td> Edward </td>
                            <td>
                                <ProgressBar variant="danger" now={60} />
                            </td>
                            <td> $ 160.25 </td>
                            <td> May 03, 2015 </td>
                        </tr>
                        <tr>
                            <td className="py-1">
                                <img src={face6} alt="user icon" />
                            </td>
                            <td> John Doe </td>
                            <td>
                                <ProgressBar variant="info" now={65} />
                            </td>
                            <td> $ 123.21 </td>
                            <td> April 05, 2015 </td>
                        </tr>
                        <tr>
                            <td className="py-1">
                                <img src={face7} alt="user icon" />
                            </td>
                            <td> Henry Tom </td>
                            <td>
                                <ProgressBar variant="warning" now={20} />
                            </td>
                            <td> $ 150.00 </td>
                            <td> June 16, 2015 </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    );
};
