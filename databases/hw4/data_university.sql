insert into Groups (id, name)
    values (1, 'M3339'), (2, 'M3338');


insert into Students (id, name)
    values (1, 'Student 1'), (2, 'Student 2'), (3, 'Student 3');

insert into Lecturers (id, name)
    values
        (1, 'Someone 1'),
        (2, 'Someone 2');

insert into Courses (id, name)
    values (1, 'Math'), (2, 'English');

insert into Marks (student_id, course_id, mark)
    values
		(1, 1, 100), (1, 2, 98),
		(2, 1, 78), (2, 2, 80),
		(3, 1, 60), (3, 2, 90);


insert into CourseToLecturer (course_id, lecturer_id)
	values
		(1, 1),
		(2, 2);

insert into StudentToGroup (student_id, group_id)
	values
		(1, 1),
		(2, 1),
		(3, 2);
