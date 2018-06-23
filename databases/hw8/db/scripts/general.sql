use airlines;

create table Flights (
  FlightId   int not null,
  FlightTime int not null,
  PlaneId    int not null,
  primary key (FlightId, FlightTime, PlaneId)
);

create table Seats (
  PlaneId    int not null,
  SeatNo     int not null,
  SeatBooked int not null,
  SeatSold   int not null,
  primary key (PlaneId, SeatNo)
);

insert into Flights values
  (1, 100, 1),
  (1, 200, 2),
  (1, 300, 3),
  (2, 150, 10),
  (2, 250, 10),
  (3, 600, 12),
  (3, 1300, 12);

insert into Seats values
  (1, 1, 0, 0),
  (1, 2, 0, 0),
  (2, 1, 0, 0),
  (2, 2, 0, 0),
  (3, 1, 0, 0),
  (3, 2, 0, 0),
  (10, 1, 0, 0),
  (10, 2, 0, 0),
  (10, 3, 0, 0),
  (10, 4, 0, 0),
  (12, 1, 0, 0),
  (12, 2, 0, 0),
  (12, 3, 0, 0),
  (12, 4, 0, 0),
  (12, 5, 0, 0),
  (12, 6, 0, 0);


select *
from Flights;
select *
from Seats;

drop table Flights;
drop table Seats;