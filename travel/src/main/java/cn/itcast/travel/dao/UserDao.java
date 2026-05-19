package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {
    //根据用户名查询用户信息
    public User findUsername(String usehname);
    //用户保存
    public void save(User usr);

    User findByCode(String code);

    void updateStatus(User user);

    User findUsernameAndPassword(String username, String password);
}
