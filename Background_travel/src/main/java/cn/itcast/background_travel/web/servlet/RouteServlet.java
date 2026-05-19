package cn.itcast.background_travel.web.servlet;


import cn.itcast.background_travel.domain.*;
import cn.itcast.background_travel.service.FavoriteService;
import cn.itcast.background_travel.service.Impl.FavoriteServiceImpl;
import cn.itcast.background_travel.service.Impl.RouteServiceImpl;
import cn.itcast.background_travel.service.RouteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {

    private RouteService routeService=new RouteServiceImpl();
    private FavoriteService favoriteService=new FavoriteServiceImpl();
    /**
     * 分页查询
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //接收参数
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        String cidStr = request.getParameter("cid");
        //接受有rname线路名称
        String rname=request.getParameter("rname");
        if (rname!=null){
            rname = new String(rname.getBytes("iso-8859-1"),"utf-8");
        }
        //处理参数
        int cid=0;
        if (cidStr!=null && cidStr.length()>0  && !"null".equals(cidStr)){
            cid = Integer.parseInt(cidStr);
        }

        int currentPage=0;//当前页码，如果不传递，则默认为第一页
        if (currentPageStr!=null && currentPageStr.length()>0){
            currentPage = Integer.parseInt(currentPageStr);
        }else {
            currentPage=1;
        }

        int pageSize=0;//每页显示条数，如果不传递，默认每页显示5条记录
        if (pageSizeStr!=null&&pageSizeStr.length()>0){
            pageSize = Integer.parseInt(pageSizeStr);
        }else {
            pageSize=5;
        }
        //调用service查询PageBean对象
        PageBean<Route> pb = routeService.pageQuery(cid, currentPage, pageSize,rname);
        //将pageBean对象序列化为json，返回
        writeValue(pb,response);

    }

    /**
     * 根据id查询一个旅游线路的详细信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    /*public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //接收id
        String rid = request.getParameter("rid");
        //调用service查询route对象
        Route route =routeService.findOne(rid);
        //转为json写回客户端
        writeValue(route,response);
    }*/
    public void findOne1(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //接收id
        String rid = request.getParameter("rid");
        String cid = request.getParameter("cid");
        System.out.println(cid+","+rid);
        int cid1 = Integer.parseInt(cid);
        //调用service查询route对象
        Route route =routeService.findOne(rid,cid1);
        //转为json写回客户端
        writeValue(route,response);
    }
    //添加酒店
    public void addRoute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        Map<String, String[]> map = request.getParameterMap();
        Route route = new Route();
        try {
            BeanUtils.populate(route,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        Boolean flag = routeService.addRoute(route, route.getCid());
        ResultInfo info = new ResultInfo();
        if (flag){
            info.setFlag(false);
            info.setErrorMsg("添加失败");
        }else {
            info.setFlag(true);
            info.setErrorMsg("添加成功");
        }
        //将info对象序列化json
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        //将json数据写回客户端
        //设置ContentType（）
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }

    //删除酒店
    public void deleteRoute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String rid = request.getParameter("rid");
        int rid1=0;
        if (rid!=null){
            rid1 = Integer.parseInt(rid);
        }
        Boolean flag = routeService.deleteRoute(rid1);
        ResultInfo info = new ResultInfo();
        if (flag){
            info.setFlag(false);
            info.setErrorMsg("删除失败");
        }else {
            info.setFlag(true);
            info.setErrorMsg("删除成功");
        }
        //将info对象序列化json
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        //将json数据写回客户端
        //设置ContentType（）
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }
    //修改酒店信息
    public void updateRoute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        Map<String, String[]> map = request.getParameterMap();
        Route route = new Route();
        try {
            BeanUtils.populate(route,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        System.out.println(route.toString());
        Boolean flag = routeService.updateRoute(route);
        ResultInfo info = new ResultInfo();
        if (flag){
            info.setFlag(false);
            info.setErrorMsg("修改失败");
        }else {
            info.setFlag(true);
            info.setErrorMsg("修改成功");
        }
        //将info对象序列化json
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        //将json数据写回客户端
        //设置ContentType（）
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }

    /**
     * 判断当前登录用户是否收藏过该线路
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //接收id
        String rid = request.getParameter("rid");
        int decide = Integer.parseInt(request.getParameter("decide"));
        //获取当前登录的用户user
        User user = (User)request.getSession().getAttribute("user");
        int uid;//用户id
        if (user==null){
            //用户尚未登录
            return;
        }else {
            //用户已经登录
            uid = user.getUid();
        }
        //调用FavoriteService查询是否收藏
        Boolean flag = favoriteService.isFavorite(rid, uid,decide);

        //转为json写回客户端
        writeValue(flag,response);
    }
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //接收id
        int rid = Integer.parseInt(request.getParameter("rid"));
        int uid = Integer.parseInt(request.getParameter("uid"));
        int cid = Integer.parseInt(request.getParameter("cid"));
        favoriteService.add(rid, uid,cid);
    }

    /**
     * 查询用户收藏的线路
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //接收参数
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        int uid = Integer.parseInt(request.getParameter("uid"));

        String decide = request.getParameter("decide");

        int currentPage=0;//当前页码，如果不传递，则默认为第一页
        if (currentPageStr!=null && currentPageStr.length()>0){
            currentPage = Integer.parseInt(currentPageStr);
        }else {
            currentPage=1;
        }

        int pageSize=0;//每页显示条数，如果不传递，默认每页显示5条记录
        if (pageSizeStr!=null&&pageSizeStr.length()>0){
            pageSize = Integer.parseInt(pageSizeStr);
        }else {
            pageSize=12;
        }
        //调用service查询PageBean对象
        PageBean<Route> pb = routeService.pageFavoriteQuery(uid,Integer.parseInt(decide),currentPage, pageSize);
        //将pageBean对象序列化为json，返回
        writeValue(pb,response);

    }
    public void findOrderName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        List<User> users = routeService.findHotelBookService();
        writeValue(users,response);
    }
    public void deleteOrderName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        int rid = Integer.parseInt(request.getParameter("rid"));
        Boolean flag = routeService.deleteBook(rid);
        ResultInfo info = new ResultInfo();
        if (flag){
            info.setFlag(false);
            info.setErrorMsg("删除失败");
        }else {
            info.setFlag(true);
            info.setErrorMsg("删除成功");
        }
        //将info对象序列化json
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        //将json数据写回客户端
        //设置ContentType（）
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }


}
