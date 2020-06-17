import React from 'react'
import Dashboard from '../../components/Dashboard'
import VisitListTable from './VisitsListTable'
const VisitsDashboard = () => {
   return (<Dashboard>
      <main role="main" className="col-md-9 ml-sm-auto col-lg-10 pt-3 px-md-4">
        <h1 className="h2">Visits</h1>
        <React.Suspense fallback={<div>Loading pets...</div>}>
          <VisitListTable />
        </React.Suspense>
      </main>
    </Dashboard>) 
}
export default VisitsDashboard;