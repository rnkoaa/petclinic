import React from "react";
import VetsListTable from "./VetListTable";
import TableCard from "../../shared/dashboard/TableCard";

const VetsDashboard = () => {
    return (
        <div>
            <div className="page-header">
                <h3 className="page-title"> Vet Dashboard </h3>
                <nav aria-label="breadcrumb">
                    <ol className="breadcrumb">
                        <li className="breadcrumb-item">
                            <a href="!#" onClick={(event) => event.preventDefault()}>
                                Vets
                            </a>
                        </li>
                    </ol>
                </nav>
            </div>
            <div className="row">
                <div className="col-lg-12 grid-margin stretch-card">
                    <TableCard>
                        <React.Suspense fallback={<div>Loading vets...</div>}>
                            <VetsListTable />
                        </React.Suspense>
                    </TableCard>
                </div>
            </div>
        </div>
    );
};
export default VetsDashboard;
