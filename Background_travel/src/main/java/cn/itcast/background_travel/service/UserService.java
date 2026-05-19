package cn.itcast.background_travel.service;

import cn.itcast.background_travel.domain.PageBean;
import cn.itcast.background_travel.domain.User;

import java.util.List;

public interface UserService {
    //添加用户信息
        public Boolean addUser(User user);
    //查看所有用户
    public PageBean<User> pageQuery(int currentPage, int pageSize);
    //通过username查询user
    public User findOnlyUser(String username);
    //uid查询user
    public User findOnlyUser(int uid);
    public Boolean deleteUser(String username);
    //修改用户
    public Boolean updateUser(User user,int uid);
}
