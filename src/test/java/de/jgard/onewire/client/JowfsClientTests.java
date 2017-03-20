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

package de.jgard.onewire.client;

import org.junit.Test;
import org.owfs.jowfsclient.OwfsConnection;
import org.owfs.jowfsclient.OwfsConnectionFactory;
import org.owfs.jowfsclient.OwfsException;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.ConnectException;
import java.util.List;

@SpringBootTest
public class JowfsClientTests {
    @Test
    public void testConnectToOwfsServer() throws Exception {
        OwfsConnection owfsConnection = getOwfsConnection();
        owfsConnection.exists("/");
        owfsConnection.disconnect();
    }

    @Test(expected = ConnectException.class)
    public void testFailedConnectToOwfsServer() throws Exception {
        OwfsConnectionFactory owfsConnectorFactory = new OwfsConnectionFactory("jupiter", 4303);

        OwfsConnection owfsConnection = owfsConnectorFactory.createNewConnection();
        owfsConnection.exists("/");
        owfsConnection.disconnect();
    }

    @Test
    public void testSuccessfullyRead() throws Exception {
        OwfsConnection owfsConnection = getOwfsConnection();

        owfsConnection.read("/settings/units/temperature_scale");
        owfsConnection.disconnect();
    }

    @Test
    public void testListDirectory() throws Exception {
        OwfsConnection owfsConnection = getOwfsConnection();

        System.out.println( owfsConnection.read("/1D.AA5D0B000000/address") );
        System.out.println( owfsConnection.read("/1D.AA5D0B000000/crc8") );
        listDir(owfsConnection, "/");
        owfsConnection.disconnect();
    }

    @Test(expected = OwfsException.class)
    public void testOwfsExceptionIfOwServerError() throws Exception {
        OwfsConnection owfsConnection = getOwfsConnection();
        owfsConnection.read("/xy42");
        owfsConnection.disconnect();
    }

    private OwfsConnection getOwfsConnection() {
        OwfsConnectionFactory owfsConnectorFactory = new OwfsConnectionFactory("jupiter", 4304);

        return owfsConnectorFactory.createNewConnection();
    }

    private void listDir(OwfsConnection owfsConnection, String dir) throws IOException, OwfsException {
        List<String> entries = owfsConnection.listDirectory(dir);
        for (String entry : entries) {
            System.out.println(entry);
        }
    }
}
