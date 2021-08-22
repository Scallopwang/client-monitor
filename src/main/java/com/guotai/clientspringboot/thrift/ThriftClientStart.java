/*
@File  : ClientStartController.java
@Author: WZC
@Date  : 2021-08-10 9:17
*/
package com.guotai.clientspringboot.thrift;


import com.guotai.clientspringboot.service.GetAgentInfoService;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransportException;
import org.apache.thrift.transport.layered.TFramedTransport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import thriftmonitor.Agent;
import thriftmonitor.AgentProcess;
import thriftmonitor.AgentService;

import java.util.List;

@Component
public class ThriftClientStart {

    @Autowired
    GetAgentInfoService getAgentInfoService;

    private Agent agent = new Agent();
    private AgentProcess agentProcess = new AgentProcess();
    private AgentService.Client client = clientInit();

    public AgentService.Client clientInit() {
        try {
            TFramedTransport transport = new TFramedTransport(new TSocket("localhost", 8888), 600);
            TCompactProtocol tCompactProtocol = new TCompactProtocol(transport);
            AgentService.Client client = new AgentService.Client(tCompactProtocol);
            transport.open();
            return client;
        } catch (TTransportException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void clientStart() {
        try {
            List processList = getAgentInfoService.getProcess();
            agent.setIp(getAgentInfoService.getIp());
            agent.setCpu_free(getAgentInfoService.getCPUInfo());
            agent.setMemory_free(getAgentInfoService.getMemInfo());
            System.out.println("agent传输数据为：" + client.sendAgentByIP(agent.ip, agent));

            agentProcess.setIp(getAgentInfoService.getIp());
            agentProcess.setProcess_name(processList.get(9).toString());
            agentProcess.setProcess_id(processList.get(0).toString());
            agentProcess.setProcess_mem(processList.get(4).toString());
            agentProcess.setProcess_start_time(processList.get(2).toString());
            System.out.println("agent传输进程信息为：" + client.sendAgentProcessByIP(agentProcess.ip, agentProcess));

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public void agentCommandResBack(String msg) {
        try {
            // agent to server msg
            client.sendMsg(msg);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
