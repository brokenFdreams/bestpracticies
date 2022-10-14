DROP ALL OBJECTS;

create table if not exists users (
    id bigint not null primary key,
    login varchar(255) not null,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    age int not null
);

create sequence users_id_sequence increment by 1 minvalue 1000;
