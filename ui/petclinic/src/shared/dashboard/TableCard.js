import React from "react";

export default function TableCard(props) {
    return (
        <div className="card">
            <div className="card-body">
                <div className="table-responsive">{props.children}</div>
            </div>
        </div>
    );
}
