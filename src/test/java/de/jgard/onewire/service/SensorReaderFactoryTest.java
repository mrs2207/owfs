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
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import de.jgard.onewire.device.BiCounterDevice;
import de.jgard.onewire.device.OneWireDeviceFactory;
import de.jgard.onewire.device.OneWireServer;
import de.jgard.onewire.model.Sensor;
import de.jgard.onewire.model.Server;

public class SensorReaderFactoryTest {
    @Test
    public void getSensorReader() throws Exception {
        Server server = new Server();
        server.setHostname("localhost");
        server.setPortNumber(4304);

        Sensor sensor = new Sensor();
        sensor.setOneWireAddress("address");
        sensor.setServer(server);

        BiCounterDevice biCounterDevice = null;
        OneWireDeviceFactory oneWireDeviceFactory = mock(OneWireDeviceFactory.class);
        when(oneWireDeviceFactory.getBiCounterDevice(any(OneWireServer.class),
                org.mockito.Mockito.matches(sensor.getOneWireAddress()))).thenReturn(biCounterDevice);

        SensorReaderFactory sensorReaderFactory = new SensorReaderFactory(oneWireDeviceFactory);
        SensorReader sensorReader = sensorReaderFactory.getSensorReader(sensor);
        assertThat(sensorReader).isInstanceOf(BiCounterSensorReader.class);

        BiCounterSensorReader biCounterSensorReader = (BiCounterSensorReader) sensorReader;
        assertThat(biCounterSensorReader.getSensor()).isEqualTo(sensor);
    }
}
