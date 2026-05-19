package cn.itcast.background_travel.dao;


import cn.itcast.background_travel.domain.Admin;

public interface AdminDao {
    /**
     * 管理员管理员
     */
    public Admin findUsernameAndPassword(String username, String password);
}
