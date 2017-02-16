package com.android_mobile.core.net;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPool {
    private static final int CORE_POOL_SIZE = 5;
    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            Thread t = new Thread(r, "thread #" + mCount.getAndIncrement());
            // t.setDaemon(true);
            t.setPriority(Thread.MIN_PRIORITY);
            return t;
        }
    };
    public static ExecutorService fixedPool = Executors.newFixedThreadPool(
            CORE_POOL_SIZE, sThreadFactory);


    public static ThreadPoolExecutor basicPool = new ThreadPoolExecutor(100, 2000, 5,
            TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(3000),
            new ThreadPoolExecutor.DiscardOldestPolicy());
}
