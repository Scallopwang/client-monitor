/*
@File  : DynamicScheduledConfigurer.java
@Author: WZC
@Date  : 2021-08-17 11:02
*/
package com.guotai.clientspringboot.controller;

import com.guotai.clientspringboot.thrift.ThriftClientStart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Controller;

import java.io.FileInputStream;
import java.util.Properties;

@Controller
public class DynamicScheduledConfigurer implements SchedulingConfigurer {

    // 默认每秒执行一次定时任务
    private String cron = "0/1 * * * * ?";

    @Autowired
    private ThriftClientStart thriftClientStart;

    public String getCron() {
        Properties properties = new Properties();
        String configFilePath = "src/main/resources/TaskCron.properties";
        try {
            FileInputStream fis = new FileInputStream(configFilePath);
            properties.load(fis);
            cron = properties.getProperty("server_monitor_cron");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return cron;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(
                () -> {
                    thriftClientStart.clientStart();
                },
                triggerContext -> {
                    // 返回执行周期(Date)
                    CronTrigger cronTrigger = new CronTrigger(getCron());
                    return cronTrigger.nextExecutionTime(triggerContext);
                }
        );
    }
}
