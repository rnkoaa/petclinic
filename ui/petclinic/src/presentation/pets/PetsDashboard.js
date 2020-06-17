import React from 'react'
import Dashboard from '../../components/Dashboard'
import PetsListTable from './PetListTable'

const PetsDashboard = () => {
   return ( <Dashboard>
      <main role="main" className="col-md-9 ml-sm-auto col-lg-10 pt-3 px-md-4">
        <h1 className="h2">Pets</h1>
        <React.Suspense fallback={<div>Loading pets...</div>}>
          <PetsListTable />
        </React.Suspense>
      </main>
    </Dashboard>) 
}
export default PetsDashboard;