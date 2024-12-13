--liquibase formatted sql

--changeset viacheslav.fedorov@syntegrico.by:create_uuid_generate_extension
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

--changeset viacheslav.fedorov@syntegrico.by:create_jobs_table
CREATE TABLE jobs (
    id BIGSERIAL PRIMARY KEY,
    job_title VARCHAR(255) NOT NULL
);

--changeset viacheslav.fedorov@syntegrico.by:create_persons_table
CREATE TABLE persons (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    job_id BIGINT,
    FOREIGN KEY (job_id) REFERENCES jobs(id) ON DELETE SET NULL
);

--changeset viacheslav.fedorov@syntegrico.by:create_idx_lower_email
CREATE UNIQUE INDEX idx_lower_email ON persons (LOWER(email));

--changeset viacheslav.fedorov@syntegrico.by:create_hobbies_table
CREATE TABLE hobbies (
    id BIGSERIAL PRIMARY KEY,
    hobby_name VARCHAR(255) NOT NULL,
    person_id UUID NOT NULL,
    FOREIGN KEY (person_id) REFERENCES persons(id) ON DELETE CASCADE
);