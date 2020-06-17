import React from 'react'
import Dashboard from '../../components/Dashboard'
import VetsListTable from './VetListTable'
const VetsDashboard = () => {
   return (<Dashboard>
      <main role="main" className="col-md-9 ml-sm-auto col-lg-10 pt-3 px-md-4">
        <h1 className="h2">Vets </h1>
        <React.Suspense fallback={<div>Loading vets...</div>}>
          <VetsListTable />
        </React.Suspense>
      </main>
    </Dashboard>) 
}
export default VetsDashboard;