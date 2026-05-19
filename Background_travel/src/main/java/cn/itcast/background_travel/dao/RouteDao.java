package cn.itcast.background_travel.dao;

import cn.itcast.background_travel.domain.Route;


import java.util.List;

public interface RouteDao {
    /**
     * 根据cid查询总记录数
     */
    public int findTotalCount(int cid, String hname);
    /**
     * 根据cid，start ，pageSize查询当前页的数据集合
     */
    public List<Route> findByPage(int cid, int start, int pageSize, String hname);
    /**
     * 根据id查询
     */
    public Route findOne(int rid,int cid);

    //添加酒店
    public Boolean addRoute(Route route,int cid);
    //删除酒店
    public Boolean deleteRoute(int rid);
    public Boolean updateRoute(Route route);
}
