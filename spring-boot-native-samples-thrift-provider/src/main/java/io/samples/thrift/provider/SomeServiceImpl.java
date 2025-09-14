package io.samples.thrift.provider;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.thrift.TException;

import io.samples.thrift.api.SomeService;
import io.samples.thrift.api.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SomeServiceImpl implements SomeService.Iface {

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
