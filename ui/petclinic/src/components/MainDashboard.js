import React, {useEffect} from "react";
import Table from "react-bootstrap/Table";
import Dashboard from "./Dashboard";
import { useRecoilState, useRecoilValue } from "recoil";
import {Link} from 'react-router-dom'
import { owners, OwnersState} from "../store/Store";

const MainDashboard = () => {
  return (
    <Dashboard>
      <main role="main" className="col-md-9 ml-sm-auto col-lg-10 pt-3 px-md-4">
        <h1 className="h2">Dashboard</h1>
        <React.Suspense fallback={<div>Loading owners with pets...</div>}>
          <OwnersListTable />
        </React.Suspense>
      </main>
    </Dashboard>
  );
};

const OwnersListTable = () => {
  const [ownerState, setOwnerState] = useRecoilState(OwnersState)
  const ownersList = useRecoilValue(owners);

  useEffect(() => {
    setOwnerState(ownersList)
  }, [ownersList])

  return (
    <div className="d-flex flex-wrap table-responsive">
      <Table responsive className="pt-3">
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
    return `${owner.first_name} ${owner.last_name}`;
  };

  const getPetNames = () => {
    if (owner.pets) {
      return owner.pets.map((p) => p.name).join(", ");
    }
    return "";
  };
  return (
    <tr>
      <td>
      <Link to={`/owners/${owner.id}`}>
        {getFullName()}
        </Link>
        </td>
      <td>{owner.address}</td>
      <td>{owner.city}</td>
      <td>{owner.telephone}</td>
      <td>{getPetNames()}</td>
    </tr>
  );
};

export default MainDashboard;
