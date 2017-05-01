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

import de.jgard.onewire.OneWireException;
import de.jgard.onewire.device.BiCounterDevice;
import de.jgard.onewire.model.Sensor;
import de.jgard.onewire.model.SensorValue;

public class BiCounterSensorReader implements SensorReader {
    private final Sensor sensor;
    private final BiCounterDevice device;

    public BiCounterSensorReader(Sensor sensor, BiCounterDevice device) {
        this.sensor = sensor;
        this.device = device;
    }

    @Override
    public SensorValue readSensorValue() {
        device.readSensorValues();

        SensorValue sensorValue = new SensorValue();
        sensorValue.setSensor(sensor);
        sensorValue.setTimeOfMeasurement(device.getTimeOfMeasurement());
        sensorValue.setReading(getReading());

        return sensorValue;
    }

    private long getReading() {
        if (sensor.getOneWireSensorName().equals(device.getNameForCounterA())) {
            return device.getCounterA();
        } else if (sensor.getOneWireSensorName().equals(device.getNameForCounterB())) {
            return device.getCounterB();
        }

        throw new OneWireException("Sensor name can't be matched to device name.");
    }

    Sensor getSensor() {
        return sensor;
    }
}
