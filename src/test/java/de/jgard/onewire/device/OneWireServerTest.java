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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Test;
import org.owfs.jowfsclient.OwfsConnection;

import de.jgard.onewire.OneWireException;

public class OneWireServerTest {
    @Test
    public void read() throws Exception {
        OwfsConnection owfsConnection = mock(OwfsConnection.class);
        when(owfsConnection.read("/test")).thenReturn("read");

        OneWireServer oneWireServer = new OneWireServer(owfsConnection);

        assertThat(oneWireServer.read("/test")).isEqualTo("read");
    }

    @Test(expected = OneWireException.class)
    public void readWithIOException() throws Exception {
        OwfsConnection owfsConnection = mock(OwfsConnection.class);
        when(owfsConnection.read("/test")).thenThrow(IOException.class);

        OneWireServer oneWireServer = new OneWireServer(owfsConnection);

        assertThat(oneWireServer.read("/test")).isEqualTo("read");
    }
}