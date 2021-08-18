/*
@File  : ServerStartController.java
@Author: WZC
@Date  : 2021-08-18 13:47
*/
package com.guotai.clientspringboot.thrift;

import com.guotai.clientspringboot.thrift.ThriftAgentService;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.apache.thrift.transport.layered.TFramedTransport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import thriftmonitor.AgentService;

@Component
public class ThriftServerStart implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    ThriftAgentService thriftAgentService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("agent service starting...");
        TNonblockingServerSocket tNonblockingServerSocket = null;
        try {
            tNonblockingServerSocket = new TNonblockingServerSocket(8887);
        } catch (TTransportException e) {
            e.printStackTrace();
        }
        THsHaServer.Args args1 = new THsHaServer.Args(tNonblockingServerSocket).minWorkerThreads(2).maxWorkerThreads(4);
        AgentService.Processor<ThriftAgentService> AgentServiceProcessor = new AgentService.Processor<>(thriftAgentService);

        args1.protocolFactory(new TCompactProtocol.Factory());
        args1.transportFactory(new TFramedTransport.Factory());
        args1.processorFactory(new TProcessorFactory(AgentServiceProcessor));

        THsHaServer tHsHaServer = new THsHaServer(args1);
        System.out.println("agent service started");
        tHsHaServer.serve();
    }
}
