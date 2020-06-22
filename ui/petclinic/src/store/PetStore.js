import { atom, selector } from "recoil";
import { findPets } from "../services/FakePetService";

export const PetsState = atom({
    key: "PetsState",
    default: [],
});

export const pets = selector({
    key: "pets",
    get: async ({ get }) => {
        const currentPets = get(PetsState);
        if (currentPets && currentPets.length > 0) {
            return currentPets;
        }
        return await findPets();
    },
});

export const selectedPetIDState = atom({
    key: "selectedPetIDState",
    default: "",
});

export const selectedPetState = selector({
    key: "selectedPetState",
    get: ({ get }) => {
        const petId = get(selectedPetIDState);
        const pets = get(PetsState);
        if (pets && pets.length > 0) {
            console.log("Found Pets");
        } else {
            console.log("Pets Not found");
        }
        // "id": "f2cafe9e-ab9c-4fb9-ac3c-ccc95fba6852",
        // "owner_id": "2af0024a-a892-4cb0-9e49-b7fa64128f31",
        // "birth_date": "1995-09-04",
        // "type": "cat",
        // "name": "Max"
        return {
            id: petId,
            owner_id: "",
            birth_date: "",
            type: "",
            name: "",
        };
    },
});
