import React from "react";
import { Switch, Route, Redirect } from "react-router-dom";
import MainDashboard from "../dashboard/MainDashboard";
import { OwnerDetail, OwnersDashboard } from "../../presentation/owner";
import { PetDetail, PetVisitsDetail, PetsDashboard} from "../../presentation/pets";
import {VetsDashboard, VetDetail} from '../../presentation/vet'
import {VisitsDashboard, VisitDetail} from '../../presentation/visit'
const Routes = () => {
    return (
        <Switch>
            <Route exact path="/dashboard" component={MainDashboard} />
            <Route exact path="/owners/:id" component={OwnerDetail} />
            <Route exact path="/owners" component={OwnersDashboard} />
            <Route exact path="/pets/:id" component={PetDetail} />
            <Route exact path="/pets/:id/visits" component={PetVisitsDetail} />
            <Route exact path="/pets" component={PetsDashboard} />
            <Route exact path="/vets/:id" component={VetDetail} />
            <Route exact path="/vets" component={VetsDashboard} />

            <Route exact path="/visits/:id" component={VisitDetail} />
            <Route exact path="/visits" component={VisitsDashboard} />
            <Redirect to="/dashboard" />
        </Switch>
    );
};

export default Routes;
