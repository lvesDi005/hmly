package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;

public class FavoriteServiceImpl implements FavoriteService {
    private FavoriteDao favoriteDao=new FavoriteDaoImpl();

    @Override
    public Boolean isFavorite(String rid, int uid,int decide) {
        Favorite favorite = favoriteDao.findByRIdAndUid(Integer.parseInt(rid), uid,decide);

        return favorite!=null;//如果对象有值为true，反正为false
    }

    @Override
    public void add(String rid, User user, int decide) {
        favoriteDao.add(Integer.parseInt(rid),user,decide);
    }



}
