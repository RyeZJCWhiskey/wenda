package com.nowcoder.wenda.model;

import org.springframework.stereotype.Component;

/**
 * 写这个类是为了让所有的视图层页面都能访问到user这个用户变量
 */
@Component
public class HostHolder {
    //用到了变量副本（线程本地变量）而不是直接new User，这么写避免了多个线程同时访问时user只能表示同一个用户
    //看起来是一个变量，其实是为每个线程中都分配了一个变量，在不同线程中每个变量所占的内存是不一样的
    private static ThreadLocal<User> users = new ThreadLocal<User>();

    //根据当前线程拿到和当前线程关联的对象，底层存在Map<ThreadID, User>
    public User getUser(){
        return users.get();
    }

    public void setUsers(User user){
        users.set(user);
    }

    public void clear(){
        users.remove();
    }
}
