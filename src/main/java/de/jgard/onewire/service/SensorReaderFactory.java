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

import org.springframework.stereotype.Service;

import de.jgard.onewire.device.BiCounterDevice;
import de.jgard.onewire.device.OneWireDeviceFactory;
import de.jgard.onewire.device.OneWireServer;
import de.jgard.onewire.model.Sensor;
import de.jgard.onewire.model.Server;

@Service
public class SensorReaderFactory {
    private final OneWireDeviceFactory oneWireDeviceFactory;

    public SensorReaderFactory(OneWireDeviceFactory oneWireDeviceFactory) {
        this.oneWireDeviceFactory = oneWireDeviceFactory;
    }

    public SensorReader getSensorReader(Sensor sensor) {
        Server server = sensor.getServer();
        OneWireServer oneWireServer = new OneWireServer(server.getHostname(), server.getPortNumber());

        BiCounterDevice biCounterDevice = oneWireDeviceFactory.getBiCounterDevice(oneWireServer,
                sensor.getOneWireAddress());
        return new BiCounterSensorReader(sensor, biCounterDevice);
    }
}
