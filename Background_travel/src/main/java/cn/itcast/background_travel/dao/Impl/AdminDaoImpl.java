package cn.itcast.background_travel.dao.Impl;

import cn.itcast.background_travel.dao.AdminDao;
import cn.itcast.background_travel.domain.Admin;

import cn.itcast.background_travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;


public class AdminDaoImpl implements AdminDao {
    private JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public Admin findUsernameAndPassword(String username, String password) {
        Admin admin = null;
        try {
            //定义sql
            String sql="select * from admin where username=? and password=?";
            //执行sql
            admin = template.queryForObject(sql, new BeanPropertyRowMapper<Admin>(Admin.class), username,password);
        } catch (Exception e) {
        }

        return admin;
    }
}
