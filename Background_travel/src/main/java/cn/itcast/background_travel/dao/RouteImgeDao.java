package cn.itcast.background_travel.dao;


import cn.itcast.background_travel.domain.RouteImg;

import java.util.List;

public interface RouteImgeDao {
    /**
     * 根据route的id查询图片
     * @param rid
     * @return
     */
    public List<RouteImg> findById(int rid, int cid);
    /**
     * 根据route的rid,cid作为判断查询图片
     */
    public List<RouteImg> findByRidAndCid(int rid,int cid);
}
