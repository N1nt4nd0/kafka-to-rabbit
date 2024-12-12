--liquibase formatted sql

--changeset viacheslav.fedorov@syntegrico.by:create_persons_table

CREATE TABLE IF NOT EXISTS persons (
    id          UUID           PRIMARY KEY NOT NULL,
    first_name  VARCHAR(255)   NOT NULL,
    last_name   VARCHAR(255)   NOT NULL,
    email       VARCHAR(255)   NOT NULL UNIQUE
    );