import React, { useEffect } from "react";
import Table from "react-bootstrap/Table";
import { useSetRecoilState, useRecoilValue } from "recoil";
import { vets, VetsState } from "../../store";
import { capitalize } from "../../utils/strings";

const VetsListTable = () => {
    const setVetsState = useSetRecoilState(VetsState);
    const vetsList = useRecoilValue(vets);

    useEffect(() => {
        setVetsState(vetsList);
    }, [vetsList, setVetsState]);

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
            return vet.specialties.map((s) => capitalize(s.name)).join(", ");
        }
        return "None";
    };

    return (
        <tr>
            <td>{getFullName()}</td>
            <td>{getSpecialties()}</td>
        </tr>
    );
};

export default VetsListTable;
