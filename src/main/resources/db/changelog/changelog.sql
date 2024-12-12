--liquibase formatted sql

--changeset viacheslav.fedorov@syntegrico.by:create_persons_table

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE persons (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL
);

--changeset viacheslav.fedorov@syntegrico.by:create_idx_lower_email
-- Creating an index for the email field so that it is impossible to add person with email in a different register

CREATE UNIQUE INDEX idx_lower_email ON persons (LOWER(email));