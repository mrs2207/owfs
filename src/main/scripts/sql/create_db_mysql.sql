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
drop database if exists power;
create database power character set = utf8;
drop user power@'%';
create user power@'%' identified by '8fBPezm*ci';
grant all privileges on `power` . * to  'power'@'%' with grant option;
grant select on `mysql`.`proc` TO 'power'@'%';