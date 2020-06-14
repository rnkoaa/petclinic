import React from 'react';
import './App.css';
import NavBar from './components/NavBar'
import Dashboard from './components/Dashboard'
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link
} from "react-router-dom";
import MainDashboard from './components/MainDashboard';
import OwnersDashboard from './presentation/OwnersDashboard'
import PetsDashboard from './presentation/PetsDashboard'
import VetsDashboard from './presentation/VetsDashboard'
import VisitsDashboard from './presentation/VisitsDashboard'

function App() {
  return (
    <>
    <NavBar />
    {/* // <Dashboard /> */}
    <Router>
      <Switch>
        <Route exact path="/">
          <MainDashboard />
        </Route>
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
    </>
  );
}

export default App;
