create sequence hibernate_sequence start with 1 increment by 1;

create table Sensor (
	id bigint not null,
	name varchar(64) not null,
	oneWireAddress varchar(32),
	oneWireFamily varchar(32),
	oneWireSensorName varchar(32),
	oneWireType varchar(32),
	server_id bigint not null,
	primary key (id)
);

create table SensorValue (
	id bigint not null,
	reading bigint not null,
	timeOfMeasurement timestamp not null,
	sensor_id bigint not null,
	primary key (id)
);

create table Server (
	id bigint not null,
	hostname varchar(255) not null,
	name varchar(64) not null,
	portNumber integer not null,
	primary key (id)
);

alter table Sensor
	add constraint UK_6i11ch2yt5iu6cdemevgsdi4a unique (name);

alter table Server
	add constraint UK_6j7pjpqptft8f5m2obom5gyyl unique (name);

alter table Sensor
	add constraint FKc3m7so6m2w2c8yll90ayjgfom foreign key (server_id)references Server;

alter table SensorValue
	add constraint FKh8fijrdd4h1da6cgv8b8fr4ah foreign key (sensor_id)references Sensor;

--
-- initialize hibernate_sequence
drop sequence hibernate_sequence;
create sequence hibernate_sequence start with 1000 increment by 1;