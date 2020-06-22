import React, { useEffect } from "react";
import Table from "react-bootstrap/Table";
import { useRecoilState, useRecoilValue } from "recoil";
import { Link } from "react-router-dom";
import { vets, VetsState } from "../../store/Store";

const VetsListTable = () => {
  const [vetsState, setVetsState] = useRecoilState(VetsState);
  const vetsList = useRecoilValue(vets);

  useEffect(() => {
    setVetsState(vetsList);
  }, [vetsList]);

  return (
    <div className="d-flex flex-wrap table-responsive">
      <Table responsive className="table-striped table-bordered pt-3">
        <thead>
          <tr>
            <th>Name</th>
            <th>Specialties</th>
          </tr>
        </thead>
        <tbody>
          {vetsList.map((o) => {
            return <VetListTableRow key={o.id} vet={o} />;
          })}
        </tbody>
      </Table>
    </div>
  );
};

const VetListTableRow = ({ vet }) => {
  const getFullName = () => {
    return `${vet.first_name} ${vet.last_name}`;
  };

  const getSpecialties = () => {
    if (vet.specialties) {
     return vet.specialties.map((s) => s.name).join(", ");
    }
    return "";
  };

  return (
    <tr>
      <td>{getFullName()}</td>
      <td>{getSpecialties()}</td>
    </tr>
  );
};

export default VetsListTable;
