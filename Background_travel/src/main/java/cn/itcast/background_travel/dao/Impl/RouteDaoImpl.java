package cn.itcast.background_travel.dao.Impl;


import cn.itcast.background_travel.dao.RouteDao;
import cn.itcast.background_travel.domain.Route;
import cn.itcast.background_travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public int findTotalCount(int cid, String rname) {
        String sql = "select count(*) from tab_route where 1=1 ";
        StringBuilder sb = new StringBuilder(sql);
        List params = new ArrayList();
        if (cid != 0) {
            sb.append(" and cid = ? ");
            params.add(cid);
        }
        if (rname != null && rname.length() > 0 && !"null".equals(rname)) {
            sb.append(" and rname like ? ");
            params.add("%" + rname + "%");
        }
        sql = sb.toString();
        return template.queryForObject(sql, Integer.class, params.toArray());
    }

    @Override
    public List<Route> findByPage(int cid, int start, int pageSize, String rname) {
        String sql = " select * from tab_route where 1 = 1 ";
        StringBuilder sb = new StringBuilder(sql);
        List params = new ArrayList();
        if (cid != 0) {
            sb.append(" and cid = ? ");
            params.add(cid);
        }
        if (rname != null && rname.length() > 0 && !"null".equals(rname)) {
            sb.append(" and rname like ? ");
            params.add("%" + rname + "%");
        }
        sb.append(" limit ? , ? ");
        sql = sb.toString();
        params.add(start);
        params.add(pageSize);
        return template.query(sql, new BeanPropertyRowMapper<Route>(Route.class), params.toArray());
    }

    @Override
    public Route findOne(int rid, int cid) {
        String sql = "select * from tab_route where rid = ?";
        return template.queryForObject(sql, new BeanPropertyRowMapper<Route>(Route.class), rid);
    }

    @Override
    public Boolean addRoute(Route route, int cid) {
        String sql = "INSERT INTO `tab_route`(`rname`,`price`,`routeIntroduce`,`rflag`,`rdate`,`isThemeTour`,`count`,`cid`,`rimage`,`sid`) VALUES(?,?,?,?,?,?,?,?,?,?)";
        int count = template.update(sql, route.getRname(), route.getPrice(), route.getRouteIntroduce(),
                route.getRflag(), route.getRdate(), route.getIsThemeTour(), route.getCount(),
                route.getCid(), route.getRimage(), route.getSid());
        return count == 0;
    }

    @Override
    public Boolean deleteRoute(int rid) {
        try {
            String sql = "delete from tab_route where rid= ?";
            int count = template.update(sql, rid);
            return count == 0;
        } catch (DataAccessException e) {
            return true;
        }
    }

    @Override
    public Boolean updateRoute(Route route) {
        try {
            String sql = "UPDATE `tab_route` SET `rname`=? ,`price`=? ,`routeIntroduce`=? ,`count`=? WHERE rid= ?";
            int count = template.update(sql, route.getRname(), route.getPrice(), route.getRouteIntroduce(), route.getCount(), route.getRid());
            return count == 0;
        } catch (DataAccessException e) {
            return true;
        }
    }
}
