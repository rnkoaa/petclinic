import React from "react";
import "./App.css";
import NavBar from "./components/NavBar";
import { BrowserRouter as Router, Switch, Route} from "react-router-dom";
import MainDashboard from "./components/MainDashboard";
import OwnersDashboard from "./presentation/OwnersDashboard";
import PetsDashboard from "./presentation/PetsDashboard";
import VetsDashboard from "./presentation/VetsDashboard";
import VisitsDashboard from "./presentation/VisitsDashboard";
import OwnerDetail from "./presentation/OwnerDetail"
import { RecoilRoot } from "recoil";

function App() {
  return (
    <RecoilRoot>
      <Router>
      <NavBar />
        <Switch>
          <Route exact path="/">
              <MainDashboard />
          </Route>
          <Route exact path="/owners/:id" component={OwnerDetail} />
            {/* <OwnerDetail />
          </Route> */}
           <Route exact path="/owners">
            <OwnersDashboard />
          </Route>
          <Route exact path="/pets">
            <PetsDashboard />
          </Route>
          <Route exact path="/vets">
            <VetsDashboard />
          </Route>
          <Route exact path="/visits">
            <VisitsDashboard />
          </Route>
        </Switch>
      </Router>
    </RecoilRoot>
  );
}

export default App;
