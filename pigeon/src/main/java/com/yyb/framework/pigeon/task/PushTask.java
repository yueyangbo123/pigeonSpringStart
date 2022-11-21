package com.yyb.framework.pigeon.task;

import com.yyb.framework.pigeon.service.LongPollingService;

import java.util.Iterator;
import java.util.Queue;

/**
 * @author YueYB
 * @version 1.0
 * @date 2022/11/22
 * @description
 */


public class PushTask implements Runnable {
    private String dataId;
    private String data;
    private Queue<PullTask> nacosPullTasks;
    LongPollingService longPollingService;

    public PushTask(LongPollingService service, String dataId, String data, Queue<PullTask> nacosPullTasks) {
        this.longPollingService = service;
        this.dataId = dataId;
        this.data = data;
        this.nacosPullTasks = nacosPullTasks;
    }

    @Override
    public void run() {
        Iterator<PullTask> iterator = nacosPullTasks.iterator();
        while (iterator.hasNext()) {
            PullTask nacosPullTask = iterator.next();
            if (dataId != null && dataId.equals(nacosPullTask.dataId)) {
                if (longPollingService.getDataStage().containsKey(dataId)) {
                    if (!longPollingService.getDataStage().get(dataId).equals(data)) {
                        //并且发生了修改
                        iterator.remove();
                        nacosPullTask.sendResponse(data);
                    }

                } else {
                    iterator.remove();
                    nacosPullTask.sendResponse(data);
                }

            }
        }
        longPollingService.getDataStage().put(dataId, data);
    }
}

