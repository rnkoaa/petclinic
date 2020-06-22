import React from "react";
import VisitListTable from "./VisitsListTable";
import TableCard from "../../shared/dashboard/TableCard";

const VisitsDashboard = () => {
    return (
        <div>
            <div className="page-header">
                <h3 className="page-title"> Visits Dashboard </h3>
                <nav aria-label="breadcrumb">
                    <ol className="breadcrumb">
                        <li className="breadcrumb-item">
                            <a href="!#" onClick={(event) => event.preventDefault()}>
                                Visits
                            </a>
                        </li>
                    </ol>
                </nav>
            </div>
            <div className="row">
                <div className="col-lg-12 grid-margin stretch-card">
                    <TableCard>
                        <React.Suspense fallback={<div>Loading pets...</div>}>
                            <VisitListTable />
                        </React.Suspense>
                    </TableCard>
                </div>
            </div>
        </div>
    );
};
export default VisitsDashboard;
