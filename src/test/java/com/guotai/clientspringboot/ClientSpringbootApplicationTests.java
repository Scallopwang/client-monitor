package com.guotai.clientspringboot;

import com.guotai.clientspringboot.utils.ExecuteCommand;
import org.hyperic.sigar.ProcCpu;
import org.hyperic.sigar.ProcState;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.cmd.Ps;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.lang.management.ManagementFactory;
import java.nio.charset.Charset;
import java.util.*;

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

    @Test
    void sigarTest() throws SigarException {
        try {
            Sigar sigar = new Sigar();
            for (Long pid : sigar.getProcList()) {
                try {
                    Ps ps = new Ps();

                    List<String> list = ps.getInfo(sigar, pid);
                    System.out.println(list.toString());
                    System.out.println("*********************");
                } catch (SigarException e) {

                }
            }
        } catch (SigarException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void myTest() {
        try {
            Ps ps = new Ps();
            Sigar sigar = new Sigar();
            String name = ManagementFactory.getRuntimeMXBean().getName();
            Long pid = Long.parseLong(name.split("@")[0]);
            List<String> list = ps.getInfo(sigar, pid);
            System.out.println(list.toString());
        } catch (SigarException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void myTest1() {
//        ExecuteCommand executeCommand = new ExecuteCommand();
//        System.out.println(executeCommand.getCommandRes("jps"));
        try {
            Sigar sigar = new Sigar();
            Ps ps = new Ps();
            List<String> list = ps.getInfo(sigar, 41904);
            System.out.println(list);
        } catch (SigarException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void myTest2() {
        String para = "jps";
        try {
            String commandBaseStr = "cmd /C ";
            Process process = Runtime.getRuntime().exec(commandBaseStr + para);
            InputStreamReader reader = new InputStreamReader(process.getInputStream(), Charset.forName("GBK"));
            LineNumberReader line = new LineNumberReader(reader);
            StringBuilder resStr = new StringBuilder();
            Long pId = 0L;
            String str;
            String comment;

            while ((str = line.readLine()) != null) {
                if (str.contains("ClientSpringbootApplication")) {
                    pId = Long.parseLong(str.split(" ")[0]);
                    System.out.println(pId);
                }
            }
            if (pId != 0) {
                comment = "进程正在运行";
            } else {
                comment = "进程不在运行";
            }
            System.out.println(comment);
            try {
                Sigar sigar = new Sigar();
                Ps ps = new Ps();
                List<String> list = ps.getInfo(sigar, pId);
                System.out.println(list);
            } catch (SigarException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    /***
     * [0]进程ID
     * [1]进程所属用户名,取得不到的话返回的是???并不是乱码
     * [2]启动时间
     * [3]进程的虚拟内存
     * [4]进程的常驻内存
     * [5]进程的共享内存(如果这一块取不到的话只会返回一个？？？所以内存占用状态取不到数组长度会少2，所以要小心越界)
     * [6]进程状态（SLEEP = ‘S’;RUN = ‘R’;STOP = ‘T’;ZOMBIE = ‘Z’;IDLE = ‘D’;）
     * [7]总时长
     * [8]进程所属详细信息
     * ***/
    public void getProcess() {
        try {
            Sigar sigar = new Sigar();
            String commandBaseStr = "cmd /C ";
            long pId = 0L;
            String lineStr;
            Process process = Runtime.getRuntime().exec(commandBaseStr + "jps");
            InputStreamReader reader = new InputStreamReader(process.getInputStream(), Charset.forName("GBK"));
            LineNumberReader line = new LineNumberReader(reader);
            while ((lineStr = line.readLine()) != null) {
                if (lineStr.contains("ClientSpringbootApplication")) {
                    pId = Long.parseLong(lineStr.split(" ")[0]);
                }
            }
            List resList = Ps.getInfo(sigar, pId);
            resList.add("ClientSpringbootApplication");
            System.out.println(resList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
