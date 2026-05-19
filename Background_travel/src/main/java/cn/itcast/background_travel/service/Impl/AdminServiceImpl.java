package cn.itcast.background_travel.service.Impl;

import cn.itcast.background_travel.dao.AdminDao;
import cn.itcast.background_travel.dao.Impl.AdminDaoImpl;
import cn.itcast.background_travel.domain.Admin;
import cn.itcast.background_travel.service.AdminService;


public class AdminServiceImpl implements AdminService {
    private AdminDao adminDao=new AdminDaoImpl();
    @Override
    public Admin login(Admin admin) {
        System.out.println(admin);
        return adminDao.findUsernameAndPassword(admin.getUsername(),admin.getPassword());
    }
}
