create table report
(
    id        int auto_increment
        primary key,
    targetype int           not null,
    content   varchar(1000) not null,
    status    int           not null,
    result    int           null,
    reason    varchar(1000) null,
    targetid  int           not null,
    host      int           not null
);


