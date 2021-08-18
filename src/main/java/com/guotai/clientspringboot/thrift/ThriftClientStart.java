/*
@File  : ClientStartController.java
@Author: WZC
@Date  : 2021-08-10 9:17
*/
package com.guotai.clientspringboot.thrift;


import com.guotai.clientspringboot.service.GetAgentInfoService;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.layered.TFramedTransport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import thriftmonitor.Agent;
import thriftmonitor.AgentService;

import java.time.LocalDateTime;

@Component
public class ThriftClientStart {

    @Autowired
    GetAgentInfoService getAgentInfoService;

    public void clientStart() {
        try {
            TFramedTransport transport = new TFramedTransport(new TSocket("localhost", 8888), 600);
            TCompactProtocol tCompactProtocol = new TCompactProtocol(transport);
            AgentService.Client client = new AgentService.Client(tCompactProtocol);
            transport.open();

            Agent agent = new Agent();
            agent.setIp(getAgentInfoService.getIp());
            agent.setCpu_free(getAgentInfoService.getCPUInfo());
            agent.setMemory_free(getAgentInfoService.getMemInfo());
//            System.out.println("agent传输数据为："+client.sendAgentByIP(agent.ip, agent));
            transport.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public void agentCommandResBack(String msg) {
        try {
            TFramedTransport transport = new TFramedTransport(new TSocket("localhost", 8888), 600);
            TCompactProtocol tCompactProtocol = new TCompactProtocol(transport);
            AgentService.Client client = new AgentService.Client(tCompactProtocol);
            transport.open();
            // agent to server msg
            client.sendMsg(msg);
            transport.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
