# Аттрибуты
StudentId, StudentName, GroupId, GroupName, CourseId, CourseName, LecturerId, LecturerName, Mark

# Функциональные зависимости

StudentId -> StudentName GroupId GroupName
StudentId CourseId -> Mark
CourseId -> CourseName
GroupId -> GroupName
LecturerId -> LecturerName

## Добавим такие зависимости чтобы было интересней

GroupId CourseName -> CourseId 
CourseId -> CourseName LecturerId LecturerName 

# Ключи

## Алгоритм
Начальное состояние: StudentId StudentName GroupId GroupName CourseId CourseName LecturerId LecturerName Mark
Уберем имена: StudentId GroupdId CourseId LecturerId Mark
Уберем оценку: StudentId GroupId CourseId LecturerId
Пользуемся 'CourseId -> CourseName LecturerId LecturerName': StudentId GroupId CourseId

Также ключом может быть StudentId  

# Неприводимое множество

StudentId -> StudentName
GroupId -> GroupName
CourseId -> CourseName
LecturerId -> LecturerName

StudentId CourseId -> Mark
StudentId -> GroupId

## С добавленными примерами
CourseId -> LecturerId
