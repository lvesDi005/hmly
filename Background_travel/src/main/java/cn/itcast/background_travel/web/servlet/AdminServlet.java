package cn.itcast.background_travel.web.servlet;



import cn.itcast.background_travel.domain.Admin;
import cn.itcast.background_travel.domain.ResultInfo;
import cn.itcast.background_travel.service.AdminService;
import cn.itcast.background_travel.service.Impl.AdminServiceImpl;
import org.apache.commons.beanutils.BeanUtils;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/admin/*")
public class AdminServlet extends BaseServlet {
    private AdminService adminService=new AdminServiceImpl();
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setPassword(password);
        Admin admin1 = adminService.login(admin);

        ResultInfo info = new ResultInfo();
        if (admin1==null){
            //用户或密码错误
            info.setFlag(false);
            info.setErrorMsg("用户或密码错误");
        }else {
            info.setFlag(true);
            request.getSession().setAttribute("admin",admin1);
        }
        writeValue(info,response);
    }
    //获取前端登录的admin账号
    public void getSession(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        Object admin = request.getSession().getAttribute("admin");
        writeValue(admin,response);
    }

}
