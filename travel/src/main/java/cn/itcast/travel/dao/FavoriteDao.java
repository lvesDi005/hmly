package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.User;

import java.util.List;


public interface FavoriteDao {
    /**
     * 根据rid和uid查询收藏信息
     */
    public Favorite findByRIdAndUid(int rid, int uid,int decide);

    /**
     * 根据rid 查询线路被收藏次数
     * @param rid
     * @return
     */
    public int findCountByRId(int rid,int decide);
    //添加收藏
    void add(int parseInt, User user , int decide);



    /**
     *根据uid查询rid
     */
    public List<Favorite> findFavorite(int uid,int decide);

    int findFavoriteTotalCount(int uid,int decide);
}
