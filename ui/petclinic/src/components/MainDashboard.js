import React, {useEffect} from "react";
import Dashboard from "./Dashboard";
import OwnersListTable from '../presentation/OwnerListTable'

const MainDashboard = () => {
  return (
    <Dashboard>
      <main role="main" className="col-md-9 ml-sm-auto col-lg-10 pt-3 px-md-4">
        <h1 className="h2">Dashboard</h1>
        <React.Suspense fallback={<div>Loading owners with pets...</div>}>
          <OwnersListTable />
        </React.Suspense>
      </main>
    </Dashboard>
  );
};


export default MainDashboard;