create table if not exists spm.student
(
    email    varchar(50) not null,
    password varchar(22) not null,
    name     varchar(8)  not null,
    courseId int,
    teacherAgreed tinyint,
    primary key (email)
);