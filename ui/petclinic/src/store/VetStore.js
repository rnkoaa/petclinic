import { atom, selector } from "recoil";
import { findVets } from "../services/FakeVetService";

export const VetsState = atom({
  key: "VetsState",
  default: [],
});

export const vets = selector({
  key: "vets",
  get: async ({ get }) => {
    const currentVets = get(VetsState);
    if (currentVets && currentVets.length > 0) {
      return currentVets;
    }
    return await findVets();
  },
});

export const selectedVetIDState = atom({
  key: "selectedVetIDState",
  default: "",
});

export const selectedVetState = selector({
  key: "selectedVetState",
  get: ({ get }) => {
    const vetId = get(selectedVetIDState);
    const vets = get(VetsState);
    if (vets && vets.length > 0) {
      console.log("Found Vets");
    } else {
      console.log("Vets Not found");
    }
    //   "id": "cf2536bb-66f6-45e8-9cc1-a962d39bf606",
    //   "first_name": "Rafael",
    //   "last_name": "Ortega",
    //   "specialties": [
    //     {
    //       "id": "064eaa1b-79a0-40c8-b6fa-afbf09b638e1",
    //       "name": "surgery"
    //     }
    //   ]
    return {
      id: vetId,
      first_name: "",
      last_name: "",
      specialties: [],
    };
  },
});
