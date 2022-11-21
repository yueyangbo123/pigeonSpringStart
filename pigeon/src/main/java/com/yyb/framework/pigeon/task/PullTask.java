package com.yyb.framework.pigeon.task;

/**
 * @author YueYB
 * @version 1.0
 * @date 2022/11/22
 * @description
 */

import com.yyb.base.common.responses.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Queue;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class PullTask implements Runnable {
    Queue<PullTask> nacosPullTasks;
    ScheduledExecutorService scheduler;
    AsyncContext asyncContext;
    String dataId;
    HttpServletRequest req;
    HttpServletResponse resp;

    Future<?> asyncTimeoutFuture;

    public PullTask(Queue<PullTask> nacosPullTasks, ScheduledExecutorService scheduler,
                    AsyncContext asyncContext, String dataId, HttpServletRequest req, HttpServletResponse resp) {
        this.nacosPullTasks = nacosPullTasks;
        this.scheduler = scheduler;
        this.asyncContext = asyncContext;
        this.dataId = dataId;
        this.req = req;
        this.resp = resp;
    }

    @Override
    public void run() {
        asyncTimeoutFuture = scheduler.schedule(() -> {
            //超时等待10s
            log.info("10秒后超时结束长轮询任务:" + new Date());
            nacosPullTasks.remove(PullTask.this);

            sendResponse("time-out");

        }, 10, TimeUnit.SECONDS);
        nacosPullTasks.add(this);
    }

    public void sendResponse(String result) {
        System.out.println("发送响应:" + new Date());
        //取消等待执行的任务,避免已经响完了,还有资源被占用
        if (asyncTimeoutFuture != null) {
            //设置为true会立即中断执行中的任务,false对执行中的任务无影响,但会取消等待执行的任务
            asyncTimeoutFuture.cancel(false);
        }

        //设置页码编码
        resp.setContentType("application/json; charset=utf-8");
        resp.setCharacterEncoding("utf-8");

        //禁用缓存
        resp.setHeader("Pragma" , "no-cache");
        resp.setHeader("Cache-Control" , "no-cache,no-store");
        resp.setDateHeader("Expires" , 0);
        resp.setStatus(HttpServletResponse.SC_OK);
        //输出Json流
        sendJsonResult(result);
    }

    private void sendJsonResult(String result) {
        R<String> pojoResult = new R<>();
        pojoResult.setData(result);
        PrintWriter writer = null;
        try {
            writer = asyncContext.getResponse().getWriter();
            writer.write(pojoResult.toString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            asyncContext.complete();
            if (null != writer) {
                writer.close();
            }
        }
    }

}

