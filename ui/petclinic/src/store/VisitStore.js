//     {
//     "id": "8008b58f-4598-4890-b0a1-1aa1baeeaa09",
//     "date": "2009-06-04T05:00:00Z",
//     "petId": "f2cafe9e-ab9c-4fb9-ac3c-ccc95fba6852",
//     "ownerId": "2af0024a-a892-4cb0-9e49-b7fa64128f31",
//     "vetId": null,
//     "description": "neutered"
//   }

import {atom, selector} from 'recoil'
import {findVisits} from '../services/FakeVisitService'

export const VisitState = atom({
    key: 'VisitState',
    default: []
})

export const allVisitState = selector({
    key: 'allVisitState',
    get: async ({get}) => { 
        const visits = get(VisitState)
        if(visits && visits.length > 0) {
            return visits
          }
          return await findVisits()
    }
})

export const selectedVisitIDState = atom({
    key: "selectedVisitIDState",
    default: "",
  });
  
  export const selectedVisitState = selector({
    key: "selectedVisitState",
    get: ({ get }) => {
      const visitId = get(selectedVisitIDState);
      const visits = get(VisitState);
      if (visits && visits.length > 0) {
        console.log("Found visits");
      } else {
        console.log("visits Not found");
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
        id: visitId,
        first_name: "",
        last_name: "",
        specialties: [],
      };
    },
  });