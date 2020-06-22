import React, { useEffect } from "react";
import Table from "react-bootstrap/Table";
import {  useRecoilValue, useSetRecoilState } from "recoil";
import { Link } from "react-router-dom";
import { owners, OwnersState } from "../../store";
import {capitalize} from '../../utils/strings'

const OwnersListTable = () => {
  const setOwnersState = useSetRecoilState(OwnersState)
  const ownersList = useRecoilValue(owners);

  useEffect(() => {
    setOwnersState(ownersList);
  }, [ownersList, setOwnersState]);

  return (
    <div className="d-flex flex-wrap table-responsive">
      <Table responsive className="table-striped table-bordered pt-3">
        <thead>
          <tr>
            <th>Name</th>
            <th>Address</th>
            <th>City</th>
            <th>Telephone</th>
            <th>Pets</th>
          </tr>
        </thead>
        <tbody>
          {ownersList.map((o) => {
            return <OwnerListTableRow key={o.id} owner={o} />;
          })}
        </tbody>
      </Table>
    </div>
  );
};

const OwnerListTableRow = ({ owner }) => {
  const getFullName = () => {
    return `${capitalize(owner.first_name)} ${capitalize(owner.last_name)}`;
  };

  const getPetNames = () => {
    if (owner.pets) {
      return owner.pets.map((p) => capitalize(p.name)).join(", ");
    }
    return "";
  };
  return (
    <tr>
      <td>
        <Link to={`/owners/${owner.id}`}>{getFullName()}</Link>
      </td>
      <td>{owner.address}</td>
      <td>{owner.city}</td>
      <td>{owner.telephone}</td>
      <td>{getPetNames()}</td>
    </tr>
  );
};

export default OwnersListTable;
