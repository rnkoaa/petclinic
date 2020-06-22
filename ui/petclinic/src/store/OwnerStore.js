import { atom, selector } from "recoil";
import { findOwnersWithPets } from "../services/FakeOwnerService";

export const OwnersState = atom({
    key: "ownersState",
    default: [],
});

export const owners = selector({
    key: "owners",
    get: async ({ get }) => {
        const currentOwners = get(OwnersState);
        if (currentOwners && currentOwners.length > 0) {
            return currentOwners;
        }
        return await findOwnersWithPets();
    },
});

export const selectedOwnerIDState = atom({
    key: "selectedOwnerIDState",
    default: "",
});

export const selectedOwnerState = selector({
    key: "selectedOwnerState",
    get: ({ get }) => {
        const ownerId = get(selectedOwnerIDState);
        const owners = get(OwnersState);
        if (owners && owners.length > 0) {
            console.log("Found Owners");
        } else {
            console.log("Owners Not found");
        }
        return {
            id: ownerId,
            first_name: "",
            last_name: "",
        };
    },
});
