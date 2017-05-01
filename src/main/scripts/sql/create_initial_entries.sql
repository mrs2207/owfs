-- Copyright (c) 2017 Michael Schoene.
--
-- This program is free software: you can redistribute it and/or modify
-- it under the terms of the GNU General Public License as published by
-- the Free Software Foundation, either version 3 of the License, or
-- (at your option) any later version.
--
-- This program is distributed in the hope that it will be useful,
-- but WITHOUT ANY WARRANTY; without even the implied warranty of
-- MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
-- GNU General Public License for more details.
--
-- You should have received a copy of the GNU General Public License
-- along with this program.  If not, see <http://www.gnu.org/licenses/>.
--
insert into Server (id,name,hostname,portNumber)
    values (1, "OneWire", "localhost", 4304);
insert into Sensor (id,name,oneWireAddress,oneWireFamily,oneWireType,oneWireSensorName,server_id)
    values (1, 'PowerMain', '1D.AA5D0B000000', '1D', 'DS2423', 'counters.A',1);
insert into Sensor (id,name,oneWireAddress,oneWireFamily,oneWireType,oneWireSensorName,server_id)
    values (2, 'PowerGarden', '1D.AA5D0B000000', '1D', 'DS2423', 'counters.B',1);
