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


##### Task 2.1 #####

create function is_seat_available(flight_id int, flight_time datetime, plane_id int)
  returns int
  begin

  end;

select * from Seats where is_seat_available()