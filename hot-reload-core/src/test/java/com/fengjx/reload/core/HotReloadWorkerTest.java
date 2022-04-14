package com.fengjx.reload.core;

import net.bytebuddy.agent.ByteBuddyAgent;
import org.junit.Test;

import java.lang.instrument.Instrumentation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class HotReloadWorkerTest {

    @Test
    public void loop() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            Thread.sleep(1000);
            invoke();
        }
    }

    void invoke() {
        System.out.println("lzy");
    }

    @Test
    public void findStaticInnerClass() throws Exception {
        DummyStaticInnerService dummyService = new DummyStaticInnerService();
        Instrumentation instrumentation = ByteBuddyAgent.install();
        Class<?> targetClass = HotReloadWorker.findTargetClass("com.fengjx.reload.core.HotReloadWorkerTest$DummyStaticInnerService", instrumentation);
        assertNotNull(targetClass);
        assertEquals(targetClass, dummyService.getClass());
    }

    @Test
    public void findStaticClass() {
        DummyStaticOuterService dummyService = new DummyStaticOuterService();
        Instrumentation instrumentation = ByteBuddyAgent.install();
        Class<?> targetClass = HotReloadWorker.findTargetClass("com.fengjx.reload.core.DummyStaticOuterService", instrumentation);
        assertNotNull(targetClass);
        assertEquals(targetClass, dummyService.getClass());
    }

    static class DummyStaticInnerService {
        public String foo() {
            return "foo";
        }
    }
}

class DummyStaticOuterService {
    public String foo() {
        return "foo";
    }
}
