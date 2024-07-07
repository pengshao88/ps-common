package cn.pengshao.common.thread;

/**
 * Description:
 *
 * @Author: yezp
 * @date 2024/7/7 7:43
 */
public interface ThreadUtils {

    Scheduler Default = new SchedulerImpl();

    static Scheduler getDefault() {
        if (!Default.isInitialized()) {
            int coreSize = Integer.parseInt(System.getProperty("utils.task.coreSize", "1"));
            Default.init(coreSize);
        }
        return Default;
    }

}
