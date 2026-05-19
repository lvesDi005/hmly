package cn.itcast.background_travel.service;


import cn.itcast.background_travel.domain.Favorite;
import cn.itcast.background_travel.domain.User;

import java.util.List;

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
     void add(int rid, int uid, int decide);



}
