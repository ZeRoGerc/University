use fitness_center;

set @birthday = '1997-01-15';

insert into Clients (first_name, last_name, birth_date, email, passport) values
  ('Fake', 'Client', @birthday, 'fc@mail.com', 'US12345');

insert into StaffPersonalInfo (first_name, last_name, birth_date, email, passport) values
  ('Fake', 'Trainer', @birthday, 'ft@mail.com', 'US12345');

# Выбрать электронные адреса людей у которых сегодня день рождения.
select email
from Clients
where
  dayofmonth(birth_date) = dayofmonth(now()) and month(birth_date) = month(now())
union select email
      from StaffPersonalInfo
      where
        dayofmonth(birth_date) = dayofmonth(now()) and month(birth_date) = month(now());

# Для каждого клиента посчитать количество посещений и персональных тренировок за последний месяц.
select
  Clients.id,
  Clients.first_name,
  Clients.last_name,
  Clients.birth_date,
  Clients.email,
  VP.type,
  count(VP.id) as amount
from Clients
  inner join
  (select
     Visits.id        as id,
     Visits.client_id as cid,
     'visits'         as type
   from Visits
   where Visits.datetime >= now() - interval 1 month
   union
   (select
      PersonalTrainings.id        as id,
      PersonalTrainings.client_id as cid,
      'personal'                  as type
    from PersonalTrainings
    where PersonalTrainings.datetime >= now() - interval 1 month)
  ) VP on VP.cid = Clients.id
group by Clients.id, VP.type;

# По id клиента посчитать количество и среднюю продолжительность каждого вида duration training.
select
  exercise                                as exercise,
  count(*)                                as amount,
  sec_to_time(avg(time_to_sec(duration))) as average_duration
from DurationTrainings
where
  client_id = 32
group by exercise;

insert into StrengthTrainings (datetime, client_id, apparatus_id, exercise, repetitions, weight) values
  ('2018-01-03 08:10:00', @ulad_id, @scott_bench_id, 'Squats', 4, 120);

# По id клиента посчитать максимально поднятый вес в упражнении для каждого дня (прогресс)
select
  exercise as exercise,
  max(weight)
from StrengthTrainings
where
  client_id = 28
group by exercise, date(datetime);

insert into VisitsSubscriptions (client_id, visits, expire_date) values
  (6, 10, '2018-01-08'),
  (7, 1, '2018-10-01');

# Выдать электронные адреса клиентов которым нужно напомнить о продлении абонемента и вид абонемента.
select
  Clients.id,
  Clients.email,
  SU.type
from Clients
  inner join
  (select
     client_id,
     'visit' as type
   from VisitsSubscriptions
   where VisitsSubscriptions.expire_date >= now() - interval 1 week and
         (VisitsSubscriptions.expire_date <= now() + interval 1 week or
          VisitsSubscriptions.visits <= (select count(id)
                                         from Visits
                                         where subscription_id = VisitsSubscriptions.id) + 3)
   union
   select
     client_id,
     'personal' as type
   from TrainingsSubscriptions
   where TrainingsSubscriptions.trainings <= (select count(id)
                                              from PersonalTrainings
                                              where subscription_id = TrainingsSubscriptions.id) + 2
  )
    as SU on Clients.id = SU.client_id;

# Выдать для каждого клиента его любимый зал (зал в котором у него больше всего посещений)
select
  Clients.id,
  Clients.first_name,
  Clients.last_name,
  Gyms.id as favourite_gym
from Clients, Gyms
where (select count(id) as visits
       from Visits vi
       where vi.client_id = Clients.id and
             vi.gym_id = Gyms.id)
      = (select max(visits)
         from
           (select
              vm.client_id,
              vm.gym_id,
              count(vm.id) as visits
            from Visits vm
            group by client_id, gym_id) MV
         where MV.client_id = Clients.id
      );

# Выдать пары тренеров, которые проводили занятия у одних и тех же людей
select
  f.id as id1,
  s.id as id2
from StaffWorkInfo f, StaffWorkInfo s
where f.id != s.id and
      f.id < s.id and
      exists(select id
             from Clients
             where (select count(id)
                    from PersonalTrainings
                    where PersonalTrainings.trainer_id = f.id) != 0 and
                   (select count(id)
                    from PersonalTrainings
                    where PersonalTrainings.trainer_id = s.id) != 0
      );

drop view if exists Chiefs;

# Работник и его начальник.
create view Chiefs as
  select
    w.id as id,
    c.id as cid
  from StaffWorkInfo w, StaffWorkInfo c
  where w.chief_id = c.id;

select *
from Chiefs;

# Клиенты которые были активны в последний месяц.
drop view if exists ActiveClients;
create view ActiveClients as
  select Clients.*
  from Clients
  where (Clients.id in (select V.client_id
                        from Visits V
                        where V.datetime >= now() - interval 1 month)) or
        (Clients.id in (select P.client_id
                        from PersonalTrainings P
                        where P.datetime >= now() - interval 1 month));

select *
from ActiveClients;