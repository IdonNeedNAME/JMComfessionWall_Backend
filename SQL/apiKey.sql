create table apikey
(
    id              int         not null
        primary key,
    apikey          varchar(12) not null,
    lastoperatetime mediumtext  not null
);


