use fitness_center;

drop procedure if exists add_duration_training;
drop procedure if exists add_strength_training;
drop procedure if exists add_personal_training;
drop procedure if exists add_visit;
drop procedure if exists can_visit;
drop procedure if exists can_have_personal_training;
drop function if exists personal_training_subscription_valid;
drop function if exists visits_subscription_valid;
drop function if exists visits_subscription_not_expired;

# Добавляет тренировку на время.
create procedure add_duration_training(
  clientId    int,
  apparatusId int,
  exercise_   varchar(50),
  duration_   time
)
  begin
    insert into DurationTrainings (datetime, client_id, apparatus_id, exercise, duration)
    values (now(), clientId, apparatusId, exercise_, duration_);
  end;

# Добавляет силовую тренировку.
create procedure add_strength_training(
  clientId     int,
  apparatusId  int,
  exercise_    varchar(50),
  repetitions_ int,
  weight_      int
)
  begin
    insert into StrengthTrainings (datetime, client_id, apparatus_id, exercise, repetitions, weight)
    values (now(), clientId, apparatusId, exercise_, repetitions_, weight_);
  end;

# Фиксирует персональную тренировку клиента если его абонемент валиден.
create procedure add_personal_training(clientId int, trainerId int, subscriptionId int)
  begin
    insert into PersonalTrainings (datetime, client_id, trainer_id, subscription_id) values
      (now(), clientId, trainerId, subscriptionId);
  end;

# Фиксирует посещение клиента если его абонемент валиден.
create procedure add_visit(clientId int, gymId int, subscriptionId int)
  begin
    insert into Visits (datetime, client_id, gym_id, subscription_id) values
      (now(), clientId, gymId, subscriptionId);
    commit;
  end;

create procedure can_have_personal_training(clientId int, trainerId int, subscriptionId int)
  begin
    set @client_id = (select client_id
                      from TrainingsSubscriptions
                      where id = subscriptionId);

    set @trainer_id = (select trainer_id
                       from TrainingsSubscriptions
                       where id = subscriptionId);

    if (not @client_id = clientId)
    then
      signal sqlstate '45000'
      set message_text = 'Subscription does not belong to the client.';
    end if;

    if (not @trainer_id = trainerId)
    then
      signal sqlstate '45000'
      set message_text = 'Subscription is for the different trainer.';
    end if;

    if (not personal_training_subscription_valid(subscriptionId))
    then
      signal sqlstate '45000'
      set message_text = 'Subscription is not valid.';
    end if;
  end;

create procedure can_visit(clientId int, subscriptionId int)
  begin
    set @client_id = (select client_id
                      from VisitsSubscriptions
                      where id = subscriptionId);

    if (not @client_id = clientId)
    then
      signal sqlstate '45000'
      set message_text = 'Subscription does not belong to the client.';
    end if;

    if (not visits_subscription_valid(subscriptionId))
    then
      signal sqlstate '45000'
      set message_text = 'Subscription is not valid.';
    end if;
  end;

create function personal_training_subscription_valid(subscriptionId int)
  returns int
  begin
    return ((select count(id)
             from PersonalTrainings
             where subscription_id = id)
            < (select trainings
               from TrainingsSubscriptions
               where id = subscriptionId));
  end;

# Проверяет что абонемент не просрочился и на нем остались посещения.
create function visits_subscription_valid(subscriptionId int)
  returns int
  begin
    return visits_subscription_not_expired(subscriptionId)
           and (
             (select count(id)
              from Visits
              where subscription_id = subscriptionId)
             < (select visits
                from VisitsSubscriptions
                where id = subscriptionId)
           );
  end;


# Проверяет что абонемент не просрочился.
create function visits_subscription_not_expired(subscriptionId int)
  returns int
  begin
    return now() <= (select expire_date
                     from VisitsSubscriptions
                     where id = subscriptionId);
  end;
