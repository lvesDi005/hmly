package cn.itcast.background_travel.service.Impl;



import cn.itcast.background_travel.dao.*;
import cn.itcast.background_travel.dao.Impl.*;
import cn.itcast.background_travel.domain.*;
import cn.itcast.background_travel.service.RouteService;

import java.util.ArrayList;
import java.util.List;

public class RouteServiceImpl implements RouteService {
    private RouteDao routeDao=new RouteDaoImpl();
    private RouteImgeDao routeImgeDao=new RouteImgeDaoImpl();

    private SellerDao sellerDao=new SellerDaoImpl();
    private FavoriteDao favoriteDao=new FavoriteDaoImpl();
    private UserDao userDao=new UserDaoImpl();
    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname) {
        //封装PageBean
        PageBean<Route> pb = new PageBean<Route>();
        //设置当前页码
        pb.setCurrentPage(currentPage);
        //设置每页显示条数
        pb.setPageSize(pageSize);
        //设置总记录数
        int totalCount=0;
        //设置当前页面显示的效果集合
        int start =0;

        totalCount=routeDao.findTotalCount(cid,rname);
        pb.setTotalCount(totalCount);
        start=(currentPage-1)*pageSize;//开始的记录数
        List<Route> list = routeDao.findByPage(cid, start, pageSize,rname);
        pb.setList(list);
        //设置总页数=总记录数/每页显示条数
        int totalPage=totalCount%pageSize==0?totalCount/pageSize:(totalCount/pageSize)+1;
        pb.setTotalPage(totalPage);
        return pb;
    }

    /**
     * 根据id查询
     * @param rid
     * @return
     */
   /* public Route findOne(String rid) {

        //根据id去route表查询route对象
        Route route = routeDao.findOne(Integer.parseInt(rid));
        //根据route的id查询图片集合信息
        List<RouteImg> routeImgList = routeImgeDao.findById(route.getRid());
        //将集合设置到route对象
        route.setRouteImgList(routeImgList);

        //根据route的sid（商家id）查询商家对象
        Seller seller = sellerDao.findById(route.getSid());
        route.setSeller(seller);

        //查询收藏次数
        int count = favoriteDao.findCountByRId(route.getRid());
        route.setCount(count);
        return route;
    }*/

    @Override
    public Route findOne(String rid, int cid) {
        //根据id去route表查询route对象
        Route route = routeDao.findOne(Integer.parseInt(rid),cid);
        //根据route的id查询图片集合信息
        List<RouteImg> routeImgList = routeImgeDao.findByRidAndCid(route.getRid(),cid);
        //将集合设置到route对象
        route.setRouteImgList(routeImgList);
        //根据route的sid（商家id）查询商家对象
        Seller seller = sellerDao.findById(route.getSid());
        route.setSeller(seller);
        //查询收藏次数
        if (cid==5){
            cid=1;
        }
        int count = favoriteDao.findCountByRId(route.getRid(),cid);
        route.setCount(count);
        return route;
    }


    @Override
    public PageBean<Route> pageFavoriteQuery(int uid,int decide,int currentPage, int pageSize) {
        //封装PageBean
        PageBean<Route> pb = new PageBean<Route>();
        //设置当前页码
        pb.setCurrentPage(currentPage);
        //设置每页显示条数
        pb.setPageSize(pageSize);
        //设置总记录数
        int totalCount=favoriteDao.findFavoriteTotalCount(uid,decide);
        pb.setTotalCount(totalCount);

        //设置当前页面显示的效果集合
        int start =(currentPage-1)*pageSize;//开始的记录数
        List<Favorite> favoriteList = favoriteDao.findFavorite(uid,decide);
        List<Route> list = new ArrayList<Route>();
        int cid=0;
        if (decide==1){
            cid=5;
        }else if (decide==2){
            cid=2;
        }
        for (int i = 0; i < favoriteList.size(); i++) {
            int rid = favoriteList.get(i).getRid();

            Route route = routeDao.findOne(rid,cid);
            //查询收藏次数
            int count = favoriteDao.findCountByRId(route.getRid(),decide);
            route.setCount(count);
            list.add(route);
        }
        pb.setList(list);
        //设置总页数=总记录数/每页显示条数
        int totalPage=totalCount%pageSize==0?totalCount/pageSize:(totalCount/pageSize)+1;
        pb.setTotalPage(totalPage);
        return pb;
    }

    @Override
    public Boolean addRoute(Route route, int cid) {
        return routeDao.addRoute(route,cid);
    }

    @Override
    public Boolean deleteRoute(int rid) {
        return routeDao.deleteRoute(rid);
    }

    @Override
    public Boolean updateRoute(Route route) {
        return routeDao.updateRoute(route);
    }

    @Override
    public List<User> findHotelBookService() {
        List<Favorite> list = favoriteDao.findHotelBook();
        ArrayList<User> users = new ArrayList<>();
        for (Favorite favorite:list) {
            int uid = favorite.getUid();
            User user = userDao.findUsername(uid);
            users.add(user);
        }
        return users;
    }

    @Override
    public Boolean deleteBook(int rid) {
        return favoriteDao.deleteFavorite(rid);
    }


}
