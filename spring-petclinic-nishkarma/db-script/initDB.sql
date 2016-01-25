-- Table: book

-- DROP TABLE book;

CREATE TABLE book
(
  id integer NOT NULL,
  title character varying(80),
  author character varying(80),
  cover character(1),
  publishedyear integer,
  available boolean,
  comments character varying(80),
  CONSTRAINT pk_book PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE book
  OWNER TO petclinic;
  
-- Table: owners

-- DROP TABLE owners;

CREATE TABLE owners
(
  id integer NOT NULL,
  first_name character varying(30),
  last_name character varying(30),
  address character varying(255),
  city character varying(80),
  telephone character varying(20),
  CONSTRAINT pk_owners PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE owners
  OWNER TO petclinic;

-- Index: idx_owners_last_name

-- DROP INDEX idx_owners_last_name;

CREATE INDEX idx_owners_last_name
  ON owners
  USING btree
  (last_name COLLATE pg_catalog."default");

-- Table: pets

-- DROP TABLE pets;

CREATE TABLE pets
(
  id integer NOT NULL,
  name character varying(30),
  birth_date date,
  type_id integer,
  owner_id integer,
  CONSTRAINT pk_pets PRIMARY KEY (id),
  CONSTRAINT fk_pets_owners FOREIGN KEY (owner_id)
      REFERENCES owners (id) MATCH SIMPLE
      ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT fk_pets_types FOREIGN KEY (type_id)
      REFERENCES types (id) MATCH SIMPLE
      ON UPDATE RESTRICT ON DELETE RESTRICT
)
WITH (
  OIDS=FALSE
);
ALTER TABLE pets
  OWNER TO petclinic;

-- Index: idx_pets_name

-- DROP INDEX idx_pets_name;

CREATE INDEX idx_pets_name
  ON pets
  USING btree
  (name COLLATE pg_catalog."default");

-- Table: specialties

-- DROP TABLE specialties;

CREATE TABLE specialties
(
  id integer NOT NULL,
  name character varying(80),
  CONSTRAINT pk_specialities PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE specialties
  OWNER TO petclinic;

-- Index: idx_specialties_name

-- DROP INDEX idx_specialties_name;

CREATE INDEX idx_specialties_name
  ON specialties
  USING btree
  (name COLLATE pg_catalog."default");

-- Table: types

-- DROP TABLE types;

CREATE TABLE types
(
  id integer NOT NULL,
  name character varying(80),
  CONSTRAINT pk_types PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE types
  OWNER TO petclinic;

-- Index: idx_types_name

-- DROP INDEX idx_types_name;

CREATE INDEX idx_types_name
  ON types
  USING btree
  (name COLLATE pg_catalog."default");

-- Table: vet_specialties

-- DROP TABLE vet_specialties;

CREATE TABLE vet_specialties
(
  vet_id integer,
  specialty_id integer,
  CONSTRAINT fk_vet_specialities_specialities FOREIGN KEY (specialty_id)
      REFERENCES specialties (id) MATCH SIMPLE
      ON UPDATE RESTRICT ON DELETE RESTRICT
)
WITH (
  OIDS=FALSE
);
ALTER TABLE vet_specialties
  OWNER TO petclinic;

-- Table: vets

-- DROP TABLE vets;

CREATE TABLE vets
(
  id integer NOT NULL,
  first_name character varying(30),
  last_name character varying(30),
  CONSTRAINT pk_vets PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE vets
  OWNER TO petclinic;

-- Table: visits

-- DROP TABLE visits;

CREATE TABLE visits
(
  id integer NOT NULL,
  pet_id integer,
  visit_date date,
  description character varying(255),
  CONSTRAINT pk_visits PRIMARY KEY (id),
  CONSTRAINT fk_visits_pets FOREIGN KEY (pet_id)
      REFERENCES pets (id) MATCH SIMPLE
      ON UPDATE RESTRICT ON DELETE RESTRICT
)
WITH (
  OIDS=FALSE
);
ALTER TABLE visits
  OWNER TO petclinic;

-- Index: idx_visits_pet_id

-- DROP INDEX idx_visits_pet_id;

CREATE INDEX idx_visits_pet_id
  ON visits
  USING btree
  (pet_id);

-- Sequence: seq_book

-- DROP SEQUENCE seq_book;

CREATE SEQUENCE seq_book
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 43
  CACHE 1;
ALTER TABLE seq_book
  OWNER TO petclinic;

-- Sequence: seq_owners

-- DROP SEQUENCE seq_owners;

CREATE SEQUENCE seq_owners
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 12
  CACHE 1;
ALTER TABLE seq_owners
  OWNER TO petclinic;

-- Sequence: seq_pets

-- DROP SEQUENCE seq_pets;

CREATE SEQUENCE seq_pets
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 15
  CACHE 1;
ALTER TABLE seq_pets
  OWNER TO petclinic;

-- Sequence: seq_visits

-- DROP SEQUENCE seq_visits;

CREATE SEQUENCE seq_visits
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 6
  CACHE 1;
ALTER TABLE seq_visits
  OWNER TO petclinic;
  
  

  
