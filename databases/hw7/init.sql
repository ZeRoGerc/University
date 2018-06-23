create database University;
use University;

create table Students (
  StudentId   int          not null primary key,
  StudentName varchar(100) not null,
  GroupId     int          not null
);

create table Groups (
  GroupId    int not null primary key,
  GroupdName varchar(20)
);

create table Courses (
  CourseId   int not null primary key,
  CourseName varchar(100)
);

create table Lecturers (
  LecturerId   int not null primary key,
  LecturerName varchar(100)
);

create table Plan (
  GroupId    int not null,
  CourseId   int not null,
  LecturerId int not null,
  primary key (GroupId, CourseId, LecturerId)
);

create table Marks (
  StudentId int not null,
  CourseId  int not null,
  Mark      int not null,
  primary key (StudentId, CourseId)
);

insert into Groups (GroupId, GroupdName) values
  (1, 'M3339'),
  (2, 'M3338');

insert into Students (StudentId, StudentName, GroupId) values
  (1, 'Ivan', 1),
  (2, 'Vlad', 1),
  (3, 'Nick', 2),
  (4, 'Ahmet', 2);

insert into Courses (CourseId, CourseName) values
  (1, 'Math'),
  (2, 'Databases'),
  (3, 'English');

insert into Lecturers (LecturerId, LecturerName) values
  (1, 'Math teacher 1'),
  (2, 'Physics teacher 1'),
  (3, 'English teacher 1'),
  (4, 'Math teacher 2');

insert into Plan (GroupId, CourseId, LecturerId) values
  (1, 1, 1),
  (1, 2, 2),
  (1, 3, 3),
  (2, 1, 4),
  (2, 3, 3);

insert into Marks (StudentId, CourseId, Mark) values
  (1, 1, 90),
  (1, 2, 80),
  (1, 3, 60),
  (2, 1, 67),
  (2, 2, 80),
  (2, 3, 100),
  (3, 1, 90),
  (3, 2, 15), 
  (3, 3, 72),
  (4, 1, 33),
  (4, 3, 88);
