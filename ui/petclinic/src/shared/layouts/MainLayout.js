import React from "react";
import Footer from "../footer/Footer";
import Navbar from "../navbar/Navbar";
import Sidebar from "../sidebar/Sidebar";
import Routes from "../routes/Routes";
const MainLayout = () => {
  return (
    <div className="container-scroller">
      <Navbar />
      <div className="container-fluid page-body-wrapper">
        <Sidebar />
        <div className="main-panel">
          <div className="content-wrapper">{<Routes />}</div>
          <Footer />
        </div>
      </div>
    </div>
  );
};
export default MainLayout;
