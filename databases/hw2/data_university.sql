insert into Groups (name)
    values ('M3339'), ('M3338');

set @m39 := (select id from Groups where name = 'M3339');
set @m38 := (select id from Groups where name = 'M3338');


insert into Students (name, group_id)
    values ('Student 1', @m39), ('Student 2', @m39), ('Student 3',@m38);


insert into Subjects (name)
    values ('Math'), ('English');

set @s1 := (select id from Students where name = 'Student 1');
set @s2 := (select id from Students where name = 'Student 2');
set @s3 := (select id from Students where name = 'Student 3');

set @math := (select id from Subjects where name = 'Math');
set @english := (select id from Subjects where name = 'English');

insert into Grades (value, student_id, subject_id)
    values 
        (90, @s1, @math), (100, @s1, @english),
        (91, @s2, @math), (80, @s2, @english),
        (80, @s3, @math), (95, @s3, @english);


set FOREIGN_KEY_CHECKS = 0;
insert into Teachers (name, subject_id)
    values 
        ('Dodo', @math),
        ('Someone 1', @english),
        ('Someone 2', @english);


set @dodo := (select id from Teachers where name = 'Dodo');
set @someone1 := (select id from Teachers where name = 'Someone 1');
set @someone2 := (select id from Teachers where name = 'Someone 2');

insert into Teachings (teacher_id, subject_id)
    values 
        (@dodo, @math),
        (@someone1, @english),
        (@someone2, @english);

set FOREIGN_KEY_CHECKS = 1;

alter table Teachers
    add foreign key (id, subject_id)
    references Teachings (teacher_id, subject_id);

