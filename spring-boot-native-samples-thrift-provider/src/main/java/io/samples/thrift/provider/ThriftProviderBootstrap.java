package io.samples.thrift.provider;

/**
 * @author: baoxin.zhao
 * @date: 2024/02/24
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;
import org.apache.thrift.transport.layered.TFramedTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.samples.thrift.api.SomeService;

@SpringBootApplication
public class ThriftProviderBootstrap {

    private static Logger log= LoggerFactory.getLogger(ThriftProviderBootstrap.class);
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Runnable runnable = () -> {
            try {
                log.info("thrift provider starting.");
                TProcessor tprocessor = new SomeService.Processor<SomeService.Iface>(new SomeServiceImpl());
                TMultiplexedProcessor processor = new TMultiplexedProcessor();
                processor.registerProcessor("SomeService", tprocessor);

                TNonblockingServerTransport serverTransport = new TNonblockingServerSocket(30880);
                TThreadedSelectorServer.Args tArgs = new TThreadedSelectorServer.Args(serverTransport);
                tArgs.transportFactory(new TFramedTransport.Factory());
                tArgs.protocolFactory(new TBinaryProtocol.Factory());
                tArgs.executorService(executorService);
                tArgs.acceptQueueSizePerThread(1000);
                tArgs.stopTimeoutVal(30);
                tArgs.stopTimeoutUnit(TimeUnit.SECONDS);
                tArgs.processor(processor);

                TServer server = new TThreadedSelectorServer(tArgs);
                server.serve();
                log.info("thrift provider started");
            } catch (TTransportException e) {
                log.warn("server start error:{}", e.getMessage());
            }
        };

        Thread thread = new Thread(runnable);
        thread.setDaemon(true);
        thread.start();

        SpringApplication.run(ThriftProviderBootstrap.class, args);
    }

}
