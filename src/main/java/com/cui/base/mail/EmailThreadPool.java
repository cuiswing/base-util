package com.cui.base.mail;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 邮件服务线程池
 *
 * @author CUI
 * @since 2021-03-30
 */
public class EmailThreadPool {

    private static ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("email-pool-thread-%d").build();
    private static BlockingQueue blockingQueue = new ArrayBlockingQueue(1000);
    // 使用单线程来发送邮件，避免并发过大网易邮箱拒绝服务
    public static final ExecutorService executorService =
            new ThreadPoolExecutor(
                    1, 1,
                    0, TimeUnit.MILLISECONDS,
                    blockingQueue,
                    threadFactory,
                    new ThreadPoolExecutor.AbortPolicy());


}
