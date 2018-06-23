use fitness_center;

delete from DurationTrainings;
delete from StrengthTrainings;
delete from PersonalTrainings;
delete from Visits;
delete from VisitsSubscriptions;
delete from TrainingsSubscriptions;
delete from Apparatus;
delete from Gyms;
delete from StaffWorkInfo;
delete from StaffPersonalInfo;
delete from Clients;

##### Clients #####
insert into Clients (first_name, last_name, birth_date, email, passport) values
  ('Ulad', 'Sazanovich', '1997-01-02', 'us@mail.com', 'US12345'),
  ('Mikita', 'Sazanovich', '1998-05-30', 'ms@mail.com', 'MS54321'),
  ('Evgeny', 'Malinovsky', '1999-04-14', 'em@mail.com', 'EM53421'),
  ('Alex', 'Rygih', '1997-01-23', 'ar@mail.com', 'AR33442'),
  ('Pavel', 'Kamenko', '1999-09-23', 'pk@mail.com', 'PK14321');

set @ulad_id = (select id
                from Clients
                where first_name = 'Ulad');
set @mikita_id = (select id
                  from Clients
                  where first_name = 'Mikita');
set @evgeny_id = (select id
                  from Clients
                  where first_name = 'Evgeny');
set @alex_id = (select id
                from Clients
                where first_name = 'Alex');
set @pavel_id = (select id
                 from Clients
                 where first_name = 'Pavel');

##### Staff #####
insert into StaffPersonalInfo (first_name, last_name, birth_date, email, passport) values
  ('Jason', 'Statham', '1967-07-26', 'js@mail.com', 'JS11111'),
  ('Dwayne', 'Johnson', '1972-05-02', 'dj@mail.com', 'DJ11111'),
  ('Jackie', 'Chan', '1954-04-07', 'jc@mail.com', 'JC11111');

set @boss_id = (select id
                from StaffPersonalInfo
                where email = 'js@mail.com');

set @strength_trainer_id = (select id
                            from StaffPersonalInfo
                            where email = 'dj@mail.com');

set @martial_arts_trainer_id = (select id
                                from StaffPersonalInfo
                                where email = 'jc@mail.com');

insert into StaffWorkInfo (id, position, salary, start_date, chief_id) values
  (@boss_id, 'Boss', 10000000, '2016-05-01', @boss_id),
  (@strength_trainer_id, 'Strength trainer', 1000000, '2016-06-01', @boss_id),
  (@martial_arts_trainer_id, 'Martial arts trainer', 1000000, '2017-01-01', @boss_id);

##### Gyms #####
insert into Gyms (name, address) values
  ('Strength gym', '42nd Street, Seattle WA'),
  ('Martial arts gym', '8th Ave, Seattle WA');

set @strength_gym_id = (select id
                        from Gyms
                        where name = 'Strength gym');
set @martial_arts_gym_id = (select id
                            from Gyms
                            where name = 'Martial arts gym');

##### Apparatus #####
insert into Apparatus (name, gym_id, exploitation_start_date, warranty_end_date) values
  ('Treadmill', @martial_arts_gym_id, '2017-01-01', '2022-01-01'),
  ('Scott bench', @strength_gym_id, '2016-06-01', '2021-06-01');

set @tredmill_id = (select id
                    from Apparatus
                    where name = 'Treadmill');
set @scott_bench_id = (select id
                       from Apparatus
                       where name = 'Scott bench');

##### Subscriptions #####
insert into VisitsSubscriptions (client_id, visits, expire_date) values
  (@ulad_id, 73, '2019-01-01'),
  (@pavel_id, 2, '2017-01-01');

set @ulad_subscription_id = (select id
                             from VisitsSubscriptions
                             where client_id = @ulad_id);
set @pavel_subscription_id = (select id
                              from VisitsSubscriptions
                              where client_id = @pavel_id);

insert into TrainingsSubscriptions (client_id, trainer_id, trainings) values
  (@mikita_id, @strength_trainer_id, 20),
  (@mikita_id, @martial_arts_trainer_id, 10),
  (@alex_id, @strength_trainer_id, 1),
  (@evgeny_id, @martial_arts_trainer_id, 1);

set @mikita_subscription_id = (select id
                               from TrainingsSubscriptions
                               where client_id = @mikita_id and trainer_id = @strength_trainer_id);

set @mikita_subscription_id_m = (select id
                                 from TrainingsSubscriptions
                                 where client_id = @mikita_id and trainer_id = @martial_arts_trainer_id);

set @alex_subscription_id = (select id
                             from TrainingsSubscriptions
                             where client_id = @alex_id);
set @evgeny_subscription_id = (select id
                               from TrainingsSubscriptions
                               where client_id = @evgeny_id);

##### Visits and trainings ####
insert into Visits (datetime, client_id, gym_id, subscription_id) values
  ('2018-01-02 08:00:00', @ulad_id, @strength_gym_id, @ulad_subscription_id),
  ('2018-01-03 08:00:00', @ulad_id, @strength_gym_id, @ulad_subscription_id),
  ('2016-06-01 08:00:00', @pavel_id, @martial_arts_gym_id, @pavel_subscription_id),
  ('2016-07-01 08:00:00', @pavel_id, @martial_arts_gym_id, @pavel_subscription_id),
  ('2016-08-01 08:00:00', @pavel_id, @strength_gym_id, @pavel_subscription_id);

insert into PersonalTrainings (datetime, client_id, trainer_id, subscription_id) values
  ('2016-06-01', @mikita_id, @strength_trainer_id, @mikita_subscription_id),
  ('2017-01-01', @alex_id, @strength_trainer_id, @alex_subscription_id),
  ('2017-01-01', @mikita_id, @martial_arts_trainer_id, @mikita_subscription_id_m);

insert into DurationTrainings (datetime, client_id, apparatus_id, exercise, duration) values
  ('2016-06-01 08:00:00', @pavel_id, @tredmill_id, 'Running', '01:00:00'),
  ('2016-07-01 08:00:00', @pavel_id, @tredmill_id, 'Running', '01:10:00');

insert into StrengthTrainings (datetime, client_id, apparatus_id, exercise, repetitions, weight) values
  ('2018-01-02 08:00:00', @ulad_id, @scott_bench_id, 'Squats', 10, 90),
  ('2018-01-03 08:00:00', @ulad_id, @scott_bench_id, 'Squats', 10, 95);

#################################################
#################################################
#################################################
##### Procedure and function calls examples #####
select visits_subscription_not_expired(@mikita_subscription_id);
select visits_subscription_valid(@mikita_subscription_id);

call add_visit(@ulad_id, @strength_gym_id, @ulad_subscription_id);

call add_duration_training(@pavel_id, @tredmill_id, 'Running', '00:30:00');
call add_strength_training(@mikita_id, @scott_bench_id, 'Squats', 10, 150);
call add_personal_training(@mikita_id, @strength_trainer_id, @mikita_subscription_id);

##### check_insert_visits trigger #####
# Wrong owner of subscription
insert into Visits (datetime, client_id, gym_id, subscription_id) values
  (now(), @ulad_id, @strength_gym_id, @mikita_subscription_id);

# Expired subscription
insert into Visits (datetime, client_id, gym_id, subscription_id) values
  (now(), @pavel_id, @strength_gym_id, @pavel_subscription_id);

##### check_update_visits trigger #####
update Visits
set datetime = now()
where client_id = @ulad_id;

##### check_insert_trainings trigger #####
# Wrong owner of subscription
insert into PersonalTrainings (datetime, client_id, trainer_id, subscription_id) values
  (now(), @ulad_id, @strength_trainer_id, @mikita_subscription_id);

# Wrong trainer of subscription
insert into PersonalTrainings (datetime, client_id, trainer_id, subscription_id) values
  (now(), @mikita_id, @martial_arts_trainer_id, @mikita_subscription_id);

# Invalid subscription
insert into PersonalTrainings (datetime, client_id, trainer_id, subscription_id) values
  (now(), @alex_id, @strength_trainer_id, @alex_subscription_id);

##### check_update_trainings trigger #####
update PersonalTrainings
set datetime = now()
where client_id = @alex_id;
