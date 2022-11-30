create database if not exists spm collate utf8_unicode_ci;
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
    if not exists spm.teacher
(
    email    varchar(50) collate utf8_unicode_ci not null,
    name     varchar(8) collate utf8_unicode_ci  not null,
    password varchar(22) collate utf8_unicode_ci not null,
    constraint teacher_pk primary key (email)
);

create table
    if not exists spm.messageboard
(
    name varchar(8) collate utf8_unicode_ci                         not null,
    msg  TEXT collate utf8_unicode_ci                               not null,
    time DATETIME collate utf8_unicode_ci default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP
);

create index
    messageboard_time_index on spm.messageboard (time desc);

create table
    if not exists spm.course
(
    courseId int collate utf8_unicode_ci auto_increment,
    email    varchar(50) collate utf8_unicode_ci not null,
    board    LONGTEXT collate utf8_unicode_ci    null,
    source   LONGTEXT collate utf8_unicode_ci    null,
    constraint course_pk primary key (courseId)
) comment '就是班级';

alter table spm.student
    add
        constraint sc foreign key (courseId) references spm.course (courseId);

alter table spm.course
    add
        constraint tc foreign key (email) references spm.teacher (email);

create table
    if not exists spm.files
(
    email    varchar(50) collate utf8_unicode_ci  not null,
    filename varchar(256) collate utf8_unicode_ci not null,
    url      varchar(256) collate utf8_unicode_ci not null,
    constraint files_pk primary key (email, filename)
);
create table exam
(
    eid   int auto_increment
        primary key,
    cid   int         not null,
    begin datetime    not null,
    end   datetime    not null,
    name  varchar(50) not null,
    text  longtext    not null,
    constraint exam_course_null_fk
        foreign key (cid) references spm.course (courseId)
);
create table se
(
    email varchar(50) not null,
    eid   int         not null,
    score double      not null,
    primary key (email, eid),
    constraint se_exam_null_fk
        foreign key (eid) references spm.exam (eid),
    constraint se_student_null_fk
        foreign key (email) references spm.student (email)
)
    comment '学生成绩表';
create view student_score as
select spm.student.email as stu_email,
       spm.student.name  as stu_name,
       e.name            as exam_name,
       spm.se.score      as score,
       c.email           as teacher_email,
       c.courseId
from spm.se
         left join spm.student on se.email = student.email
         left join spm.course c on student.courseId = c.courseId
         left join spm.exam e on e.eid = spm.se.eid
;

INSERT INTO spm.messageboard (name, msg, time)
VALUES ('cqc', 'hello world!', '2022-11-15 16:19:50');
INSERT INTO spm.messageboard (name, msg, time)
VALUES ('cqc', 'hello?', '2022-11-15 21:52:18');
INSERT INTO spm.messageboard (name, msg, time)
VALUES ('cqc', 'emmm', '2022-11-15 21:54:29');
INSERT INTO spm.messageboard (name, msg, time)
VALUES ('cqc', '114514', '2022-11-15 21:54:45');
INSERT INTO spm.messageboard (name, msg, time)
VALUES ('capoo', 'capoo?', '2022-11-15 22:09:02');
INSERT INTO spm.messageboard (name, msg, time)
VALUES ('zjs', '留言', '2022-11-17 14:35:01');
INSERT INTO spm.messageboard (name, msg, time)
VALUES ('zxt', '留言', '2022-11-17 14:35:21');
INSERT INTO spm.messageboard (name, msg, time)
VALUES ('mtj', '留言', '2022-11-17 14:43:21');
INSERT INTO spm.messageboard (name, msg, time)
VALUES ('？？？', '留言', '2022-11-17 14:43:35');
INSERT INTO spm.messageboard (name, msg, time)
VALUES ('ababa', 'hello', '2022-11-17 15:07:27');
INSERT INTO spm.messageboard (name, msg, time)
VALUES ('emmmm', 'helloworld', '2022-11-17 15:08:44');
insert into spm.student(email, password, name)
values ('example@example.com', '123456', '学生1');
insert into spm.student(email, password, name)
values ('example@usst.com', '123456', '学生2');
insert into spm.teacher(email, name, password)
values ('admin@usst.com', 'admin', '123456')