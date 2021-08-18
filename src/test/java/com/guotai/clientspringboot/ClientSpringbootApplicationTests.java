package com.guotai.clientspringboot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.util.Properties;

class ClientSpringbootApplicationTests {

    @Test
    void contextLoads() {
        Properties properties = new Properties();
        String configFilePath = "src/main/resources/TaskCron.properties";
        try {
            FileInputStream fis = new FileInputStream(configFilePath);
            properties.load(fis);
            String value = properties.getProperty("server_monitor_cron");

            System.out.println(value);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
