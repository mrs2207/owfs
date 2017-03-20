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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OneWireDeviceFactoryTest {
    @Test
    public void getBiCounterDevice() throws Exception {
        OneWireDeviceFactory oneWireDeviceFactory = new OneWireDeviceFactory();
        OwfsConnection owfsConnection = mock(OwfsConnection.class);
        String basePath = "/1D.AA5D0B000000";

        when(owfsConnection.read(basePath + OneWireUniversalDevice.PATH_TYPE)).thenReturn(OneWireDS2423Device.ONE_WIRE_TYPE);
        when(owfsConnection.read(basePath + OneWireUniversalDevice.PATH_FAMILY)).thenReturn(OneWireDS2423Device.ONE_WIRE_FAMILY);

        BiCounterDevice biCounterDevice = oneWireDeviceFactory.getBiCounterDevice(owfsConnection, basePath );
        assertThat(biCounterDevice).isNotNull();
    }

    @Test(expected = OneWireException.class)
    public void getBiCounterDeviceWrongType() throws Exception {
        OneWireDeviceFactory oneWireDeviceFactory = new OneWireDeviceFactory();
        OwfsConnection owfsConnection = mock(OwfsConnection.class);
        String basePath = "/1D.AA5D0B000000";

        when(owfsConnection.read(basePath + OneWireUniversalDevice.PATH_TYPE)).thenReturn("DS4242");
        when(owfsConnection.read(basePath + OneWireUniversalDevice.PATH_FAMILY)).thenReturn(OneWireDS2423Device.ONE_WIRE_FAMILY);

        oneWireDeviceFactory.getBiCounterDevice(owfsConnection, basePath );
    }

    @Test(expected = OneWireException.class)
    public void getBiCounterDeviceWrongFamily() throws Exception {
        OneWireDeviceFactory oneWireDeviceFactory = new OneWireDeviceFactory();
        OwfsConnection owfsConnection = mock(OwfsConnection.class);
        String basePath = "/1D.AA5D0B000000";

        when(owfsConnection.read(basePath + OneWireUniversalDevice.PATH_TYPE)).thenReturn(OneWireDS2423Device.ONE_WIRE_TYPE);
        when(owfsConnection.read(basePath + OneWireUniversalDevice.PATH_FAMILY)).thenReturn("42");

        oneWireDeviceFactory.getBiCounterDevice(owfsConnection, basePath );
    }

    @Test(expected = OneWireException.class)
    public void getBiCounterDeviceUnkownPath() throws Exception {
        OneWireDeviceFactory oneWireDeviceFactory = new OneWireDeviceFactory();
        OwfsConnection owfsConnection = mock(OwfsConnection.class);
        String basePath = "/1D.AA5D0B000000";

        when(owfsConnection.read(any())).thenThrow(new OneWireException("error"));

        oneWireDeviceFactory.getBiCounterDevice(owfsConnection, basePath );
    }
}