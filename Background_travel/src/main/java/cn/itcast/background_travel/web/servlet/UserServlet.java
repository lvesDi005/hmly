package cn.itcast.background_travel.web.servlet;



import cn.itcast.background_travel.domain.PageBean;
import cn.itcast.background_travel.domain.ResultInfo;
import cn.itcast.background_travel.domain.User;
import cn.itcast.background_travel.service.Impl.UserServiceImpl;
import cn.itcast.background_travel.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    private UserService userService=new UserServiceImpl();
    public void findUserByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //接收参数
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");

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
        PageBean<User> pb = userService.pageQuery(currentPage, pageSize);
        writeValue(pb,response);
    }
    public void findOnlyUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int uid = Integer.parseInt(request.getParameter("uid"));
        User user = userService.findOnlyUser(uid);
        request.getSession().setAttribute("uid",uid);
        writeValue(user,response);
    }
    public void addUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取数据
        Map<String, String[]> map = request.getParameterMap();
        //封装对象
        User user = new User();

        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        String username = user.getUsername();
        User user1 = userService.findOnlyUser(username);
        ResultInfo info = new ResultInfo();
        if (user1!=null){
            info.setFlag(false);
            info.setErrorMsg("该用户名已被注册");
        }else {
            //调用service完成注册
            Boolean flag =userService.addUser(user);
            //响应结果
            if (!flag){
                //注册成功
                info.setFlag(true);
            }else{
                //注册失败
                info.setFlag(false);
                info.setErrorMsg("注册失败");
            }
        }
        //将info对象序列化json
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        //将json数据写回客户端
        //设置ContentType（）
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);

    }
    public void deleteUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        Boolean flag=userService.deleteUser(username);
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
    public void updateUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取数据
        Map<String, String[]> map = request.getParameterMap();
        int uid = (int)request.getSession().getAttribute("uid");
        System.out.println(uid);
        //封装对象
        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        System.out.println(user.toString());
        Boolean flag=userService.updateUser(user,uid);
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


}
