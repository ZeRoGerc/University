create database university;
use university;

create table Groups (
    id int primary key auto_increment,
    name VARCHAR(20)
);

create table Students (
    id int primary key auto_increment,
    name VARCHAR(50) not null,
    group_id int not null,
    foreign key (group_id)
        references Groups (id)
        on delete cascade
);

create table Subjects (
    id int primary key auto_increment,
    name VARCHAR(50) not null
);

create table Teachers (
    id int primary key auto_increment,
    name VARCHAR(50) not null,
    subject_id int not null
);

create table Grades (
    value int not null,
    student_id int not null,
    subject_id int not null,
    primary key (student_id, subject_id),
    foreign key (student_id)
        references Students (id)
        on delete cascade,
    foreign key (subject_id)
        references Subjects (id)
        on delete cascade
);

create table Teachings (
    teacher_id int not null,
    subject_id int not null,
    primary key (teacher_id, subject_id),
    foreign key (teacher_id)
        references Teachers (id)
        on delete cascade,
    foreign key (subject_id)
        references Subjects (id)
        on delete cascade
);

alter table Teachers 
    add foreign key (id, subject_id) 
    references Teachings (teacher_id, subject_id);
