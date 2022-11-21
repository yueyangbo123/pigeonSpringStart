package com.yyb.framework.pigeon.service;

import com.yyb.framework.pigeon.task.PullTask;
import com.yyb.framework.pigeon.task.PushTask;
import org.springframework.stereotype.Service;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author YueYB
 * @version 1.0
 * @date 2022/11/22
 * @description
 */

@Service
public class LongPollingServiceImpl implements LongPollingService {

    private Map<String, String> dataStage = new HashMap<>();

    final ScheduledExecutorService scheduler;

    final Queue<PullTask> nacosPullTasks;

    public LongPollingServiceImpl() {
        scheduler = new ScheduledThreadPoolExecutor(1, r -> {
            Thread t = new Thread(r);
            t.setName("NacosLongPollingTask");
            t.setDaemon(true);
            return t;
        });
        nacosPullTasks = new ConcurrentLinkedQueue<>();
        scheduler.scheduleAtFixedRate(() -> System.out.println("线程存活状态:" + new Date()), 0L, 5, TimeUnit.SECONDS);
    }


    @Override
    public void doGet(String dataId, HttpServletRequest req, HttpServletResponse resp) {
        // 一定要由当前HTTP线程调用，如果放在task线程容器会立即发送响应 相当于拿到一个future
        final AsyncContext asyncContext = req.startAsync();
        scheduler.execute(new PullTask(nacosPullTasks, scheduler, asyncContext, dataId, req, resp));
    }

    @Override
    public void push(String dataId, String data) {
        scheduler.schedule(new PushTask(this, dataId, data, nacosPullTasks), 0L, TimeUnit.MILLISECONDS);
    }

    @Override
    public Map<String, String> getDataStage() {
        return dataStage;
    }
}

