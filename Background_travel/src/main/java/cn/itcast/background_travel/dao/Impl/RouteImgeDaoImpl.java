package cn.itcast.background_travel.dao.Impl;


import cn.itcast.background_travel.dao.RouteImgeDao;
import cn.itcast.background_travel.domain.RouteImg;
import cn.itcast.background_travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RouteImgeDaoImpl implements RouteImgeDao {

    private JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public List<RouteImg> findById(int rid, int cid) {
        String sql = "select * from tab_route_img where rid = ?";
        return template.query(sql, new BeanPropertyRowMapper<RouteImg>(RouteImg.class), rid);
    }

    @Override
    public List<RouteImg> findByRidAndCid(int rid, int cid) {
        String sql = "select * from tab_route_img where rid = ?";
        return template.query(sql, new BeanPropertyRowMapper<RouteImg>(RouteImg.class), rid);
    }
}
