use airlines;

create table Flights (
  FlightId   int      not null,
  FlightTime datetime not null,
  PlaneId    int      not null,
  Closed     int      not null,
  primary key (FlightId, FlightTime)
);

create table Seats (
  PlaneId int not null,
  SeatNo  int not null,
  primary key (PlaneId, SeatNo)
);

create table SeatsBookBuy (
  FlightId   int      not null,
  FlightTime datetime not null,
  PlaneId    int      not null,
  SeatNo     int      not null,
  SeatBooked datetime,
  SeatSold   int      not null,
  primary key (FlightId, FlightTime, PlaneId, SeatNo)
);

select *
from Flights;

select *
from Seats;

select *
from SeatsBookBuy;

drop table Flights;
drop table Seats;
drop table SeatsBookBuy;

insert into Flights values
  (1, '2017-12-10 22:00:00', 1, 0),
  (1, '2017-12-11 22:00:00', 2, 0),
  (1, '2017-12-12 22:00:00', 3, 0),
  (2, '2017-12-10 10:00:00', 10, 0);

# select now() >= '2017-12-02 22:00:00' + interval 1 day;

insert into Seats values
  (1, 1),
  (1, 2),
  (2, 1),
  (2, 2),
  (2, 3),
  (2, 4);

##### General triggers, check sold or booked but not both on update and insert #####

create procedure check_seat_either_booked_or_sold_but_not_both(newBooked datetime, newSold int)
  begin
    if ((newBooked is null) and newSold = 0)
    then
      signal sqlstate '45000'
      set message_text = 'Seat should be either booked or sold in SeatsBookBuy';
    end if;

    if ((newBooked is not null) and newSold != 0)
    then
      signal sqlstate '45000'
      set message_text = 'Seat cannot be booked and sold at the same time in SeatsBookBuy';
    end if;
  end;
drop procedure check_seat_either_booked_or_sold_but_not_both;

create trigger booked_and_sold_check_insert
before insert on SeatsBookBuy
for each row
  begin
    call check_seat_either_booked_or_sold_but_not_both(new.SeatBooked, new.SeatSold);
  end;
drop trigger booked_and_sold_check_insert;

create trigger booked_and_sold_check_update
before update on SeatsBookBuy
for each row
  begin
    call check_seat_either_booked_or_sold_but_not_both(new.SeatBooked, new.SeatSold);
  end;

drop trigger booked_and_sold_check_update;

# Check (should fail)
delete from SeatsBookBuy;
insert into SeatsBookBuy values (1, '2017-12-10 22:00:00', 1, 1, null, 0); #fail
insert into SeatsBookBuy values (1, '2017-12-10 22:00:00', 1, 1, now(), 1); #fail
insert into SeatsBookBuy values (1, '2017-12-10 22:00:00', 1, 1, now(), 0); #ok
delete from SeatsBookBuy;

######################### Tasks 1.1, 1.3, 1.4, 1.5 ##############################

##### Reusable general functions #####

create function is_selling_closed_by_admin(flight_id int, flight_time datetime)
  returns int
  begin
    return (select Closed
            from Flights
            where FlightId = flight_id and FlightTime = flight_time);
  end;

create function is_selling_closed_by_timeout(flight_time datetime)
  returns int
  begin
    return now() >= flight_time - interval 2 hour;
  end;

create function is_booking_closed_by_timeout(flight_time datetime)
  returns int
  begin
    return now() >= flight_time - interval 1 day;
  end;

create function count_free_seats(flight_id int, flight_time datetime, plane_id int)
  returns int
  begin
    return
    (select count(Seats.SeatNo)
     from Seats
     where Seats.PlaneId = plane_id and (
       Seats.SeatNo not in (select SeatsBookBuy.SeatNo
                            from SeatsBookBuy
                            where SeatsBookBuy.FlightId = flight_id and
                                  SeatsBookBuy.FlightTime = flight_time and
                                  SeatsBookBuy.PlaneId = plane_id)));
  end;

create function booking_valid(flight_time datetime, booking_time datetime)
  returns int
  begin
    set @booked = booking_time is not null; # seat booked

    # drop booking after one day
    if ((booking_time is not null) and (now() >= booking_time + interval 1 day))
    then
      set @booked = false;
    end if;

    # drop booking 1 day before flight
    if ((flight_time is not null) and (now() >= flight_time - interval 1 day))
    then
      set @booked = false;
    end if;

    return @booked;
  end;

##### Procedures for triggers #####

create procedure check_selling_not_closed(flight_id int, flight_time datetime, plane_id int)
  begin
    if (is_selling_closed_by_admin(flight_id, flight_time))
    then
      signal sqlstate '45000'
      set message_text = 'Ticket selling for this flight was closed by admin';
    end if;

    if (is_selling_closed_by_timeout(flight_time))
    then
      signal sqlstate '45000'
      set message_text = 'Ticket selling closes two hours before the flight';
    end if;

    if (count_free_seats(flight_id, flight_time, plane_id) = 0)
    then
      signal sqlstate '45000'
      set message_text = 'No free seats for this flight available';
    end if;
  end;

create procedure check_booking_not_closed(flight_id int, flight_time datetime, plane_id int)
  begin
    call check_selling_not_closed(flight_id, flight_time, plane_id); # first see if selling available

    if (is_booking_closed_by_timeout(flight_time))
    then # booking closes one day before the flight
      signal sqlstate '45000'
      set message_text = 'Booking closes one day before the flight';
    end if;
  end;

create procedure check_not_booked(flightTime datetime, oldBooked datetime)
  begin
    if ((oldBooked is not null) and (booking_valid(flightTime, oldBooked)))
    then
      signal sqlstate '45000'
      set message_text = 'Seat already booked!';
    end if;
  end;

create trigger seat_insert_book_buy_info_check
before insert on SeatsBookBuy
for each row
  begin
    if (new.SeatBooked is not null)
    then
      call check_booking_not_closed(new.FlightId, new.FlightTime, new.PlaneId);
    end if;

    if (new.SeatSold = 1)
    then
      call check_selling_not_closed(new.FlightId, new.FlightTime, new.PlaneId);
    end if;
  end;

create trigger seat_update_book_buy_info_check
before update on SeatsBookBuy
for each row
  begin
    if (old.SeatSold = 1 and new.SeatSold != 0)
    then
      signal sqlstate '45000'
      set message_text = 'Seat already bought!';
    end if;

    if (old.SeatBooked != new.SeatBooked) # book info changed
    then
      call check_booking_not_closed(old.FlightId, old.FlightTime, old.PlaneId);
      call check_not_booked(old.FlightTime, old.SeatBooked);
    elseif (old.SeatSold = 0 and new.SeatSold = 1)
      then
        call check_selling_not_closed(old.FlightId, old.FlightTime, old.PlaneId);
    end if;
  end;

drop trigger seat_update_book_buy_info_check;

##### Task 2.1 #####

create procedure get_available_seats(flight_id int, flight_time datetime, plane_id int)
  begin
    start transaction;
    call check_selling_not_closed(flight_id, flight_time, plane_id);

    select *
    from Seats
    where PlaneId = plane_id and
          SeatNo not in (select SeatsBookBuy.SeatNo
                         from SeatsBookBuy
                         where SeatsBookBuy.FlightId = flight_id and
                               SeatsBookBuy.FlightTime = flight_time and
                               SeatsBookBuy.PlaneId = plane_id and
                               (SeatsBookBuy.SeatSold = 1 or
                                booking_valid(flight_time, SeatsBookBuy.SeatBooked)));
    commit;
  end;
drop procedure get_available_seats;

call get_available_seats(2, '2017-12-10 10:00:00', 2);
call get_available_seats(1, '2017-12-10 22:00:00', 1);

##### Task 2.2 #####

create procedure book_seat(flight_id int, flight_time datetime, plane_id int, seat_no int)
  begin
    insert into SeatsBookBuy values (flight_id, flight_time, plane_id, seat_no, now(), 0)
    on duplicate key update SeatsBookBuy.SeatBooked = now();
  end;

call book_seat(2, '2017-12-10 10:00:00', 2, 1);

##### Task 2.3 #####

create procedure update_booking(flight_id int, flight_time datetime, plane_id int, seat_no int)
  begin
    start transaction;
    set @book_time = (select SeatBooked
                      from SeatsBookBuy
                      where FlightId = flight_id and
                            FlightTime = flight_time and
                            PlaneId = plane_id and
                            SeatNo = seat_no);

    if (not (booking_valid(flight_time, @book_time)))
    then
      signal sqlstate '45000'
      set message_text = 'Booking not valid anymore!';
    end if;

    update SeatsBookBuy
    set SeatBooked = null
    where
      FlightId = flight_id and FlightTime = flight_time and PlaneId = plane_id and SeatNo = seat_no;

    update SeatsBookBuy
    set SeatBooked = now()
    where
      FlightId = flight_id and FlightTime = flight_time and PlaneId = plane_id and SeatNo = seat_no;
    commit;
  end;
drop procedure update_booking;

call update_booking(1, '2017-12-10 22:00:00', 1, 1);

##### Task 2.4 #####

create procedure buy_seat(flight_id int, flight_time datetime, plane_id int, seat_no int)
  begin
    insert into SeatsBookBuy values (flight_id, flight_time, plane_id, seat_no, null, 1)
    on duplicate key update SeatsBookBuy.SeatSold = 1;
  end;

call buy_seat(2, '2017-12-10 10:00:00', 2, 1);

##### Task 2.5 #####

create procedure buy_booked_seat(flight_id int, flight_time datetime, plane_id int, seat_no int)
  begin
    start transaction;
    set @book_time = (select SeatBooked
                      from SeatsBookBuy
                      where FlightId = flight_id and
                            FlightTime = flight_time and
                            PlaneId = plane_id and
                            SeatNo = seat_no);

    if (not (booking_valid(flight_time, @book_time)))
    then
      signal sqlstate '45000'
      set message_text = 'Seat not booked or booking not valid anymore!';
    end if;

    update SeatsBookBuy
    set SeatBooked = null, SeatSold = 1
    where FlightId = flight_id and
          FlightTime = flight_time and
          PlaneId = plane_id and
          SeatNo = seat_no;
    commit;
  end;
drop procedure buy_booked_seat;

call buy_booked_seat(2, '2017-12-10 10:00:00', 2, 1);

##### Task 2.6 #####

create procedure close_selling(flight_id int, flight_time datetime)
  begin
    update Flights
    set Closed = 1
    where FlightId = flight_id and FlightTime = flight_time;
  end;

##### Task 2.7 #####

create function get_all_seats(plane_id int)
  returns int
  begin
    set @count = (select count(*)
                  from Seats
                  where PlaneId = plane_id);
    return @count;
  end;
drop function get_all_seats;

create function get_sold_count(flight_id int, flight_time datetime, plane_id int)
  returns int
  begin
    set @count = (select count(*)
                  from SeatsBookBuy
                  where FlightId = flight_id and
                        FlightTime = flight_time and
                        PlaneId = plane_id and
                        SeatSold = 1);
    return @count;
  end;
drop function get_sold_count;

create function get_booked_count(flight_id int, flight_time datetime, plane_id int)
  returns int
  begin
    set @count = (select count(*)
                  from SeatsBookBuy
                  where FlightId = flight_id and
                        FlightTime = flight_time and
                        PlaneId = plane_id and
                        booking_valid(flight_time, SeatBooked));
    return @count;
  end;
drop function get_booked_count;

create procedure get_stats()
  begin
    select
      FlightId,
      FlightTime,
      PlaneId,
      Closed,
      get_all_seats(PlaneId) - get_booked_count(FlightId, FlightTime, PlaneId) -
      get_sold_count(FlightId, FlightTime, PlaneId)   as Free,
      get_booked_count(FlightId, FlightTime, PlaneId) as Booked,
      get_sold_count(FlightId, FlightTime, PlaneId)   as Sold
    from Flights;
  end;
drop procedure get_stats;

call get_stats();

select *
from Flights;
select *
from SeatsBookBuy;

call buy_seat(1, '2017-12-11 22:00:00', 2, 1);
call book_seat(1, '2017-12-11 22:00:00', 2, 2)