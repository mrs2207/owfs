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
import org.junit.Before;
import org.junit.Test;
import org.owfs.jowfsclient.OwfsConnection;
import org.owfs.jowfsclient.OwfsException;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class OneWireDS2423DeviceTests {
    private static final String BASEPATH = "/1D.AA5D0B000000";
    private OwfsConnection owfsConnection;

    @Before
    public void setup() {
        owfsConnection = mock(OwfsConnection.class);
    }

    @Test
    public void readBaseParameter() throws Exception {
        when(owfsConnection.read(BASEPATH + OneWireUniversalDevice.PATH_FAMILY)).thenReturn("1D");
        when(owfsConnection.read(BASEPATH + OneWireUniversalDevice.PATH_TYPE)).thenReturn("DS2423");

        OneWireDS2423Device oneWireDS2423Device = new OneWireDS2423Device(BASEPATH);
        oneWireDS2423Device.readBaseParameter(owfsConnection);

        assertThat(oneWireDS2423Device.getFamily()).isEqualTo("1D");
        assertThat(oneWireDS2423Device.getType()).isEqualTo("DS2423");
    }

    @Test(expected = OneWireException.class)
    public void readBaseParameterWrongFamily() throws Exception {
        when(owfsConnection.read(BASEPATH + OneWireUniversalDevice.PATH_FAMILY)).thenReturn("1F");
        when(owfsConnection.read(BASEPATH + OneWireUniversalDevice.PATH_TYPE)).thenReturn("DS2423");

        OneWireDS2423Device oneWireDS2423Device = new OneWireDS2423Device(BASEPATH);
        oneWireDS2423Device.readBaseParameter(owfsConnection);
    }

    @Test(expected = OneWireException.class)
    public void readBaseParameterWrongType() throws Exception {
        when(owfsConnection.read(BASEPATH + OneWireUniversalDevice.PATH_FAMILY)).thenReturn("1D");
        when(owfsConnection.read(BASEPATH + OneWireUniversalDevice.PATH_TYPE)).thenReturn("DS2422");

        OneWireDS2423Device oneWireDS2423Device = new OneWireDS2423Device(BASEPATH);
        oneWireDS2423Device.readBaseParameter(owfsConnection);
    }

    @Test
    public void readSensorValues() throws Exception {
        when(owfsConnection.read(BASEPATH + OneWireDS2423Device.PATH_COUNTER_A)).thenReturn("42");
        when(owfsConnection.read(BASEPATH + OneWireDS2423Device.PATH_COUNTER_B)).thenReturn("21");

        OneWireDS2423Device oneWireDS2423Device = new OneWireDS2423Device(BASEPATH);
        boolean valuesRead = oneWireDS2423Device.readSensorValues(owfsConnection);

        assertThat(valuesRead).isTrue();
        assertThat(oneWireDS2423Device.getCounterA()).isEqualTo(42L);
        assertThat(oneWireDS2423Device.getCounterB()).isEqualTo(21L);
    }

    @Test(expected = OneWireException.class)
    public void readSensorValuesInvalidValue() throws Exception {
        when(owfsConnection.read(BASEPATH + OneWireDS2423Device.PATH_COUNTER_A)).thenReturn("42x");
        when(owfsConnection.read(BASEPATH + OneWireDS2423Device.PATH_COUNTER_B)).thenReturn("21x");

        OneWireDS2423Device oneWireDS2423Device = new OneWireDS2423Device(BASEPATH);
        oneWireDS2423Device.readSensorValues(owfsConnection);
    }

    @Test(expected = OneWireException.class)
    public void readSensorValuesReadError() throws Exception {
        when(owfsConnection.read(BASEPATH + OneWireDS2423Device.PATH_COUNTER_A))
                .thenThrow(new OwfsException("error", 1));

        OneWireDS2423Device oneWireDS2423Device = new OneWireDS2423Device(BASEPATH);
        oneWireDS2423Device.readSensorValues(owfsConnection);
    }

    @Test
    public void readSensorValuesToFast() throws Exception {
        when(owfsConnection.read(BASEPATH + OneWireDS2423Device.PATH_COUNTER_A)).thenReturn("42");
        when(owfsConnection.read(BASEPATH + OneWireDS2423Device.PATH_COUNTER_B)).thenReturn("21");

        OneWireDS2423Device oneWireDS2423Device = new OneWireDS2423Device(BASEPATH);
        boolean valuesRead = oneWireDS2423Device.readSensorValues(owfsConnection);

        assertThat(valuesRead).isTrue();
        assertThat(oneWireDS2423Device.getCounterA()).isEqualTo(42L);
        assertThat(oneWireDS2423Device.getCounterB()).isEqualTo(21L);

        when(owfsConnection.read(BASEPATH + OneWireDS2423Device.PATH_COUNTER_A)).thenReturn("43");
        when(owfsConnection.read(BASEPATH + OneWireDS2423Device.PATH_COUNTER_B)).thenReturn("22");

        valuesRead = oneWireDS2423Device.readSensorValues(owfsConnection);

        assertThat(valuesRead).isFalse();
        assertThat(oneWireDS2423Device.getCounterA()).isEqualTo(42L);
        assertThat(oneWireDS2423Device.getCounterB()).isEqualTo(21L);
    }

    @Test
    public void readSensorValuesWithMaximumRate() throws Exception {
        when(owfsConnection.read(BASEPATH + OneWireDS2423Device.PATH_COUNTER_A)).thenReturn("42");
        when(owfsConnection.read(BASEPATH + OneWireDS2423Device.PATH_COUNTER_B)).thenReturn("21");

        OneWireDS2423Device oneWireDS2423Device = new OneWireDS2423Device(BASEPATH);
        boolean valuesRead = oneWireDS2423Device.readSensorValues(owfsConnection);

        assertThat(valuesRead).isTrue();
        assertThat(oneWireDS2423Device.getCounterA()).isEqualTo(42L);
        assertThat(oneWireDS2423Device.getCounterB()).isEqualTo(21L);

        when(owfsConnection.read(BASEPATH + OneWireDS2423Device.PATH_COUNTER_A)).thenReturn("43");
        when(owfsConnection.read(BASEPATH + OneWireDS2423Device.PATH_COUNTER_B)).thenReturn("22");

        Thread.sleep(OneWireUniversalDevice.MINIMAL_READ_SENSOR_VALUES_INTERVAL);
        valuesRead = oneWireDS2423Device.readSensorValues(owfsConnection);

        assertThat(valuesRead).isTrue();
        assertThat(oneWireDS2423Device.getCounterA()).isEqualTo(43L);
        assertThat(oneWireDS2423Device.getCounterB()).isEqualTo(22L);
    }

    @Test
    public void readUncachedSensorValues() throws Exception {
        when(owfsConnection.read(OneWireUniversalDevice.PATH_UNCACHED + BASEPATH + OneWireDS2423Device.PATH_COUNTER_A))
                .thenReturn("42");
        when(owfsConnection.read(OneWireUniversalDevice.PATH_UNCACHED + BASEPATH + OneWireDS2423Device.PATH_COUNTER_B))
                .thenReturn("21");

        OneWireDS2423Device oneWireDS2423Device = new OneWireDS2423Device(BASEPATH);
        boolean valuesRead = oneWireDS2423Device.readUncachedSensorValues(owfsConnection);

        assertThat(valuesRead).isTrue();
        assertThat(oneWireDS2423Device.getCounterA()).isEqualTo(42L);
        assertThat(oneWireDS2423Device.getCounterB()).isEqualTo(21L);
    }
}
