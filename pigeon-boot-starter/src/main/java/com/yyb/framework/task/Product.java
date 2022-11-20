package com.yyb.framework.task;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author YueYB
 * @version 1.0
 * @date 2022/11/20
 * @description <p>
 * <p>
 * 不停的运行Product.task
 * 不停的运行Product.task
 * 不停的运行Product.task
 * 因为已经管理，这里可以不用写组件注解，因为已经的Bean了
 * <p>
 * <p>
 * -- 所以 @PostConstruct可以生效
 * </p>
 */

public class Product {

    public static class Task implements Runnable {


        @Override
        public void run() {
            for (; ; ) {
                System.out.println("不停的运行Product.task");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @PostConstruct
    public void init() {
        System.out.println("开始执行Product-init后置处理器");
        new Thread(new Task()).start();
    }

}
