--liquibase formatted sql

--changeset elsa:1
CREATE TABLE email (
    id            INTEGER PRIMARY KEY DEFAULT nextval('common_seq'),
    to_list       TEXT,
    cc_list       TEXT,
    subject       TEXT,
    body          TEXT,
    status        TEXT,
    error_message TEXT,
    sent_time     TIMESTAMP
);

