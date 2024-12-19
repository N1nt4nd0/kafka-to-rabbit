--liquibase formatted sql

--changeset viacheslav.fedorov@syntegrico.by:create_uuid_generate_extension
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

--changeset viacheslav.fedorov@syntegrico.by:create_companies_table
CREATE TABLE company (
    id BIGSERIAL PRIMARY KEY,
    company_name VARCHAR(255) NOT NULL UNIQUE
);

--changeset viacheslav.fedorov@syntegrico.by:create_persons_table
CREATE TABLE person (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    company_id BIGINT,
    FOREIGN KEY (company_id) REFERENCES company(id) ON DELETE SET NULL
);

--changeset viacheslav.fedorov@syntegrico.by:create_hobbies_table
CREATE TABLE hobby (
    id BIGSERIAL PRIMARY KEY,
    hobby_name VARCHAR(255) NOT NULL UNIQUE
);

--changeset viacheslav.fedorov@syntegrico.by:create_person_hobby_table
CREATE TABLE person_hobby (
    person_id UUID NOT NULL,
    hobby_id BIGINT NOT NULL,
    PRIMARY KEY (person_id, hobby_id),
    FOREIGN KEY (person_id) REFERENCES person(id) ON DELETE CASCADE,
    FOREIGN KEY (hobby_id) REFERENCES hobby(id) ON DELETE CASCADE
);

--changeset viacheslav.fedorov@syntegrico.by:create_idx_lower_email
CREATE UNIQUE INDEX idx_lower_email ON person (LOWER(email));

--changeset viacheslav.fedorov@syntegrico.by:create_idx_lower_company_name
CREATE UNIQUE INDEX idx_lower_company_name ON company (LOWER(company_name));

--changeset viacheslav.fedorov@syntegrico.by:create_idx_lower_hobby_name
CREATE UNIQUE INDEX idx_lower_hobby_name ON hobby (LOWER(hobby_name));