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

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Cacheable
public class Sensor extends AbstractPersistable<Long> {
    @NotNull
    @Column(unique = true, length = 64, nullable = false)
    private String name;
    @Column(length = 32)
    private String oneWireAddress;
    @Column(length = 32)
    private String oneWireFamily;
    @Column(length = 32)
    private String oneWireType;
    @Column(length = 32)
    private String oneWireSensorName;
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Server server;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOneWireAddress() {
        return oneWireAddress;
    }

    public void setOneWireAddress(String oneWireAddress) {
        this.oneWireAddress = oneWireAddress;
    }

    public String getOneWireFamily() {
        return oneWireFamily;
    }

    public void setOneWireFamily(String oneWireFamily) {
        this.oneWireFamily = oneWireFamily;
    }

    public String getOneWireType() {
        return oneWireType;
    }

    public void setOneWireType(String oneWireType) {
        this.oneWireType = oneWireType;
    }

    public String getOneWireSensorName() {
        return oneWireSensorName;
    }

    public void setOneWireSensorName(String oneWireSensorName) {
        this.oneWireSensorName = oneWireSensorName;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }
}
