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

package de.jgard.onewire.device;

import de.jgard.onewire.OneWireException;
import org.owfs.jowfsclient.OwfsConnection;
import org.springframework.stereotype.Service;

@Service
public class OneWireDeviceFactory {
    public BiCounterDevice getBiCounterDevice(OwfsConnection owfsConnection, String basePath) {
        OneWireUniversalDevice oneWireDevice = getOneWireUniversalDevice(owfsConnection, basePath);

        if (isDS2423(oneWireDevice)) {
            return getOneWireDS2423Device(owfsConnection, basePath);
        }

        throw new OneWireException(
                "1wire device for path '" + basePath + "' has unknown type (" + oneWireDevice.getType() + ").");
    }

    private OneWireUniversalDevice getOneWireUniversalDevice(OwfsConnection owfsConnection, String basePath) {
        OneWireUniversalDevice oneWireDevice = new OneWireUniversalDevice(basePath);
        oneWireDevice.readBaseParameter(owfsConnection);
        return oneWireDevice;
    }

    private BiCounterDevice getOneWireDS2423Device(OwfsConnection owfsConnection, String basePath) {
        OneWireDS2423Device oneWireDS2423Device = new OneWireDS2423Device(basePath);
        oneWireDS2423Device.readBaseParameter(owfsConnection);

        return oneWireDS2423Device;
    }

    private boolean isDS2423(OneWireUniversalDevice oneWireDevice) {
        return oneWireDevice.getFamily().equals(OneWireDS2423Device.ONE_WIRE_FAMILY) &&
                oneWireDevice.getType().equals(OneWireDS2423Device.ONE_WIRE_TYPE);
    }
}
