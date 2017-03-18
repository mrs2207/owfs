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


public class SensorTest {
    private Sensor sensor;

    @Before
    public void setup() {
        sensor = new Sensor();
    }

    @Test
    public void getSetName() throws Exception {
        sensor.setName("name");

        assertThat(sensor.getName()).isEqualTo("name");
    }

    @Test
    public void getSetOneWireAddress() throws Exception {
        sensor.setOneWireAddress("address");

        assertThat(sensor.getOneWireAddress()).isEqualTo("address");
    }

    @Test
    public void getOneWireFamily() throws Exception {
        sensor.setOneWireFamily("family");

        assertThat(sensor.getOneWireFamily()).isEqualTo("family");
    }

    @Test
    public void getSetOneWireType() throws Exception {
        sensor.setOneWireType("type");

        assertThat(sensor.getOneWireType()).isEqualTo("type");
    }

    @Test
    public void getSetOneWireSensorName() throws Exception {
        sensor.setOneWireSensorName("sensorName");

        assertThat(sensor.getOneWireSensorName()).isEqualTo("sensorName");
    }

    @Test
    public void getSetId() throws Exception {
        sensor.setId(Long.valueOf(42L));

        assertThat(sensor.getId()).isEqualTo(42L);
    }
}