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

public class OneWireDS2423Device extends OneWireUniversalDevice implements BiCounterDevice {
    static final String ONE_WIRE_FAMILY = "1D";
    static final String ONE_WIRE_TYPE = "DS2423";
    private static final String NAME_COUNTER_A = "counters.A";
    protected static final String PATH_COUNTER_A = "/" + NAME_COUNTER_A;
    private static final String NAME_COUNTER_B = "counters.B";
    protected static final String PATH_COUNTER_B = "/" + NAME_COUNTER_B;
    private long counterA;
    private long counterB;

    OneWireDS2423Device(String basePath, OneWireServer oneWireServer) {
        super(basePath, oneWireServer);
    }

    @Override
    public boolean readSensorValues() throws OneWireException {
        return readSensorValuesWithLimitedRate(
                () -> readSensorValues(oneWireServer, getBasePath()));
    }

    @Override
    public boolean readUncachedSensorValues() throws OneWireException {
        return readSensorValuesWithLimitedRate(
                () -> readSensorValues(oneWireServer, PATH_UNCACHED + getBasePath()));
    }

    @Override
    public long getCounterA() {
        return counterA;
    }

    @Override
    public long getCounterB() {
        return counterB;
    }

    @Override
    public String getNameForCounterA() {
        return NAME_COUNTER_A;
    }

    @Override
    public String getNameForCounterB() {
        return NAME_COUNTER_B;
    }

    private void readSensorValues(OneWireServer oneWireServer, String path) throws OneWireException {
        try {
            counterA = Long.parseLong(oneWireServer.read((path + PATH_COUNTER_A)).trim());
            counterB = Long.parseLong(oneWireServer.read((path + PATH_COUNTER_B)).trim());
        } catch (NumberFormatException e) {
            throw new OneWireException("Sensor values doesn't have expected format (long).", e);
        }
    }

    @Override
    void readBaseParameter() throws OneWireException {
        super.readBaseParameter();

        if (!ONE_WIRE_FAMILY.equals(getFamily()))
            throw new OneWireException(
                    "Wrong family: expected: '" + ONE_WIRE_FAMILY + "'; received: '" + getFamily() + "'.");

        if (!ONE_WIRE_TYPE.equals(getType()))
            throw new OneWireException(
                    "Wrong type: expected: '" + ONE_WIRE_TYPE + "'; received: '" + getType() + "'.");
    }
}
