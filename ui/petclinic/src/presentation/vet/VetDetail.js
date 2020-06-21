import React from 'react'
import ProgressBar from "react-bootstrap/ProgressBar";
import face1 from "../../assets/images/faces/face1.jpg";
import face2 from "../../assets/images/faces/face2.jpg";
import face3 from "../../assets/images/faces/face3.jpg";
import face4 from "../../assets/images/faces/face4.jpg";
import face5 from "../../assets/images/faces/face5.jpg";
import face6 from "../../assets/images/faces/face6.jpg";
import face7 from "../../assets/images/faces/face7.jpg"
const VetDetail = ({match}) => {
   const {id} = match.params
   return(<div>
      <div className="page-header">
          <h3 className="page-title"> Visit details for {id} </h3>
          <nav aria-label="breadcrumb">
              <ol className="breadcrumb">
                  <li className="breadcrumb-item">
                      <a href="!#" onClick={(event) => event.preventDefault()}>
                          Visit
                      </a>
                  </li>
              </ol>
          </nav>
      </div>
      <div className="row">
          <div className="col-lg-12 grid-margin stretch-card">
              <div className="card">
                  <div className="card-body">
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
              </div>
          </div>
      </div>
  </div>) 
}

export default VetDetail
