--DROP extension if exists "uuid-ossp";
--CREATE EXTENSION if not exists "uuid-ossp";
--
--create table example_table
--(
--    id           character varying(64) not null primary key,
--    name         character varying(100),
--    created_date date                  not null default now(),
--    created_time timestamp             not null,
--    is_active    boolean                        default false,
--    counter      int                   not null default 0,
--    currency     decimal               not null,
--    description  text,
--    floating     double precision
--);
--
--insert into example_table(id, name, created_date, created_time, is_active, counter, currency, description, floating)
--values ('001', 'Sammi Aldhi Yanto', now(), now(), true, 0, 100000, null, 0.1),
--       ('002', 'Ayatulah Ramadhan Jacoeb', now(), now(), true, 0, 100000, null, 10.1),
--       ('003', 'Aditya Andika Putra', now(), now(), true, 0, 100000, null, 100),
--       ('004', 'Gusnur', now(), now(), true, 0, 100000, null, 0.25),
--       ('005', 'Dandi Arnanda', now(), now(), true, 0, 100000, null, 0);
--

DROP extension if exists "uuid-ossp";
CREATE EXTENSION if not exists "uuid-ossp";

create table person(
    id           character varying(64) not null primary key,
    name         character varying(100),
    email        character varying(100),
    is_active    boolean                        default false,
    salary       decimal               not null,
    created_date date                  not null default now(),
    created_time timestamp             not null
);

insert into person(id, name, email, is_active, salary, created_date, created_time)
values ('001', 'Sammi Aldhi Yanto',        'Sammi@gmail.com',    true,  100000, now(), now()),
       ('002', 'Ayatulah Ramadhan Jacoeb', 'ayat@gmail.com',     true,  100000, now(), now()),
       ('003', 'Aditya Andika Putra',      'Aditya@gmail.com',   true,  100000, now(), now()),
       ('004', 'Gusnur',                   'Gusnur@gmail.com',   true,  100000, now(), now()),
       ('005', 'Dandi Arnanda',            'Dandi@gmail.com',    true,  100000, now(), now()
);