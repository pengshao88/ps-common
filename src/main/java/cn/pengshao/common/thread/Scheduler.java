package cn.pengshao.common.thread;

import java.util.concurrent.ScheduledFuture;

/**
 * Description:
 *
 * @Author: yezp
 * @date 2024/7/7 7:44
 */
public interface Scheduler {

    boolean isInitialized();

    void init(int coreSize);

    void shutdown();

    ScheduledFuture<?> schedule(Runnable var1, long delay, long interval);

}
