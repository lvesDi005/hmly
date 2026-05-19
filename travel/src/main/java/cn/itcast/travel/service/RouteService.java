package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import redis.clients.jedis.RedisPipeline;

/**
 * ÏßÂ·service
 */
public interface RouteService {
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname);

//    Route findOne(String rid);
    Route findOne(String rid,int cid);

    PageBean<Route> pageFavoriteQuery(int uid,int decide,int currentPage, int pageSize);
}
