package cn.itcast.background_travel.service.Impl;

import cn.itcast.background_travel.dao.Impl.UserDaoImpl;
import cn.itcast.background_travel.dao.UserDao;
import cn.itcast.background_travel.domain.PageBean;
import cn.itcast.background_travel.domain.User;
import cn.itcast.background_travel.service.UserService;


import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDao userDao=new UserDaoImpl();

    @Override
    public Boolean addUser(User user) {

        return userDao.add(user);
    }

    @Override
    public PageBean<User> pageQuery(int currentPage, int pageSize) {
        PageBean<User> pb = new PageBean<>();
        //设置当前页码
        pb.setCurrentPage(currentPage);
        //设置每页显示条数
        pb.setPageSize(pageSize);
        //总记录数
        int totalCount=0;
        int start =0;
        totalCount = userDao.findTotalCount();
        pb.setTotalCount(totalCount);
        start=(currentPage-1)*pageSize;//开始记录数
        List<User> list = userDao.findUserByPage(start, pageSize);
        pb.setList(list);
        //设置总页数=总记录数/每页显示条数
        int totalPage=totalCount%pageSize==0?totalCount/pageSize:(totalCount/pageSize)+1;
        pb.setTotalPage(totalPage);
        return pb;
    }

    @Override
    public User findOnlyUser(String username) {
        return userDao.findUsername(username);
    }
    @Override
    public User findOnlyUser(int uid){
        return userDao.findUsername(uid);
    }
    @Override
    public Boolean deleteUser(String username) {
        Boolean flag = userDao.deleteUser(username);
        return flag;
    }

    @Override
    public Boolean updateUser(User user ,int uid) {
        return userDao.updateUser(user,uid);
    }

}
