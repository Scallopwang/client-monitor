/*
@File  : ThriftAgentService.java
@Author: WZC
@Date  : 2021-08-18 13:53
*/
package com.guotai.clientspringboot.thrift;

import com.guotai.clientspringboot.utils.ExecuteCommand;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thriftmonitor.Agent;
import thriftmonitor.AgentProcess;
import thriftmonitor.AgentService;
import thriftmonitor.DataException;

@Service
public class ThriftAgentService implements AgentService.Iface {

    @Autowired
    ThriftClientStart thriftClientStart;
    @Autowired
    ExecuteCommand executeCommand;

    @Override
    public Agent sendAgentByIP(String s, Agent agent) throws DataException, TException {
        return null;
    }

    @Override
    public AgentProcess sendAgentProcessByIP(String s, AgentProcess agentProcess) throws TException {
        return null;
    }

    @Override
    public String getCommand(String s, String s1) throws TException {
        return null;
    }

    @Override
    public String getCollectFre(String s) throws TException {
        return null;
    }

    @Override
    public void sendMsg(String msg) throws TException {
        System.out.println("客户端接收服务端的数据为：" + msg);
        // 获取本地指令执行结果
        String commandRes = executeCommand.getCommandRes(msg);
        System.out.println("客户端执行命令结果为：" + commandRes);
        // 发送指令执行结果
        thriftClientStart.agentCommandResBack(commandRes);

    }
}
