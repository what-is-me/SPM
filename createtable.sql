-- Active: 1643025696105@@127.0.0.1@3306@spm

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
    spm.messageboard (
        name varchar(8) collate utf8_unicode_ci not null,
        msg TEXT collate utf8_unicode_ci not null,
        time DATETIME collate utf8_unicode_ci default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP
    );

create index
    messageboard_time_index on spm.messageboard (time desc);