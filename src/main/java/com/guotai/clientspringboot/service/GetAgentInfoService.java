/*
@File  : AgentInfo.java
@Author: WZC
@Date  : 2021-08-04 14:59
*/
package com.guotai.clientspringboot.service;


import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.cmd.Ps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.List;

@Service
public class GetAgentInfoService {
    @Autowired
    private GetSigarInstance getSigarInstance;


    public String getIp(){
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
    }

    public double getCPUInfo() {
        try {
            CpuPerc cpuPerc = getSigarInstance.getSigar().getCpuPerc();
            return cpuPerc.getCombined();
        } catch (SigarException e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    public double getMemInfo() {
        double freeMem;
        try {
            Mem mem = getSigarInstance.getSigar().getMem();
            freeMem = mem.getFree()*1.0/1024/1024;
            return freeMem;
        } catch (SigarException e) {
            e.printStackTrace();
            return 0.0;
        }
    }

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
     * [9]自己补充的进程名
     * ***/
    public List getProcess() {
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
            return resList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getTimeInfo() {
        return (int) System.currentTimeMillis();
    }

}
