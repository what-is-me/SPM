create table
    if not exists spm.student (
        email varchar(50) collate utf8_unicode_ci not null,
        password varchar(22) collate utf8_unicode_ci not null,
        name varchar(8) collate utf8_unicode_ci not null,
        courseId int,
        teacherAgreed tinyint,
        primary key (email)
    );

create table
    spm.teacher (
        email varchar(50) collate utf8_unicode_ci not null,
        name varchar(8) collate utf8_unicode_ci not null,
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
create table spm.course
(
    courseId int auto_increment,
    board    LONGTEXT    null,
    source   LONGTEXT    null,
    Temail   varchar(50) not null,
    constraint course_pk
        primary key (courseId)
)
    comment '就是班级';

alter table spm.student
    add constraint sc
        foreign key (courseId) references spm.course (courseId);
alter table spm.course
    add constraint tc
        foreign key (Temail) references spm.teacher (email);