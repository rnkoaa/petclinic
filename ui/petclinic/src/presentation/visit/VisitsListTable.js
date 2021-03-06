import React, { useEffect } from "react";
import Table from "react-bootstrap/Table";
import { useSetRecoilState, useRecoilValue } from "recoil";
import { VisitState, allVisitState } from "../../store";
import { Link } from "react-router-dom";

const VisitListTable = () => {
  const setVisitState = useSetRecoilState(VisitState);
  const visits = useRecoilValue(allVisitState);

  useEffect(() => {
    setVisitState(visits);
  }, [visits, setVisitState]);

  return (
    <div className="d-flex flex-wrap table-responsive">
      <Table responsive className="table-striped table-bordered pt-3">
        <thead>
          <tr>
            <th>Date</th>
            <th>Pet</th>
            <th>Owner</th>
            <th>Description</th>
          </tr>
        </thead>
        <tbody>
          {visits.map((o) => {
            return <VisitListTableRow key={o.id} visit={o} />;
          })}
        </tbody>
      </Table>
    </div>
  );
};

const VisitListTableRow = ({ visit }) => {
  // const getFullName = () => {
  //   return `${owner.first_name} ${owner.last_name}`;
  // };

  // const getPetNames = () => {
  //   if (owner.pets) {
  //     return owner.pets.map((p) => p.name).join(", ");
  //   }
  //   return "";
  // };
  return (
    <tr>
      <td>{visit.date}</td>
      <td>{visit.petId}</td>
      <td>
        <Link to={`/owners/${visit.ownerId}`}>{visit.ownerId}</Link>
      </td>
      <td class="text-capitalize">{visit.description}</td>
    </tr>
  );
};

export default VisitListTable;
