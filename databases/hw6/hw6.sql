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

# task 1
select *
from Students
where
  Students.StudentId in (
    select Marks.StudentId
    from Marks
    where Marks.Mark = 80 and Marks.CourseId in (select Courses.CourseId
                                                 from Courses
                                                 where Courses.CourseName = "Databases"));

# task 2
select *
from Students
where Students.StudentId not in (select Marks.StudentId
                                 from Marks
                                 where Marks.CourseId in (select Courses.CourseId
                                                          from Courses
                                                          where
                                                            Courses.CourseName =
                                                            "Databases"));

select *
from Students
where
  Students.GroupId in (select Plan.GroupId
                       from Plan
                       where Plan.CourseId in (
                         select Courses.CourseId
                         from Courses
                         where Courses.CourseName = "Databases"))
  and Students.StudentId not in (select Marks.StudentId
                                 from Marks
                                 where Marks.CourseId in (select Courses.CourseId
                                                          from Courses
                                                          where
                                                            Courses.CourseName =
                                                            "Databases"));

# task 3
select *
from Students
where Students.StudentId in (select Marks.StudentId
                             from Marks
                             where Marks.CourseId in (select Plan.CourseId
                                                      from Plan
                                                      where Plan.LecturerId = 1));

# task 4
select Students.StudentId
from Students
where Students.StudentId not in (select Marks.StudentId
                                 from Marks
                                 where Marks.CourseId in (select Plan.CourseId
                                                          from Plan
                                                          where Plan.LecturerId = 1));

# task 5
select *
from Students
where not exists(select *
                 from Courses
                 where
                   Courses.CourseId in (select Plan.CourseId
                                        from Plan
                                        where Plan.LecturerId = 3)
                   and not exists(select *
                                  from Marks
                                  where Marks.StudentId = Students.StudentId and Marks.CourseId = Courses.CourseId));

# task 6
select
  Students.StudentId,
  Students.StudentName,
  Plan.CourseId
from Students, Plan
where Students.GroupId = Plan.GroupId;

# task 7
select *
from Students
where Students.GroupId in (select Plan.GroupId
                           from Plan
                           where Plan.LecturerId = 1);

# task 8
select *
from Students as S1, Students as S2
where
  not exists(select Marks.CourseId
             from Marks
             where
               Marks.StudentId = S1.StudentId
               and Marks.Mark >= 60
               and Marks.CourseId not in (select Marks.CourseId
                                          from Marks
                                          where Marks.StudentId = S2.StudentId and Marks.Mark >= 60));

# task 9

select
  Plan.GroupId,
  Plan.CourseId
from Plan
where not exists(select *
                 from Students
                 where
                   Students.GroupId = Plan.GroupId
                   and not exists(select *
                                  from Marks
                                  where Marks.StudentId = Students.StudentId
                                        and Marks.CourseId = Plan.CourseId
                                        and Marks.Mark >= 60));
