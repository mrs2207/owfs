/*
 * Copyright (c) 2017 Michael Schoene.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package de.jgard.onewire.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.jgard.onewire.model.SensorValue;
import de.jgard.onewire.model.SensorValueRepository;

@Service
public class PersistSensorValues {
    private final SensorValueRepository sensorValueRepository;

    @Autowired
    public PersistSensorValues(SensorValueRepository sensorValueRepository) {
        this.sensorValueRepository = sensorValueRepository;
    }

    @Transactional
    public void readAndSaveSensorValues(SensorReaderRegistry sensorReaderRegistry) {
        for (SensorReader sensorReader : sensorReaderRegistry.getSensorReaders()) {
            SensorValue sensorValue = sensorReader.readSensorValue();

            sensorValueRepository.save(sensorValue);
        }
    }
}
