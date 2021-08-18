/*
@File  : AgentInfo.java
@Author: WZC
@Date  : 2021-08-04 14:59
*/
package com.guotai.clientspringboot.service;


import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.SigarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;

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

    public int getTimeInfo() {
        return (int) System.currentTimeMillis();
    }

}
