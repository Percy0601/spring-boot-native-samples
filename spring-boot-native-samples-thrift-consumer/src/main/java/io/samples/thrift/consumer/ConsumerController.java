package io.samples.thrift.consumer;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.apache.thrift.transport.layered.TFramedTransport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.samples.thrift.api.SomeService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: baoxin.zhao
 * @date: 2024/02/24
 */
@Slf4j
@RestController
@RequestMapping("/consumer")
public class ConsumerController {

    @GetMapping("/hello")
    public String hello(String name) {
        TTransport tsocket = null;
        TTransport transport = null;
        try {
            tsocket = new TSocket("localhost", 30880);
            transport = new TFramedTransport(tsocket);
        } catch (TTransportException e) {
            throw new RuntimeException(e);
        }

        TProtocol protocol = new TBinaryProtocol(transport);
        TMultiplexedProtocol multiplexedProtocol = new TMultiplexedProtocol(protocol, "SomeService");
        SomeService.Iface client = new SomeService.Client(multiplexedProtocol);
        try {
            tsocket.open();
            String result = client.echo(name);
            return "Hello World, ".concat(result).concat("!");
        } catch (TException e) {
            throw new RuntimeException(e);
        } finally {
            if(transport.isOpen()) {
                transport.close();
            }
            if(tsocket.isOpen()) {
                tsocket.close();
            }
        }
    }

}
