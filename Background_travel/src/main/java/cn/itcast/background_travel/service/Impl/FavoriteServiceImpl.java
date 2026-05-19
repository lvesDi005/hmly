package cn.itcast.background_travel.service.Impl;


import cn.itcast.background_travel.dao.FavoriteDao;
import cn.itcast.background_travel.dao.Impl.FavoriteDaoImpl;
import cn.itcast.background_travel.domain.Favorite;
import cn.itcast.background_travel.domain.User;
import cn.itcast.background_travel.service.FavoriteService;

import java.util.List;

public class FavoriteServiceImpl implements FavoriteService {
    private FavoriteDao favoriteDao=new FavoriteDaoImpl();

    @Override
    public Boolean isFavorite(String rid, int uid,int decide) {
        Favorite favorite = favoriteDao.findByRIdAndUid(Integer.parseInt(rid), uid,decide);

        return favorite!=null;//如果对象有值为true，反正为false
    }

    @Override
    public void add(int rid, int uid, int decide) {
        favoriteDao.add(rid,uid,decide);
    }




}
