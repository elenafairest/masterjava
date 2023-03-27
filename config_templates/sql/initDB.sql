DROP TABLE IF EXISTS users_groups;

DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS user_seq;
DROP TYPE IF EXISTS user_flag;

DROP TABLE IF EXISTS city;
DROP SEQUENCE IF EXISTS city_seq;

DROP TABLE IF EXISTS groups;
DROP SEQUENCE IF EXISTS group_seq;
DROP TYPE IF EXISTS group_type;

DROP TABLE IF EXISTS project;
DROP SEQUENCE IF EXISTS project_seq;

CREATE TYPE user_flag AS ENUM ('active', 'deleted', 'superuser');
CREATE TYPE group_type AS ENUM ('REGISTERING', 'CURRENT', 'FINISHED');

CREATE SEQUENCE user_seq START 100000;
CREATE SEQUENCE project_seq START 1000;
CREATE SEQUENCE group_seq START 1000;
CREATE SEQUENCE city_seq START 1000;


CREATE TABLE project
(
    id          INTEGER PRIMARY KEY DEFAULT nextval('project_seq'),
    name        TEXT NOT NULL,
    description TEXT NOT NULL
);

CREATE TABLE groups
(
    id         INTEGER PRIMARY KEY DEFAULT nextval('group_seq'),
    name       TEXT       NOT NULL,
    type       group_type NOT NULL,
    project_id INTEGER    NOT NULL,
    constraint fk_project FOREIGN KEY (project_id) REFERENCES project (id) ON DELETE CASCADE
);

CREATE TABLE city
(
    id      INTEGER PRIMARY KEY DEFAULT nextval('city_seq'),
    name    TEXT NOT NULL,
    city_id TEXT NOT NULL
);

CREATE UNIQUE INDEX city_idx ON city (city_id);

CREATE TABLE users
(
    id        INTEGER PRIMARY KEY DEFAULT nextval('user_seq'),
    full_name TEXT      NOT NULL,
    email     TEXT      NOT NULL,
    flag      user_flag NOT NULL,
    city_id   INTEGER   NOT NULL,
    constraint fk_city FOREIGN KEY (city_id) REFERENCES city (id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX email_idx ON users (email);

CREATE TABLE users_groups
(
    user_id  INTEGER NOT NULL,
    group_id INTEGER NOT NULL,
    constraint fk_users FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    constraint fk_groups FOREIGN KEY (group_id) REFERENCES groups (id) ON DELETE CASCADE
);