/*
 * Copyright 2015 devbury LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package devbury.threadscope;

import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ThreadScopeStateTest implements ObjectFactory<ThreadScopeStateTest> {

    boolean destructionCallbackCalled = false;

    @Test
    void testFinalize() throws Throwable {
        ThreadScopeState victim = new ThreadScopeState();
        victim.addDestructionCallback("bean", new Runnable() {
            @Override
            public void run() {
                destructionCallbackCalled = true;
            }
        });
        victim.finalize();

        assertTrue(destructionCallbackCalled);
    }

    @Test
    public void testBeansSize() {
        ThreadScopeState victim = new ThreadScopeState();
        assertEquals(0, victim.size());
        victim.addBean("bean", this);
        assertEquals(1, victim.size());
        assertEquals(this, victim.getBean("bean"));
        assertEquals(1, victim.size());
        victim.removeBean("bean");
        assertEquals(0, victim.size());
    }

    @Override
    public ThreadScopeStateTest getObject() throws BeansException {
        return this;
    }
}