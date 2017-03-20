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
import org.owfs.jowfsclient.OwfsConnectionFactory;
import org.owfs.jowfsclient.OwfsException;

import java.io.IOException;

public class OneWireServer {
    private final String hostname;
    private final int portNumber;
    private final OwfsConnection owfsConnection;

    public OneWireServer(String hostname, int portNumber) {
        this.hostname = hostname;
        this.portNumber = portNumber;

        this.owfsConnection = createOwfsConnection();
    }

    public String read(String path) throws OneWireException {
        try {
            return owfsConnection.read(path);
        } catch (IOException | OwfsException e) {
            throw new OneWireException("Can't read from path '" + path + "'.", e);
        }
    }

    @Override
    public String toString() {
        return "OneWireServer{" +
                "hostname='" + hostname + '\'' +
                ", portNumber=" + portNumber +
                '}';
    }

    private OwfsConnection createOwfsConnection() {
        OwfsConnectionFactory owfsConnectorFactory = new OwfsConnectionFactory(hostname, portNumber);

        return owfsConnectorFactory.createNewConnection();
    }
}
