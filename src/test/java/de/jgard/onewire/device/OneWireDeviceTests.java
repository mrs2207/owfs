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

import org.junit.Test;
import org.owfs.jowfsclient.OwfsConnection;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class OneWireDeviceTests {
    private static final String BASEPATH = "/1D.AA5D0B000000";

    private boolean sensorValuesRead;

    @Test
    public void readBaseParameter() throws Exception {
        OwfsConnection owfsConnection = mock(OwfsConnection.class);

        when(owfsConnection.read(BASEPATH + OneWireDevice.PATH_ADDRESS)).thenReturn("1DAA5D0B000000BD");
        when(owfsConnection.read(BASEPATH + OneWireDevice.PATH_CRC8)).thenReturn("BD");
        when(owfsConnection.read(BASEPATH + OneWireDevice.PATH_FAMILY)).thenReturn("1D");
        when(owfsConnection.read(BASEPATH + OneWireDevice.PATH_ID)).thenReturn("AA5D0B000000");
        when(owfsConnection.read(BASEPATH + OneWireDevice.PATH_LOCATOR)).thenReturn("FF08FFFFFFFFFFFF");
        when(owfsConnection.read(BASEPATH + OneWireDevice.PATH_PRESENT)).thenReturn("1");
        when(owfsConnection.read(BASEPATH + OneWireDevice.PATH_TYPE)).thenReturn("DS2423");

        OneWireDevice oneWireDevice = new OneWireDevice(BASEPATH);
        oneWireDevice.readBaseParameter(owfsConnection);

        assertThat(oneWireDevice.getAddress()).isEqualTo("1DAA5D0B000000BD");
        assertThat(oneWireDevice.getCrc8()).isEqualTo("BD");
        assertThat(oneWireDevice.getFamily()).isEqualTo("1D");
        assertThat(oneWireDevice.getId()).isEqualTo("AA5D0B000000");
        assertThat(oneWireDevice.getLocator()).isEqualTo("FF08FFFFFFFFFFFF");
        assertThat(oneWireDevice.getPresent()).isEqualTo("1");
        assertThat(oneWireDevice.getType()).isEqualTo("DS2423");
    }

    @Test
    public void readSensorValuesWithLimitedRateToFast() throws Exception {
        OneWireDevice oneWireDevice = new OneWireDevice(BASEPATH);
        sensorValuesRead = false;

        boolean executed = oneWireDevice.readSensorValuesWithLimitedRate(() -> {
            sensorValuesRead = true;
        });
        assertThat(executed).isTrue();
        assertThat(sensorValuesRead).isTrue();

        executed = oneWireDevice.readSensorValuesWithLimitedRate(() -> {
            sensorValuesRead = false;
        });
        assertThat(executed).isFalse();
        assertThat(sensorValuesRead).isTrue();
    }

    @Test
    public void readSensorValuesWithLimitedRate() throws Exception {
        OneWireDevice oneWireDevice = new OneWireDevice(BASEPATH);
        sensorValuesRead = false;

        boolean executed = oneWireDevice.readSensorValuesWithLimitedRate(() -> {
            sensorValuesRead = true;
        });
        assertThat(executed).isTrue();
        assertThat(sensorValuesRead).isTrue();

        Thread.sleep(OneWireDevice.MINIMAL_READ_SENSOR_VALUES_INTERVAL);
        executed = oneWireDevice.readSensorValuesWithLimitedRate(() -> {
            sensorValuesRead = false;
        });
        assertThat(executed).isTrue();
        assertThat(sensorValuesRead).isFalse();
    }

    @Test
    public void testToString() throws Exception {
        OwfsConnection owfsConnection = mock(OwfsConnection.class);

        when(owfsConnection.read(BASEPATH + OneWireDevice.PATH_ADDRESS)).thenReturn("1DAA5D0B000000BD");
        when(owfsConnection.read(BASEPATH + OneWireDevice.PATH_CRC8)).thenReturn("BD");
        when(owfsConnection.read(BASEPATH + OneWireDevice.PATH_FAMILY)).thenReturn("1D");
        when(owfsConnection.read(BASEPATH + OneWireDevice.PATH_ID)).thenReturn("AA5D0B000000");
        when(owfsConnection.read(BASEPATH + OneWireDevice.PATH_LOCATOR)).thenReturn("FF08FFFFFFFFFFFF");
        when(owfsConnection.read(BASEPATH + OneWireDevice.PATH_PRESENT)).thenReturn("1");
        when(owfsConnection.read(BASEPATH + OneWireDevice.PATH_TYPE)).thenReturn("DS2423");

        OneWireDevice oneWireDevice = new OneWireDevice(BASEPATH);
        oneWireDevice.readBaseParameter(owfsConnection);

        String toString = oneWireDevice.toString();
        assertThat(toString).isEqualTo(
                "OneWireDevice{basePath='/1D.AA5D0B000000', address='1DAA5D0B000000BD', crc8='BD', family='1D', id='AA5D0B000000', locator='FF08FFFFFFFFFFFF', present='1', type='DS2423'}");
    }
}
