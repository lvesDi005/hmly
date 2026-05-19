package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

public interface UserService {
    //注册用户
    Boolean regist(User user);
    //激活方法
    Boolean active(String code);

    User login(User user);
}
