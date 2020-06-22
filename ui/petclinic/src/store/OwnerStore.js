import { atom, selector, selectorFamily } from "recoil";
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

export const selectOwnerByIdState = selectorFamily({
    key: "selectOwnerByIdState",
    get: (ownerId) => async ({ get }) => {
        const owners = get(OwnersState);
        if (owners && owners.length > 0) {
            console.log("owners exist");
            const idx = owners.findIndex((item) => item.id === ownerId);
            if (idx <= -1) {
                throw new Error(`owner with id ${ownerId} not found`);
            }
            return owners[idx];
        }
        return {
            id: ownerId,
            first_name: "",
            last_name: "",
        };
    },
});
