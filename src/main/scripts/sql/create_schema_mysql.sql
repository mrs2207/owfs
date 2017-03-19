create table Sensor (
	id bigint not null,
	name varchar(64),
	oneWireAddress varchar(32),
	oneWireFamily varchar(32),
	oneWireSensorName varchar(32),
	oneWireType varchar(32),
	primary key (id)
) ENGINE=InnoDB;

create table SensorValue (
	id bigint not null,
	reading bigint not null,
	timeOfMeasurement datetime not null,
	sensor_id bigint not null,
	primary key (id)
) ENGINE=InnoDB;

alter table Sensor
	add constraint UK_6i11ch2yt5iu6cdemevgsdi4a unique (name);

alter table SensorValue
	add constraint FKh8fijrdd4h1da6cgv8b8fr4ah foreign key (sensor_id)
	references Sensor (id);

