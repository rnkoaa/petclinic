import Pets from '../data/pets.json'

export const findPets = async () => {
    return Promise.resolve(Pets)
}