use fitness_center;

drop trigger if exists check_insert_visits;
drop trigger if exists check_update_visits;
drop trigger if exists check_insert_personal_trainings;
drop trigger if exists check_update_personal_trainings;

create trigger check_insert_visits
  before insert
  on Visits
  for each row
  begin
    call can_visit(new.client_id, new.subscription_id);
  end;


create trigger check_update_visits
  before update
  on Visits
  for each row
  begin
    signal sqlstate '45000'
    set message_text = 'Visits update restricted.';
  end;


create trigger check_insert_personal_trainings
  before insert
  on PersonalTrainings
  for each row
  begin
    call can_have_personal_training(new.client_id, new.trainer_id, new.subscription_id);
  end;

create trigger check_update_personal_trainings
  before update
  on PersonalTrainings
  for each row
  begin
    signal sqlstate '45000'
    set message_text = 'Trainings update restricted.';
  end;