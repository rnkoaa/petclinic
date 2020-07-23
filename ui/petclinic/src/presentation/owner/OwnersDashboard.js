import React from "react";
import OwnersListTable from "./OwnerListTable";
import ProBanner from "../../shared/dashboard/ProBanner";
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
                            <button
                                type="button"
                                class="btn btn-primary btn-fw mb-3"
                                onClick={() => {
                                    console.log("adding new owner");
                                }}
                            >
                                Add new owner
                            </button>
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
