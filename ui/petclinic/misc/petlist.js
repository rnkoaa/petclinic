"use strict";

const fs = require("fs");

fs.readFile("./owners_with_pets.json", (err, data) => {
  if (err) throw err;
  const owners = JSON.parse(data);
  owners.forEach(owner => {
    const { pets } = owner;
    let petNames = ""
    if (pets) {
  petNames =      pets.map(p => p.name).join(', ')
    }
        console.log(`Owner ${owner.first_name} ${owner.last_name} ${petNames}`);
  });
});
