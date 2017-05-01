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

package de.jgard.onewire.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

public class ServerTest {
    private Server server;

    @Before
    public void setup() {
        server = new Server();
    }

    @Test
    public void getSetName() throws Exception {
        server.setName("name");

        assertThat(server.getName()).isEqualTo("name");
    }

    @Test
    public void getSetHostname() throws Exception {
        server.setHostname("hostname");

        assertThat(server.getHostname()).isEqualTo("hostname");
    }

    @Test
    public void getSetPortNunmber() throws Exception {
        server.setPortNumber(4304);

        assertThat(server.getPortNumber()).isEqualTo(4304);
    }
}
