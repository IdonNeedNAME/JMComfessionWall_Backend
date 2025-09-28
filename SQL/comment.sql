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


