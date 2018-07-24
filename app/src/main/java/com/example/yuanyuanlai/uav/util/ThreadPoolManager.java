package com.example.yuanyuanlai.uav.util;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author: Gong Yunhao
 * @version: V1.0
 * @date: 2018/7/20
 * @github https://github.com/GongYunHaoyyy
 * @blog https://www.jianshu.com/u/52a8fa1f29fb
 */
public class ThreadPoolManager {
    private static final int CORE_POOL_SIZE = 3;
    private static final int MAX_POOL_SIZE = 12;

    private ThreadPoolManager() {}
    private static volatile ThreadPoolManager threadPoolManager;

    public static ThreadPoolManager getInstance(){
        if (threadPoolManager == null){
            synchronized (ThreadPoolManager.class){
                if (threadPoolManager == null){
                    threadPoolManager = new ThreadPoolManager();
                }
            }
        }
        return threadPoolManager;
    }

    /**
     * 线程池
     * @param corePoolSize - 池中所保存的线程数，包括空闲线程。
     * @param maximumPoolSize - 池中允许的最大线程数。
     * @param keepAliveTime - 当线程数大于核心时，此为终止前多余的空闲线程等待新任务的最长时间。
     * @param unit - keepAliveTime 参数的时间单位。
     * @param workQueue - 执行前用于保持任务的队列。此队列仅由保持 execute 方法提交的 Runnable 任务。
     * @param handler - 由于超出线程范围和队列容量而使执行被阻塞时所使用的处理程序。
     */
    private final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE,
            0L, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());

    /**
     * 向线程池中添加任务方法
     */
    public void addExecuteTask(Runnable task) {
        if (task != null) {
            threadPoolExecutor.execute(task);
        }
    }

    /**
     * 判断是否是最后一个任务
     */
    protected boolean isTaskEnd() {
        if (threadPoolExecutor.getActiveCount() == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取缓存大小
     */
    public int getQueue(){
        return threadPoolExecutor.getQueue().size();
    }

    /**
     * 获取线程池中的线程数目
     */
    public int getPoolSize(){
        return threadPoolExecutor.getPoolSize();
    }

    /**
     * 获取已完成的任务数
     */
    public long getCompletedTaskCount(){
        return threadPoolExecutor.getCompletedTaskCount();
    }

    /**
     * 关闭线程池，不在接受新的任务，会把已接受的任务执行玩
     */
    public void shutdown() {
        threadPoolExecutor.shutdown();
    }

}
