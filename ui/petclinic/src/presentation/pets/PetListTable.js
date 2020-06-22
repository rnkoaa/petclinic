import React, { useEffect } from "react";
import Table from "react-bootstrap/Table";
import { useSetRecoilState, useRecoilValue } from "recoil";
import { Link } from "react-router-dom";
import { pets, PetsState } from "../../store";
import { capitalize } from "../../utils/strings";
const PetsListTable = () => {
    const setPetsState = useSetRecoilState(PetsState);
    const petsList = useRecoilValue(pets);

    useEffect(() => {
        setPetsState(petsList);
    }, [petsList, setPetsState]);

    return (
        <div className="d-flex flex-wrap table-responsive">
            <Table responsive className="table-striped table-bordered pt-3">
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Birthdate</th>
                        <th>Type</th>
                        <th>Owner</th>
                        <th>Visits</th>
                    </tr>
                </thead>
                <tbody>
                    {petsList.map((o) => {
                        return <PetListTableRow key={o.id} pet={o} />;
                    })}
                </tbody>
            </Table>
        </div>
    );
};

const PetListTableRow = ({ pet }) => {
    return (
        <tr>
            <td>{pet.name}</td>
            <td>{pet.birth_date}</td>
            <td>{capitalize(pet.type)}</td>
            <td>
                <Link to={`/owners/${pet.owner_id}`}>{pet.owner_id}</Link>
            </td>
            <td>
                <Link to={`/pets/${pet.id}/visits`}>Visits</Link>
            </td>
        </tr>
    );
};

export default PetsListTable;
