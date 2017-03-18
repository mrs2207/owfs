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

package de.jgard.onewire.util;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class LimitedRateExecutorTests {
    private final static long MINIMAL_EXECUTION_INTERVAL = 100;

    private boolean runnableExecuted;

    @Before
    public void setup() {
        runnableExecuted = false;
    }

    @Test
    public void testExecuteWithLimitedRateToFast() throws Exception {
        LimitedRateExecutor limitedRateExecutor = new LimitedRateExecutor(MINIMAL_EXECUTION_INTERVAL);

        boolean executed = limitedRateExecutor.executeWithLimitedRate(() -> {
            runnableExecuted = true;
        });
        assertThat(executed).isTrue();
        assertThat(runnableExecuted).isTrue();

        executed = limitedRateExecutor.executeWithLimitedRate(() -> {
            runnableExecuted = false;
        });
        assertThat(executed).isFalse();
        assertThat(runnableExecuted).isTrue();
    }

    @Test
    public void testExecuteWithLimitedRate() throws Exception {
        LimitedRateExecutor limitedRateExecutor = new LimitedRateExecutor(MINIMAL_EXECUTION_INTERVAL);

        boolean executed = limitedRateExecutor.executeWithLimitedRate(() -> {
            runnableExecuted = true;
        });
        assertThat(executed).isTrue();
        assertThat(runnableExecuted).isTrue();

        Thread.sleep(MINIMAL_EXECUTION_INTERVAL);
        executed = limitedRateExecutor.executeWithLimitedRate(() -> {
            runnableExecuted = false;
        });
        assertThat(executed).isTrue();
        assertThat(runnableExecuted).isFalse();
    }
}
