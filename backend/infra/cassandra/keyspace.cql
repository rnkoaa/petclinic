CREATE KEYSPACE IF NOT EXISTS petclinic WITH replication = {'class':'SimpleStrategy', 'replication_factor' : 1};

CREATE TYPE IF NOT EXISTS petclinic.vet_specialty (
    id UUID ,
    name TEXT
    );

CREATE TYPE IF NOT EXISTS petclinic.pet_type (
    name TEXT
    );

-- Table to hold list of specialties for reference
CREATE TABLE IF NOT EXISTS petclinic.specialty
(
    id   UUID PRIMARY KEY,
    name TEXT
);

-- Table to hold list of specialties for reference
CREATE TABLE IF NOT EXISTS petclinic.specialty_by_name
(
    name TEXT,
    id   UUID,
    PRIMARY KEY ( name, id )
) WITH CLUSTERING ORDER BY (ID ASC);

CREATE TABLE IF NOT EXISTS petclinic.owner_by_telephone
(
    telephone  TEXT,
    id         UUID,
    first_name TEXT,
    last_name  TEXT,
    address    TEXT,
    city       TEXT,
    PRIMARY KEY (telephone, first_name, last_name, id)
) WITH CLUSTERING ORDER BY (first_name ASC, last_name ASC, id ASC);

CREATE TABLE IF NOT EXISTS petclinic.owner
(
    id         UUID PRIMARY KEY,
    first_name TEXT,
    last_name  TEXT,
    address    TEXT,
    city       TEXT,
    telephone  TEXT,
);

CREATE TABLE IF NOT EXISTS petclinic.pet_by_owner
(
    owner_id   UUID,
    pet_id     UUID,
    name       TEXT,
    birth_date date,
    pet_type   petclinic.pet_type,
    PRIMARY KEY ( owner_id, pet_id )
) WITH CLUSTERING ORDER BY (pet_id ASC);

CREATE TABLE IF NOT EXISTS petclinic.pet
(
    id         UUID PRIMARY KEY,
    owner_id   UUID,
    name       TEXT,
    birth_date date,
    pet_type   petclinic.pet_type
);

CREATE TABLE IF NOT EXISTS petclinic.vet
(
    id          UUID PRIMARY KEY,
    first_name  TEXT,
    telephone   TEXT,
    email       TEXT,
    last_name   TEXT,
    specialties FROZEN<SET<petclinic.vet_specialty>>
);

CREATE TABLE IF NOT EXISTS petclinic.vet_by_telephone
(
    id         UUID,
    first_name TEXT,
    last_name  TEXT,
    telephone  TEXT,
    email      TEXT,
    PRIMARY KEY (telephone, first_name, last_name, id)
) WITH CLUSTERING ORDER BY (first_name ASC, last_name ASC, id ASC);

CREATE TABLE IF NOT EXISTS petclinic.visit
(
    id          UUID PRIMARY KEY,
    date        timestamp,
    pet_id      UUID,
    owner_id    UUID,
    vet_id      UUID,
    description TEXT
);