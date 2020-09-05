package com.hexin.common.thread;


import com.hexin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.*;

/**
 * @ClassName ConcurrentTest
 * @Description 线程池执行，Future 获取执行结果
 * @Author shenxiaojie
 * @Date 2019-05-17 11:42
 * @Version 1.0
 */
public class ConcurrentTest {

    @Autowired
    private UserService userService;


    static class TestJob implements Callable<String> {

        private String name;

        private Integer sleepTimes;

        public TestJob(String name, Integer sleepTimes) {
            this.name = name;
            this.sleepTimes = sleepTimes;
        }

        public String call() throws Exception {
            // 假设这是一个比较耗时的操作
            Thread.sleep(sleepTimes * 1000);
            // Future 获取返回结果
            return "this is content : hello " + this.name;
        }

    }

    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                10,  //核心池的大小（即线程池中的线程数目大于这个参数时，提交的任务会被放进任务缓存队列）
                10, //线程池最大能容忍的线程数
                200, //线程存活时间
                TimeUnit.MILLISECONDS, //参数keepAliveTime的时间单位
                new ArrayBlockingQueue<Runnable>(5) //任务缓存队列，用来存放等待执行的任务
        );

        CompletionService<String> completionService = new ExecutorCompletionService(executor);
        // 十个
        long startTime = System.currentTimeMillis();
        int count = 0;
        for (int i = 0; i < 10; i++) {
            count++;
            TestJob getContentTask = new TestJob("micro" + i, 5);
            completionService.submit(getContentTask);
        }
        System.out.println("提交完任务，主线程空闲了, 可以去做一些事情。");
        // 假装做了8秒种其他事情
        try {
            Thread.sleep(3000);
            System.out.println("主线程做完了，等待结果");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            // 做完事情要结果
            for (int i = 0; i < count; i++) {
                Future<String> result = completionService.take();
                System.out.println(result.get());
            }
            long endTime = System.currentTimeMillis();
            System.out.println("耗时 : " + (endTime - startTime) / 1000);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        executor.shutdown();
    }
}
