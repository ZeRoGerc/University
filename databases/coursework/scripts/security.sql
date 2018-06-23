use fitness_center;

##### director - директор сети (select для сбора различных статистик, личные данные "скроем") ######
create user director
  identified by password 'director';

grant select on Gyms to director
with grant option;
grant select on Apparatus to director
with grant option;
grant select on Visits to director
with grant option;
grant select on PersonalTrainings to director
with grant option;
grant select on StrengthTrainings to director
with grant option;
grant select on DurationTrainings to director
with grant option;
grant select on StaffWorkInfo to director
with grant option;
grant select on VisitsSubscriptions to director
with grant option;
grant select on TrainingsSubscriptions to director
with grant option;

##### trainer - проводит тренрировки #####
create user trainer
  identified by password 'strength_trainer';

grant execute on add_personal_training to trainer;
grant select on StrengthTrainings to trainer;
grant select on DurationTrainings to trainer;
grant select on Chiefs to trainer;

##### gym manager - отвечает за оборудование зала #####
create user gym_manager
  identified by password 'gym_manager';

grant all privileges on Gyms to gym_manager;
grant all privileges on Apparatus to gym_manager;

##### accountant - выписывает абонементы #####
create user accountant
  identified by password 'accountant';

grant all privileges on VisitsSubscriptions to accountant;
grant all privileges on TrainingsSubscriptions to accountant;

##### Система которая регистрирует деятельность клиентов #####
create user action_system
  identified by password 'action_system';

grant all privileges on Visits to action_system;
grant all privileges on StrengthTrainings to action_system;
grant all privileges on DurationTrainings to action_system;
