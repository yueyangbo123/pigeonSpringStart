package com.yyb.framework.pigeon.controller;

import com.yyb.framework.pigeon.service.LongPollingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import java.io.IOException;

/**
 * @author YueYB
 * @version 1.0
 * @date 2022/11/22
 * @description 长轮训
 *
 * TODO 这个文章得在看下
 * https://blog.csdn.net/sunquan291/article/details/111635105
 */

@RestController
@RequestMapping("/nacos")
public class LongPollingController extends HttpServlet {
    @Autowired
    private LongPollingService longPollingService;

    @RequestMapping("/pull")
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String dataId = req.getParameter("dataId");
        if (StringUtils.isEmpty(dataId)) {
            throw new IllegalArgumentException("请求参数异常,dataId能为空");
        }
        longPollingService.doGet(dataId, req, resp);
    }

    @GetMapping("/push")
    public Object push(@RequestParam("dataId") String dataId, @RequestParam("data") String data) {
        if (StringUtils.isEmpty(dataId) || StringUtils.isEmpty(data)) {
            throw new IllegalArgumentException("请求参数异常,dataId和data均不能为空");
        }
        longPollingService.push(dataId, data);
        return data;

    }

}
