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
public class OneWireDeviceDS2423Tests {
    private static final String BASEPATH = "/1D.AA5D0B000000";
    private OwfsConnection owfsConnection;

    @Before
    public void setup() {
        owfsConnection = mock(OwfsConnection.class);
    }

    @Test
    public void readBaseParameter() throws Exception {
        when(owfsConnection.read(BASEPATH + OneWireDevice.PATH_FAMILY)).thenReturn("1D");
        when(owfsConnection.read(BASEPATH + OneWireDevice.PATH_TYPE)).thenReturn("DS2423");

        OneWireDeviceDS2423 oneWireDeviceDS2423 = new OneWireDeviceDS2423(BASEPATH);
        oneWireDeviceDS2423.readBaseParameter(owfsConnection);

        assertThat(oneWireDeviceDS2423.getFamily()).isEqualTo("1D");
        assertThat(oneWireDeviceDS2423.getType()).isEqualTo("DS2423");
    }

    @Test(expected = IllegalStateException.class)
    public void readBaseParameterWrongFamily() throws Exception {
        when(owfsConnection.read(BASEPATH + OneWireDevice.PATH_FAMILY)).thenReturn("1F");
        when(owfsConnection.read(BASEPATH + OneWireDevice.PATH_TYPE)).thenReturn("DS2423");

        OneWireDeviceDS2423 oneWireDeviceDS2423 = new OneWireDeviceDS2423(BASEPATH);
        oneWireDeviceDS2423.readBaseParameter(owfsConnection);
    }

    @Test(expected = IllegalStateException.class)
    public void readBaseParameterWrongType() throws Exception {
        when(owfsConnection.read(BASEPATH + OneWireDevice.PATH_FAMILY)).thenReturn("1D");
        when(owfsConnection.read(BASEPATH + OneWireDevice.PATH_TYPE)).thenReturn("DS2422");

        OneWireDeviceDS2423 oneWireDeviceDS2423 = new OneWireDeviceDS2423(BASEPATH);
        oneWireDeviceDS2423.readBaseParameter(owfsConnection);
    }

    @Test
    public void readSensorValues() throws Exception {
        when(owfsConnection.read(BASEPATH + OneWireDeviceDS2423.PATH_COUNTER_A)).thenReturn("42");
        when(owfsConnection.read(BASEPATH + OneWireDeviceDS2423.PATH_COUNTER_B)).thenReturn("21");

        OneWireDeviceDS2423 oneWireDeviceDS2423 = new OneWireDeviceDS2423(BASEPATH);
        boolean valuesRead = oneWireDeviceDS2423.readSensorValues(owfsConnection);

        assertThat(valuesRead).isTrue();
        assertThat(oneWireDeviceDS2423.getCounterA()).isEqualTo(42L);
        assertThat(oneWireDeviceDS2423.getCounterB()).isEqualTo(21L);
    }

    @Test(expected = OneWireException.class)
    public void readSensorValuesInvalidValue() throws Exception {
        when(owfsConnection.read(BASEPATH + OneWireDeviceDS2423.PATH_COUNTER_A)).thenReturn("42x");
        when(owfsConnection.read(BASEPATH + OneWireDeviceDS2423.PATH_COUNTER_B)).thenReturn("21x");

        OneWireDeviceDS2423 oneWireDeviceDS2423 = new OneWireDeviceDS2423(BASEPATH);
        oneWireDeviceDS2423.readSensorValues(owfsConnection);
    }

    @Test(expected = OneWireException.class)
    public void readSensorValuesReadError() throws Exception {
        when(owfsConnection.read(BASEPATH + OneWireDeviceDS2423.PATH_COUNTER_A)).thenThrow(OwfsException.class);

        OneWireDeviceDS2423 oneWireDeviceDS2423 = new OneWireDeviceDS2423(BASEPATH);
        oneWireDeviceDS2423.readSensorValues(owfsConnection);
    }

    @Test
    public void readSensorValuesToFast() throws Exception {
        when(owfsConnection.read(BASEPATH + OneWireDeviceDS2423.PATH_COUNTER_A)).thenReturn("42");
        when(owfsConnection.read(BASEPATH + OneWireDeviceDS2423.PATH_COUNTER_B)).thenReturn("21");

        OneWireDeviceDS2423 oneWireDeviceDS2423 = new OneWireDeviceDS2423(BASEPATH);
        boolean valuesRead = oneWireDeviceDS2423.readSensorValues(owfsConnection);

        assertThat(valuesRead).isTrue();
        assertThat(oneWireDeviceDS2423.getCounterA()).isEqualTo(42L);
        assertThat(oneWireDeviceDS2423.getCounterB()).isEqualTo(21L);

        when(owfsConnection.read(BASEPATH + OneWireDeviceDS2423.PATH_COUNTER_A)).thenReturn("43");
        when(owfsConnection.read(BASEPATH + OneWireDeviceDS2423.PATH_COUNTER_B)).thenReturn("22");

        valuesRead = oneWireDeviceDS2423.readSensorValues(owfsConnection);

        assertThat(valuesRead).isFalse();
        assertThat(oneWireDeviceDS2423.getCounterA()).isEqualTo(42L);
        assertThat(oneWireDeviceDS2423.getCounterB()).isEqualTo(21L);
    }

    @Test
    public void readSensorValuesWithMaximumRate() throws Exception {
        when(owfsConnection.read(BASEPATH + OneWireDeviceDS2423.PATH_COUNTER_A)).thenReturn("42");
        when(owfsConnection.read(BASEPATH + OneWireDeviceDS2423.PATH_COUNTER_B)).thenReturn("21");

        OneWireDeviceDS2423 oneWireDeviceDS2423 = new OneWireDeviceDS2423(BASEPATH);
        boolean valuesRead = oneWireDeviceDS2423.readSensorValues(owfsConnection);

        assertThat(valuesRead).isTrue();
        assertThat(oneWireDeviceDS2423.getCounterA()).isEqualTo(42L);
        assertThat(oneWireDeviceDS2423.getCounterB()).isEqualTo(21L);

        when(owfsConnection.read(BASEPATH + OneWireDeviceDS2423.PATH_COUNTER_A)).thenReturn("43");
        when(owfsConnection.read(BASEPATH + OneWireDeviceDS2423.PATH_COUNTER_B)).thenReturn("22");

        Thread.sleep(OneWireDevice.MINIMAL_READ_SENSOR_VALUES_INTERVAL);
        valuesRead = oneWireDeviceDS2423.readSensorValues(owfsConnection);

        assertThat(valuesRead).isTrue();
        assertThat(oneWireDeviceDS2423.getCounterA()).isEqualTo(43L);
        assertThat(oneWireDeviceDS2423.getCounterB()).isEqualTo(22L);
    }

    @Test
    public void readUncachedSensorValues() throws Exception {
        when(owfsConnection.read(OneWireDevice.PATH_UNCACHED + BASEPATH + OneWireDeviceDS2423.PATH_COUNTER_A))
                .thenReturn("42");
        when(owfsConnection.read(OneWireDevice.PATH_UNCACHED + BASEPATH + OneWireDeviceDS2423.PATH_COUNTER_B))
                .thenReturn("21");

        OneWireDeviceDS2423 oneWireDeviceDS2423 = new OneWireDeviceDS2423(BASEPATH);
        boolean valuesRead = oneWireDeviceDS2423.readUncachedSensorValues(owfsConnection);

        assertThat(valuesRead).isTrue();
        assertThat(oneWireDeviceDS2423.getCounterA()).isEqualTo(42L);
        assertThat(oneWireDeviceDS2423.getCounterB()).isEqualTo(21L);
    }
}
