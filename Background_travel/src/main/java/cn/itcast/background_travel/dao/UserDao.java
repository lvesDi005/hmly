package cn.itcast.background_travel.dao;

import cn.itcast.background_travel.domain.User;

import java.util.List;

public interface UserDao {

    //查询用户个数
    public int findTotalCount();
    //查询所有用户
    public List<User> findUserByPage(int start, int pageSize);
    //根据单个用户名查询用户信息
    public User findUsername(String username);
    public User findUsername(int uid);
    //用户添加
    public Boolean add(User user);
    //用户删除
    public  Boolean deleteUser(String username);
    //修改用户
    public  Boolean updateUser(User user,int uid);
}
