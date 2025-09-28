create table post
(
    id         int auto_increment
        primary key,
    host       int           not null,
    title      varchar(20)   not null,
    content    varchar(1000) not null,
    subcomment json          null,
    likelist   json          not null,
    picture    json          not null,
    depth      int           not null,
    anonymity  tinyint(1)    not null,
    hidden     tinyint(1)    not null,
    ispublic   tinyint(1)    not null,
    comments   int           not null comment '评论数',
    likes      int           not null comment '点赞数'
);


