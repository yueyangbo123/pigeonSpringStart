package com.yyb.framework.pigeon.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author YueYB
 * @version 1.0
 * @date 2022/11/22
 * @description
 */

public interface LongPollingService {

    void doGet(String dataId, HttpServletRequest req, HttpServletResponse resp);

    void push(String dataId, String data);

    Map<String, String> getDataStage();

}
