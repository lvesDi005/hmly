package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.util.List;

public class FavoriteDaoImpl implements FavoriteDao {
    private JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 根据rid和uid查询收藏信息
     * @param rid
     * @param uid
     * @return
     */
    @Override
    public Favorite findByRIdAndUid(int rid, int uid ,int decide) {
        Favorite favorite = null;
        String sql=null;
        try {
            if (decide==1){
                sql="select * from tab_favorite where rid = ? and uid = ? ";
            }else if (decide==2){
                sql="select * from hotel_favorite where rid = ? and uid = ? ";
            }

            favorite = template.queryForObject(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class), rid, uid);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        return favorite;
    }

    /**
     * 根据rid 查询收藏次数
     * @param rid
     * @return
     */
    @Override
    public int findCountByRId(int rid,int decide) {
        String sql=null;
        if (decide==1){
            sql="select count(*) from tab_favorite where rid = ?";
        }else if (decide==2){
            sql="select count(*) from hotel_favorite where rid = ?";
        }
        return template.queryForObject(sql,Integer.class,rid);
    }

    /**
     * 添加收藏
     * @param rid
     * @param user
     */
    @Override
    public void add(int rid, User user, int decide) {
        String sql=null;
        int uid = user.getUid();
        String status = user.getStatus();
        if (decide==1){
            sql="INSERT INTO `tab_favorite`(`rid`,`date`,`uid`) VALUE(?,?,?)";
            template.update(sql,rid,new Date(),uid);
        }else if (decide==2){
            sql="INSERT INTO `hotel_favorite`(`rid`,`date`,`uid`,`status`) VALUE(?,?,?,?)";
            template.update(sql,rid,new Date(),uid,status);
        }

    }

    /**
     * 根据uid查询收藏Favorite
     * @param uid
     * @return
     */
    @Override
    public List<Favorite> findFavorite(int uid,int decide) {
        String sql=null;
        if (decide==1){
            sql="select * from tab_favorite where  uid = ? ";
        }else if (decide==2){
            sql="select * from hotel_favorite where  uid = ? ";
        }
        return template.query(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class), uid);
    }

    @Override
    public int findFavoriteTotalCount(int uid, int decide) {
        String sql=null;
        if (decide==1){
            sql="select count(*) from tab_favorite where uid = ?";
        }else if (decide==2){
            sql="select count(*) from hotel_favorite where uid = ?";
        }
        return template.queryForObject(sql,Integer.class,uid);

    }
}
