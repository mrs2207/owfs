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

package de.jgard.onewire;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OneWireExceptionTest {
    @Test
    public void creationWithMessage() throws Exception {
        OneWireException oneWireException = new OneWireException("Test");

        assertThat(oneWireException.getMessage()).isEqualTo("Test");
    }

    @Test
    public void creationWithMessageandException() throws Exception {
        Exception exception = new IllegalArgumentException();
        OneWireException oneWireException = new OneWireException("Test", exception);

        assertThat(oneWireException.getMessage()).isEqualTo("Test");
        assertThat(oneWireException.getCause()).isEqualTo(exception);
    }
}