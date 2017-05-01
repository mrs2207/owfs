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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import de.jgard.onewire.model.Sensor;
import de.jgard.onewire.model.SensorValue;
import de.jgard.onewire.model.SensorValueRepository;

public class PersistSensorValuesTest {
    SensorReaderRegistry sensorReaderRegistry;
    SensorValueRepository sensorValueRepository;
    SensorReader sensorReader;

    @Before
    public void setup() {
        Sensor sensor = new Sensor();

        sensorReader = mock(SensorReader.class);

        sensorReaderRegistry = mock(SensorReaderRegistry.class);
        when(sensorReaderRegistry.getSensorReaders()).thenReturn(Arrays.asList(sensorReader));

        sensorValueRepository = mock(SensorValueRepository.class);
    }

    @Test
    public void readAndSaveSensorValues() throws Exception {
        SensorValue sensorValue = new SensorValue();

        when(sensorReader.readSensorValue()).thenReturn(sensorValue);

        PersistSensorValues persistSensorValues = new PersistSensorValues(sensorValueRepository);
        persistSensorValues.readAndSaveSensorValues(sensorReaderRegistry);

        verify(sensorValueRepository).save(sensorValue);
    }
}
