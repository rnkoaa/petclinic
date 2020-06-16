import React, {useEffect} from "react";
import Dashboard from "../components/Dashboard";

import { selectedOwnerIDState, selectedOwnerState} from '../store/Store'
import { useRecoilState, useRecoilValue } from "recoil";

const OwnerDetail = ({match}) => {
    const {id} = match.params
    const [selectedOwnerId, setSelectedOwnerId] = useRecoilState(selectedOwnerIDState)

    useEffect(() => {
        // Run! Like go get some data from an API.
        setSelectedOwnerId(id)
      }, [id]);
    const selectedOwner = useRecoilValue(selectedOwnerState)
    console.log(selectedOwner)
  return (
    <Dashboard>
      <main role="main" className="col-md-9 ml-sm-auto col-lg-10 pt-3 px-md-4">
        <h1 className="h2">Owner Details</h1>
        <h6>{id}</h6>
      </main>
    </Dashboard>
  );
};
export default OwnerDetail;
