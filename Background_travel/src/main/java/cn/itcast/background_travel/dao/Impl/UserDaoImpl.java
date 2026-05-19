package cn.itcast.background_travel.dao.Impl;

import cn.itcast.background_travel.dao.UserDao;
import cn.itcast.background_travel.domain.User;
import cn.itcast.background_travel.util.JDBCUtils;
import org.junit.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class UserDaoImpl implements UserDao {
    private JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public Boolean deleteUser(String username) {
        boolean flag=true;
        int count=0;
        String sql=null;
        try {
            sql="delete from tab_user  where username = ?";
            count = template.update(sql, username);
        } catch (DataAccessException e) {
            System.out.println("该用户有其它信息，不可删除！");
        }
        if (count!=0){
            flag=false;
        }
        return flag;
    }

    @Override
    public Boolean updateUser(User user,int uid) {
        boolean flag=true;
        int count=0;
        String sql="UPDATE tab_user SET `username`=? ,`password`=? ,`name`=? ,`birthday`=? ,`sex`=? ,`telephone`=? ,`email`=? ,`status`=? WHERE uid= ?";
        count = template.update(sql, user.getUsername(),
                user.getPassword(),
                user.getName(), user.getBirthday(),
                user.getSex(), user.getTelephone(),
                user.getEmail(), user.getStatus(),uid);
        if (count!=0){
            flag=false;
        }
        return flag;
    }

    @Override
    public int findTotalCount() {
        String sql="select count(*) from tab_user";
        return template.queryForObject(sql,Integer.class);
    }

    @Override
    public List<User> findUserByPage(int start,int pageSize){
        String sql=null;
        try {
            sql="select * from tab_user limit ?,?";
        }catch (Exception e){
        }
        return template.query(sql,new BeanPropertyRowMapper<User>(User.class),start,pageSize);
    }
    @Override
    public User findUsername(String username) {
        User user = null;
        try {
            //定义sql
            String sql="select * from tab_user where username=?";
            //执行sql
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
        } catch (Exception e) {
        }
        return user;
    }
    public User findUsername(int uid){
        User user = null;
        try {
            //定义sql
            String sql="select * from tab_user where uid=?";
            //执行sql
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), uid);
        } catch (Exception e) {
        }
        return user;
    }
    //添加用户
    @Override
    public Boolean add(User user) {
        int count=0;
        Boolean flag=true;
        //sql
        String sql="INSERT INTO tab_user(username,`PASSWORD`,`NAME`,birthday,sex,telephone,email,status) VALUES(?,?,?,?,?,?,?,?)";
        count = template.update(sql,user.getUsername(),
                user.getPassword(),
                user.getName(), user.getBirthday(),
                user.getSex(), user.getTelephone(),
                user.getEmail(), user.getStatus())
        ;
        if (count!=0){
            flag=false;
        }
        return flag;
    }


}
