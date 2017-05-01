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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Test;

import de.jgard.onewire.model.Sensor;
import de.jgard.onewire.model.SensorRepository;

public class SensorReaderRegistryTest {
    @Test
    public void getSensorReaders() throws Exception {
        Sensor sensor = new Sensor();

        SensorRepository sensorRepository = mock(SensorRepository.class);
        when(sensorRepository.findAll()).thenReturn(Arrays.asList(sensor));
        SensorReader sensorReader = mock(SensorReader.class);
        SensorReaderFactory sensorReaderFactory = mock(SensorReaderFactory.class);
        when(sensorReaderFactory.getSensorReader(sensor)).thenReturn(sensorReader);

        SensorReaderRegistry sensorReaderRegistry = new SensorReaderRegistry(sensorReaderFactory, sensorRepository);
        sensorReaderRegistry.buildSensorReaderRegistry();

        assertThat(sensorReaderRegistry.getSensorReaders()).containsExactly(sensorReader);
    }
}
