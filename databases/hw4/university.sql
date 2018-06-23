create database university;
use university;

create table Groups (
    id int primary key,
    name VARCHAR(20) not null
);

create table Students (
    id int primary key,
    name VARCHAR(100) not null
);

create table Lecturers (
    id int primary key,
    name VARCHAR(100) not null
);

create table Courses (
    id int primary key,
    name VARCHAR(100) not null
);

create table Marks (
    student_id int not null,
    course_id int not null,
    mark int not null,
    primary key (student_id, course_id),
    foreign key (student_id)
        references Students (id),
    foreign key (course_id)
        references Courses (id)
);

create table CourseToLecturer (
    course_id int not null,
    lecturer_id int not null,
    primary key (course_id),
    foreign key (lecturer_id)
        references Lecturers (id),
    foreign key (course_id)
        references Courses (id)
);

create table StudentToGroup (
	student_id int not null,
	group_id int not null,
	primary key (student_id),
	foreign key (student_id)
		references Students (id),
	foreign key (group_id)
		references Groups (id)
)
