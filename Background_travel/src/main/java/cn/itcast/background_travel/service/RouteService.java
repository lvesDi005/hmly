package cn.itcast.background_travel.service;


import cn.itcast.background_travel.domain.Favorite;
import cn.itcast.background_travel.domain.PageBean;
import cn.itcast.background_travel.domain.Route;
import cn.itcast.background_travel.domain.User;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.List;

/**
 * ÏßÂ·service
 */
public interface RouteService {
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname);

//    Route findOne(String rid);
    Route findOne(String rid,int cid);

    PageBean<Route> pageFavoriteQuery(int uid,int decide,int currentPage, int pageSize);
    public Boolean addRoute(Route route,int cid);
    //É¾³ý¾Æµê
    public Boolean deleteRoute(int rid);
    //ÐÞ¸Ä¾Æµê
    public Boolean updateRoute(Route route);
    //²éÑ¯Ô¤¶¨
    List<User> findHotelBookService();
    //É¾³ýÔ¤¶©
    Boolean deleteBook(int rid);
}
