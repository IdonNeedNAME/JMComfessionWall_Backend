create table picture
(
    id          int auto_increment
        primary key,
    name        varchar(20) not null,
    width       int         not null,
    height      int         not null,
    featurecode int         not null
);


