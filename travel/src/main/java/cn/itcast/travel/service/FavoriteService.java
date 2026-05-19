package cn.itcast.travel.service;

import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;

public interface FavoriteService {
    /**
     * 判断是否收藏
     * @param rid
     * @param uid
     * @return
     */
    public Boolean isFavorite(String rid,int uid,int decide);

    /**
     * 添加收藏
     * @param rid

     */
     void add(String rid, User user, int decide);


}
