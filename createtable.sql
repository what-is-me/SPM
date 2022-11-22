create table
    if not exists spm.student
(
    email         varchar(50) collate utf8_unicode_ci not null,
    password      varchar(22) collate utf8_unicode_ci not null,
    name          varchar(8) collate utf8_unicode_ci  not null,
    courseId      int,
    teacherAgreed tinyint,
    primary key (email)
);

create table
    spm.teacher
(
    email    varchar(50) collate utf8_unicode_ci not null,
    name     varchar(8) collate utf8_unicode_ci  not null,
    password varchar(22) collate utf8_unicode_ci not null,
    constraint teacher_pk primary key (email)
);

create table
    spm.messageboard
(
    name varchar(8) collate utf8_unicode_ci                         not null,
    msg  TEXT collate utf8_unicode_ci                               not null,
    time DATETIME collate utf8_unicode_ci default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP
);

create index
    messageboard_time_index on spm.messageboard (time desc);

create table
    spm.course
(
    courseId int auto_increment,
    email    varchar(50) not null,
    board    LONGTEXT    null,
    source   LONGTEXT    null,
    constraint course_pk primary key (courseId)
) comment '就是班级';

alter table spm.student
    add
        constraint sc foreign key (courseId) references spm.course (courseId);

alter table spm.course
    add
        constraint tc foreign key (email) references spm.teacher (email);

create table
    spm.files
(
    email    varchar(50)  not null,
    filename varchar(256) not null,
    url      varchar(256) not null,
    constraint files_pk primary key (email, filename)
);