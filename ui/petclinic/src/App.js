import React from "react";
import "./App.css";
import NavBar from "./components/NavBar";
import { BrowserRouter as Router, Switch, Route} from "react-router-dom";
import MainDashboard from "./components/MainDashboard";
import OwnersDashboard from "./presentation/OwnersDashboard";
import PetsDashboard from "./presentation/pets/PetsDashboard";
import VetsDashboard from "./presentation/vet/VetsDashboard";
import VisitsDashboard from "./presentation/visit/VisitsDashboard";
import OwnerDetail from "./presentation/OwnerDetail"
import { RecoilRoot } from "recoil";
import {PetDetail, PetVisitsDetail} from './presentation/pets'
import {VisitDetail} from './presentation/visit'
import {VetDetail} from './presentation/vet'

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
           <Route exact path="/owners">
            <OwnersDashboard />
          </Route>
          <Route exact path="/pets/:id" component={PetDetail} />
          <Route exact path="/pets/:id/visits" component={PetVisitsDetail} />
          <Route exact path="/pets">
            <PetsDashboard />
          </Route>
          <Route exact path="/vets/:id" component={VetDetail}/>
          <Route exact path="/vets">
            <VetsDashboard />
          </Route>
          <Route exact path="/visits/:id" component={VisitDetail}/>
          <Route exact path="/visits">
            <VisitsDashboard />
          </Route>
        </Switch>
      </Router>
    </RecoilRoot>
  );
}

export default App;
