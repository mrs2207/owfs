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

import java.sql.Timestamp;

import org.junit.Before;
import org.junit.Test;

import de.jgard.onewire.OneWireException;
import de.jgard.onewire.device.BiCounterDevice;
import de.jgard.onewire.model.Sensor;
import de.jgard.onewire.model.SensorValue;

public class BiCounterSensorReaderTest {
    BiCounterDevice biCounterDevice;
    Timestamp currentTime;

    @Before
    public void setup() {
        currentTime = new Timestamp(System.currentTimeMillis());

        biCounterDevice = mock(BiCounterDevice.class);
        when(biCounterDevice.getNameForCounterA()).thenReturn("counterA");
        when(biCounterDevice.getNameForCounterB()).thenReturn("counterB");
        when(biCounterDevice.getCounterA()).thenReturn(42L);
        when(biCounterDevice.getCounterB()).thenReturn(43L);
        when(biCounterDevice.getTimeOfMeasurement()).thenReturn(currentTime);
    }

    @Test
    public void readSensorValueA() throws Exception {
        readSensorValue("counterA", 42L);
    }

    @Test
    public void readSensorValueB() throws Exception {
        readSensorValue("counterB", 43L);
    }

    @Test(expected = OneWireException.class)
    public void readSensorWithInvalidName() throws Exception {
        readSensorValue("counterXY", 42L);
    }

    @Test
    public void getSensor() throws Exception {
        Sensor sensor = new Sensor();

        BiCounterSensorReader biCounterSensorReader = new BiCounterSensorReader(sensor, biCounterDevice);

        assertThat(biCounterSensorReader.getSensor()).isEqualTo(sensor);
    }

    private void readSensorValue(String sensorName, long reading) {
        Sensor sensor = new Sensor();
        sensor.setOneWireSensorName(sensorName);

        BiCounterSensorReader biCounterSensorReader = new BiCounterSensorReader(sensor, biCounterDevice);
        SensorValue sensorValue = biCounterSensorReader.readSensorValue();

        assertThat(sensorValue.getReading()).isEqualTo(reading);
        assertThat(sensorValue.getSensor()).isEqualTo(sensor);
        assertThat(sensorValue.getTimeOfMeasurement()).isEqualTo(currentTime);
    }
}
