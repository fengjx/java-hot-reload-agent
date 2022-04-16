
drop table if exists todo;

create table todo
(
    id      int primary key auto_increment,
    task    varchar,
    status  varchar,
    createTime datetime
);