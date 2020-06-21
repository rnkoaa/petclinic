import React from "react";
import "./App.scss";
// import NavBar from "./components/NavBar";
import { BrowserRouter as Router } from "react-router-dom";
// import MainDashboard from "./components/MainDashboard";
// import MainDashboard from "./shared/dashboard/MainDashboard";
// import OwnersDashboard from "./presentation/OwnersDashboard";
// import PetsDashboard from "./presentation/pets/PetsDashboard";
// import VetsDashboard from "./presentation/vet/VetsDashboard";
// import VisitsDashboard from "./presentation/visit/VisitsDashboard";
// import OwnerDetail from "./presentation/OwnerDetail";
// import { RecoilRoot } from "recoil";
// import { PetDetail, PetVisitsDetail } from "./presentation/pets";
// import { VisitDetail } from "./presentation/visit";
// import { VetDetail } from "./presentation/vet";
import MainLayout from "./shared/layouts/MainLayout";

function App() {
  return (
    // {/*// <RecoilRoot>
    //   */}
    <Router>

        <MainLayout />
    </Router>
    // {/*//   </Router>
    // </RecoilRoot>
    // */}
  );
}

export default App;
