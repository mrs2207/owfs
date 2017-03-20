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

import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;

import static org.assertj.core.api.Assertions.assertThat;

public class SensorValueTest {
    private SensorValue sensorValue;

    @Before
    public void setup() {
        sensorValue = new SensorValue();
    }

    @Test
    public void getSetReading() throws Exception {
        sensorValue.setReading(42L);

        assertThat(sensorValue.getReading()).isEqualTo(42L);
    }

    @Test
    public void getSetTimeOfMeasurement() throws Exception {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        sensorValue.setTimeOfMeasurement(currentTime);

        assertThat(sensorValue.getTimeOfMeasurement()).isEqualTo(currentTime);
    }
}