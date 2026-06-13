package io.samples.thrift.provider;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.samples.thrift.api.SomeService;
import io.samples.thrift.api.User;

public class SomeServiceImpl implements SomeService.Iface {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    public SomeServiceImpl() {
        log.info("##############1111");
    }

    @Override
    public String echo(String msg) throws TException {
        return "Hello " + msg;
    }

    @Override
    public int addUser(User user) throws TException {
        user.setUserId(new Random().nextInt(100));
        log.info("request addUser: user:{}", user);
        return user.getUserId();
    }

    @Override
    public List<User> findUserByIds(List<Integer> idList) throws TException {
        List<User> users = new ArrayList<>();
        User user = new User();
        user.setUserId(new Random().nextInt(1000));
        users.add(user);
        log.info("request findUserByIds, idList:{}, result:{}", idList, users);
        return users;
    }
}
