create table adminwhitelist
(
    id       int auto_increment
        primary key,
    username varchar(20) not null
);

create table apikey
(
    id              int        not null
        primary key,
    apikey          text       not null,
    lastoperatetime mediumtext not null
);

create table comment
(
    id         int auto_increment
        primary key,
    content    varchar(1000) not null,
    depth      int           not null,
    subcomment json          null,
    likelist   json          null,
    hidden     int           null,
    host       int           not null,
    dadtype    int           not null comment '父节点的类型',
    dadid      int           not null comment '父节点的id',
    likes      int           not null comment '点赞数',
    comments   int           not null comment '评论数'
);

create table picture
(
    id          int auto_increment
        primary key,
    name        varchar(20) not null,
    width       int         not null,
    height      int         not null,
    featurecode int         not null
);

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

create table user
(
    id         int auto_increment
        primary key,
    username   varchar(20) not null,
    name       varchar(20) not null,
    password   varchar(30) not null,
    type       int         not null,
    pictureref int         not null,
    blacklist  json        null
);


