package cn.pengshao.common.thread;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @Author: yezp
 * @date 2024/7/7 7:45
 */
public class SchedulerImpl implements Scheduler {

    boolean initialized = false;
    ScheduledExecutorService executor;

    public SchedulerImpl() {
    }

    public void init(int coreSize) {
        this.executor = Executors.newScheduledThreadPool(coreSize);
        this.initialized = true;
    }

    @Override
    public boolean isInitialized() {
        return this.initialized;
    }

    @Override
    public void shutdown() {
        this.executor.shutdown();

        try {
            this.executor.awaitTermination(1L, TimeUnit.SECONDS);
            if (!this.executor.isTerminated()) {
                this.executor.shutdownNow();
            }
        } catch (InterruptedException exception) {
        }
    }

    @Override
    public ScheduledFuture<?> schedule(Runnable runnable, long delay, long interval) {
        return this.executor.scheduleWithFixedDelay(runnable, delay, interval, TimeUnit.MILLISECONDS);
    }
}
