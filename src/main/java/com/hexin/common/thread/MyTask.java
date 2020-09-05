package com.hexin.common.thread;

public class MyTask implements Runnable {

    private int taskNum;

    public MyTask(int num) {
        this.taskNum = num;
    }

    @Override
    public void run() {
        System.out.println("正在执行task:"+taskNum);
        try {
            Thread.currentThread().sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("task:"+taskNum+"执行完毕");
    }







//    ExecutorService executorService = Executors.newFixedThreadPool(4);
//    List<Future<Integer>> resultList = new ArrayList<Future<Integer>>();
//    CompletionService<Integer> pool = new ExecutorCompletionService<Integer>(executorService);
//    // 创建4个任务并执行
//            for (int i = 0; i < 4; i++) {
//        // 使用ExecutorService执行Callable类型的任务，并将结果保存在future变量中
//        Future<Integer> future = pool.submit(new TaskWithResult(this.rewardMessageService, queue));
//        // 将任务执行结果存储到List中
//        resultList.add(future);
//    }
//    // 遍历任务的结果
//            for (Future<Integer> fs : resultList) {
//        try {
//            Integer size = pool.take().get();
//            logger.info("线程处理数量分别为：{}", size);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } finally {
//            // 启动一次顺序关闭，执行以前提交的任务，但不接受新任务。如果已经关闭，则调用没有其他作用。
//            executorService.shutdown();
//        }
//    }

}
