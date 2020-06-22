import React from "react";
import PetsListTable from "./PetListTable";
import TableCard from "../../shared/dashboard/TableCard";

const PetsDashboard = () => {
    return (
        <div>
            <div className="page-header">
                <h3 className="page-title"> All Available Pets </h3>
                <nav aria-label="breadcrumb">
                    <ol className="breadcrumb">
                        <li className="breadcrumb-item">
                            <a href="!#" onClick={(event) => event.preventDefault()}>
                                Pets
                            </a>
                        </li>
                    </ol>
                </nav>
            </div>
            <div className="row">
                <div className="col-lg-12 grid-margin stretch-card">
                    <TableCard>
                        <React.Suspense fallback={<div>Loading pets...</div>}>
                            <PetsListTable />
                        </React.Suspense>
                    </TableCard>
                </div>
            </div>
        </div>
    );
};
export default PetsDashboard;
