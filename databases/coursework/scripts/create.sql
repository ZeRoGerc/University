use fitness_center;

drop table if exists Visits;
drop table if exists PersonalTrainings;
drop table if exists DurationTrainings;
drop table if exists StrengthTrainings;
drop table if exists VisitsSubscriptions;
drop table if exists TrainingsSubscriptions;
drop table if exists Apparatus;
drop table if exists Gyms;
drop table if exists StaffWorkInfo;
drop table if exists StaffPersonalInfo;
drop table if exists Clients;

########## Основные сущности (таблицы) ##########
# Клиент (тот, кто занимается в зале).
create table Clients (
  id         int auto_increment not null primary key,
  first_name varchar(100)       not null,
  last_name  varchar(100)       not null,
  birth_date date               not null,
  email      varchar(50)        not null,
  passport   varchar(50)        not null
);

# Персональные данные работников зала.
create table StaffPersonalInfo (
  id         int auto_increment not null primary key,
  first_name varchar(100)       not null,
  last_name  varchar(100)       not null,
  birth_date date               not null,
  email      varchar(50)        not null,
  passport   varchar(50)        not null
);

# Рабочая информация о работниках зала (должность, зарплата, дата выхода, начальник)
create table StaffWorkInfo (
  id         int          not null primary key,
  position   varchar(100) not null,
  salary     int          not null check (salary > 0),
  start_date date         not null,
  chief_id   int          not null,
  foreign key (id) references StaffPersonalInfo (id)
    on delete cascade,
  foreign key (chief_id) references StaffPersonalInfo (id)
    on delete restrict # сначала перераспределить подчиненных
);

# Залы
create table Gyms (
  id      int auto_increment not null primary key,
  name    varchar(50)        not null,
  address varchar(200)       not null
);

# Тренажеры для занятий.
create table Apparatus (
  id                      int auto_increment not null primary key,
  name                    varchar(100)       not null,
  gym_id                  int,
  exploitation_start_date date               not null,
  warranty_end_date       date               not null,
  foreign key (gym_id) references Gyms (id)
    on delete set null
);

########## Таблицы абонементов ##########
# Абонемент на количество посещений, которые надо использовать за определенное время.
# Например 100 посещений в течении года.
create table VisitsSubscriptions (
  id          int auto_increment not null primary key,
  client_id   int                not null,
  visits      int                not null,
  expire_date date               not null,
  foreign key (client_id) references Clients (id)
    on delete cascade
);

# Абонемент на персональные тренировки.
# Например 20 тренировок с мастером спорта.
create table TrainingsSubscriptions (
  id         int auto_increment not null primary key,
  client_id  int                not null,
  trainer_id int                not null,
  trainings  int                not null,
  foreign key (client_id) references Clients (id)
    on delete cascade,
  foreign key (trainer_id) references StaffPersonalInfo (id)
    on delete restrict # сначала поменять тренера
);

########## Таблицы различных видов тренировок или посещений##########
# Посещение человеком определенного зала по абонементу.
create table Visits (
  id              int auto_increment primary key,
  datetime        datetime not null,
  client_id       int      not null,
  gym_id          int      not null,
  subscription_id int      not null,
  foreign key (client_id) references Clients (id)
    on delete cascade,
  foreign key (gym_id) references Gyms (id)
    on delete restrict,
  foreign key (subscription_id) references VisitsSubscriptions (id)
    on delete restrict
);

# Занятие с тренером по абонементу.
create table PersonalTrainings (
  id              int auto_increment primary key,
  datetime        datetime not null,
  client_id       int      not null,
  trainer_id      int      not null,
  subscription_id int      not null,
  foreign key (client_id) references Clients (id)
    on delete cascade,
  foreign key (trainer_id) references StaffPersonalInfo (id)
    on delete restrict, # сначала поменять тренера
  foreign key (subscription_id) references TrainingsSubscriptions (id)
    on delete restrict
);

# Тренировка на тренажере, где важна длительность.
create table DurationTrainings (
  id           int auto_increment primary key,
  datetime     datetime    not null,
  client_id    int         not null,
  apparatus_id int         not null,
  exercise     varchar(50) not null,
  duration     time        not null,
  foreign key (client_id) references Clients (id)
    on delete cascade,
  foreign key (apparatus_id) references Apparatus (id)
    on delete restrict
);

# Тренировка на тренажере, где важен вес и количество повторений.
create table StrengthTrainings (
  id           int auto_increment primary key,
  datetime     datetime    not null,
  client_id    int         not null,
  apparatus_id int         not null,
  exercise     varchar(50) not null,
  repetitions  int         not null,
  weight       int         not null,
  foreign key (client_id) references Clients (id)
    on delete cascade,
  foreign key (apparatus_id) references Apparatus (id)
    on delete restrict
);

########## Индексы ##########
create index client_birthdays
  on Clients (birth_date);

create index staff_birthdays
  on StaffPersonalInfo (birth_date);

create index visits_datetime
  on Visits (datetime);

create index personal_trainings_datetime
  on PersonalTrainings (datetime);

create index strength_trainings_datetime
  on StrengthTrainings (datetime);

create index duration_trainings_date_time
  on DurationTrainings (datetime);
