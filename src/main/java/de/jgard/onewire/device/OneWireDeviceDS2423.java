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
import org.owfs.jowfsclient.OwfsException;

import java.io.IOException;

public class OneWireDeviceDS2423 extends OneWireDevice {
    protected static final String PATH_COUNTER_A = "/counters.A";
    protected static final String PATH_COUNTER_B = "/counters.B";
    private static final String ONE_WIRE_FAMILY = "1D";
    private static final String ONE_WIRE_TYPE = "DS2423";
    private long counterA;
    private long counterB;

    public OneWireDeviceDS2423(String basePath) {
        super(basePath);
    }

    public void readBaseParameter(OwfsConnection owfsConnection) throws IOException, OwfsException {
        super.readBaseParameter(owfsConnection);

        if (!ONE_WIRE_FAMILY.equals(getFamily()))
            throw new IllegalStateException(
                    "Wrong family: expected: '" + ONE_WIRE_FAMILY + "'; received: '" + getFamily() + "'.");

        if (!ONE_WIRE_TYPE.equals(getType()))
            throw new IllegalStateException(
                    "Wrong type: expected: '" + ONE_WIRE_TYPE + "'; received: '" + getType() + "'.");
    }

    public boolean readSensorValues(OwfsConnection owfsConnection) {
        boolean valuesRead = readSensorValuesWithLimitedRate(() -> {
            readSensorValues(owfsConnection, getBasePath());
        });

        return valuesRead;
    }

    public boolean readUncachedSensorValues(OwfsConnection owfsConnection) {
        boolean valuesRead = readSensorValuesWithLimitedRate(() -> {
            readSensorValues(owfsConnection, PATH_UNCACHED + getBasePath());
        });

        return valuesRead;
    }

    private void readSensorValues(OwfsConnection owfsConnection, String path) {
        try {
            counterA = Long.parseLong(owfsConnection.read((path + PATH_COUNTER_A).trim()));
            counterB = Long.parseLong(owfsConnection.read((path + PATH_COUNTER_B).trim()));
        } catch (IOException | OwfsException e) {
            throw new OneWireException("Can't read sensor values.", e);
        } catch (NumberFormatException e) {
            throw new OneWireException("Sensor values doesn't have expected format (long).", e);
        }
    }

    public long getCounterA() {
        return counterA;
    }

    public long getCounterB() {
        return counterB;
    }
}
