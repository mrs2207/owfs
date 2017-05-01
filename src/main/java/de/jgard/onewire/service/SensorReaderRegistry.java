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

package de.jgard.onewire.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.jgard.onewire.model.Sensor;
import de.jgard.onewire.model.SensorRepository;

@Service
public class SensorReaderRegistry {
    private final SensorReaderFactory sensorReaderFactory;
    private final SensorRepository sensorRepository;
    private final List<SensorReader> sensorReaders;

    @Autowired
    public SensorReaderRegistry(SensorReaderFactory sensorReaderFactory, SensorRepository sensorRepository) {
        this.sensorReaderFactory = sensorReaderFactory;
        this.sensorRepository = sensorRepository;

        sensorReaders = new ArrayList<>();
    }

    public void buildSensorReaderRegistry() {
        sensorReaders.clear();

        for (Sensor sensor : sensorRepository.findAll()) {
            sensorReaders.add(sensorReaderFactory.getSensorReader(sensor));
        }
    }

    public List<SensorReader> getSensorReaders() {
        return Collections.unmodifiableList(sensorReaders);
    }
}
