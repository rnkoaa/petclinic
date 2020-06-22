import React from "react";
import OwnersListTable from "./OwnerListTable";

const OwnersDashboard = () => {
    return (
        <div>
            <div className="page-header">
                <h3 className="page-title"> Current Pet Owners </h3>
                <nav aria-label="breadcrumb">
                    <ol className="breadcrumb">
                        <li className="breadcrumb-item">
                            <a href="!#" onClick={(event) => event.preventDefault()}>
                                Owners
                            </a>
                        </li>
                    </ol>
                </nav>
            </div>
            <div className="row">
                <div className="col-lg-12 grid-margin stretch-card">
                    <div className="card">
                        <div className="card-body">
                            <React.Suspense fallback={<div>Loading owners with pets...</div>}>
                                <OwnersListTable />
                            </React.Suspense>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default OwnersDashboard;
