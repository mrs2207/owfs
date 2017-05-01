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

import java.sql.Timestamp;

import de.jgard.onewire.OneWireException;
import de.jgard.onewire.util.LimitedRateExecutor;

public class OneWireUniversalDevice {
    // 1wire path
    static final String PATH_ADDRESS = "/address";
    static final String PATH_CRC8 = "/crc8";
    static final String PATH_FAMILY = "/family";
    static final String PATH_ID = "/id";
    static final String PATH_LOCATOR = "/locator";
    static final String PATH_PRESENT = "/present";
    static final String PATH_TYPE = "/type";
    static final String PATH_UNCACHED = "/uncached";

    static final long MINIMAL_READ_SENSOR_VALUES_INTERVAL = 1000;
    protected final OneWireServer oneWireServer;
    private final String basePath;
    private final LimitedRateExecutor limitedRateExecutor;

    private String address;
    private String crc8;
    private String family;
    private String id;
    private String locator;
    private String present;
    private String type;

    private Timestamp timeOfMeasurement;

    OneWireUniversalDevice(String basePath, OneWireServer oneWireServer) {
        this.basePath = basePath;
        this.oneWireServer = oneWireServer;

        limitedRateExecutor = new LimitedRateExecutor(MINIMAL_READ_SENSOR_VALUES_INTERVAL);
    }

    @Override
    public String toString() {
        return "OneWireUniversalDevice{" + "basePath='" + basePath + '\'' + ", address='" + address + '\'' + ", crc8='"
                + crc8
                + '\'' + ", family='" + family + '\'' + ", id='" + id + '\'' + ", locator='" + locator + '\''
                + ", present='" + present + '\'' + ", type='" + type + '\'' + '}';
    }

    public String getAddress() {
        return address;
    }

    public String getCrc8() {
        return crc8;
    }

    public String getFamily() {
        return family;
    }

    public String getId() {
        return id;
    }

    public String getLocator() {
        return locator;
    }

    public String getPresent() {
        return present;
    }

    public String getType() {
        return type;
    }

    public Timestamp getTimeOfMeasurement() {
        return timeOfMeasurement;
    }

    boolean readSensorValuesWithLimitedRate(Runnable run) {
        boolean executed = limitedRateExecutor.executeWithLimitedRate(run);
        timeOfMeasurement = limitedRateExecutor.getLastExecutionTime();
        return executed;
    }

    String getBasePath() {
        return basePath;
    }

    void readBaseParameter() throws OneWireException {
        address = oneWireServer.read(basePath + PATH_ADDRESS);
        crc8 = oneWireServer.read(basePath + PATH_CRC8);
        family = oneWireServer.read(basePath + PATH_FAMILY);
        id = oneWireServer.read(basePath + PATH_ID);
        locator = oneWireServer.read(basePath + PATH_LOCATOR);
        present = oneWireServer.read(basePath + PATH_PRESENT);
        type = oneWireServer.read(basePath + PATH_TYPE);
    }
}
