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
import org.junit.Test;
import org.owfs.jowfsclient.OwfsConnection;
import org.owfs.jowfsclient.OwfsException;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class OneWireUniversalDeviceTests {
    private static final String BASEPATH = "/1D.AA5D0B000000";

    private boolean sensorValuesRead;

    @Test
    public void readBaseParameter() throws Exception {
        OneWireServer oneWireServer = mock(OneWireServer.class);

        when(oneWireServer.read(BASEPATH + OneWireUniversalDevice.PATH_ADDRESS)).thenReturn("1DAA5D0B000000BD");
        when(oneWireServer.read(BASEPATH + OneWireUniversalDevice.PATH_CRC8)).thenReturn("BD");
        when(oneWireServer.read(BASEPATH + OneWireUniversalDevice.PATH_FAMILY)).thenReturn("1D");
        when(oneWireServer.read(BASEPATH + OneWireUniversalDevice.PATH_ID)).thenReturn("AA5D0B000000");
        when(oneWireServer.read(BASEPATH + OneWireUniversalDevice.PATH_LOCATOR)).thenReturn("FF08FFFFFFFFFFFF");
        when(oneWireServer.read(BASEPATH + OneWireUniversalDevice.PATH_PRESENT)).thenReturn("1");
        when(oneWireServer.read(BASEPATH + OneWireUniversalDevice.PATH_TYPE)).thenReturn("DS2423");

        OneWireUniversalDevice oneWireDevice = new OneWireUniversalDevice(BASEPATH);
        oneWireDevice.readBaseParameter(oneWireServer);

        assertThat(oneWireDevice.getAddress()).isEqualTo("1DAA5D0B000000BD");
        assertThat(oneWireDevice.getCrc8()).isEqualTo("BD");
        assertThat(oneWireDevice.getFamily()).isEqualTo("1D");
        assertThat(oneWireDevice.getId()).isEqualTo("AA5D0B000000");
        assertThat(oneWireDevice.getLocator()).isEqualTo("FF08FFFFFFFFFFFF");
        assertThat(oneWireDevice.getPresent()).isEqualTo("1");
        assertThat(oneWireDevice.getType()).isEqualTo("DS2423");
    }

    @Test(expected = OneWireException.class)
    public void readBaseParameterWithUnknownPath() throws Exception {
        OneWireServer oneWireServer = mock(OneWireServer.class);

        when(oneWireServer.read(any())).thenThrow(new OneWireException("error"));

        OneWireUniversalDevice oneWireDevice = new OneWireUniversalDevice(BASEPATH);
        oneWireDevice.readBaseParameter(oneWireServer);
    }

    @Test
    public void readSensorValuesWithLimitedRateToFast() throws Exception {
        OneWireUniversalDevice oneWireDevice = new OneWireUniversalDevice(BASEPATH);
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
        OneWireUniversalDevice oneWireDevice = new OneWireUniversalDevice(BASEPATH);
        sensorValuesRead = false;

        boolean executed = oneWireDevice.readSensorValuesWithLimitedRate(() -> {
            sensorValuesRead = true;
        });
        assertThat(executed).isTrue();
        assertThat(sensorValuesRead).isTrue();

        Thread.sleep(OneWireUniversalDevice.MINIMAL_READ_SENSOR_VALUES_INTERVAL);
        executed = oneWireDevice.readSensorValuesWithLimitedRate(() -> {
            sensorValuesRead = false;
        });
        assertThat(executed).isTrue();
        assertThat(sensorValuesRead).isFalse();
    }

    @Test
    public void testToString() throws Exception {
        OneWireServer oneWireServer = mock(OneWireServer.class);

        when(oneWireServer.read(BASEPATH + OneWireUniversalDevice.PATH_ADDRESS)).thenReturn("1DAA5D0B000000BD");
        when(oneWireServer.read(BASEPATH + OneWireUniversalDevice.PATH_CRC8)).thenReturn("BD");
        when(oneWireServer.read(BASEPATH + OneWireUniversalDevice.PATH_FAMILY)).thenReturn("1D");
        when(oneWireServer.read(BASEPATH + OneWireUniversalDevice.PATH_ID)).thenReturn("AA5D0B000000");
        when(oneWireServer.read(BASEPATH + OneWireUniversalDevice.PATH_LOCATOR)).thenReturn("FF08FFFFFFFFFFFF");
        when(oneWireServer.read(BASEPATH + OneWireUniversalDevice.PATH_PRESENT)).thenReturn("1");
        when(oneWireServer.read(BASEPATH + OneWireUniversalDevice.PATH_TYPE)).thenReturn("DS2423");

        OneWireUniversalDevice oneWireDevice = new OneWireUniversalDevice(BASEPATH);
        oneWireDevice.readBaseParameter(oneWireServer);

        String toString = oneWireDevice.toString();
        assertThat(toString).isEqualTo(
                "OneWireUniversalDevice{basePath='/1D.AA5D0B000000', address='1DAA5D0B000000BD', crc8='BD', family='1D', id='AA5D0B000000', locator='FF08FFFFFFFFFFFF', present='1', type='DS2423'}");
    }
}
