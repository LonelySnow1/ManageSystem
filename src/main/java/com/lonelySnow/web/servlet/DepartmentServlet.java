package com.lonelySnow.web.servlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lonelySnow.pojo.Department;
import com.lonelySnow.pojo.Notice;
import com.lonelySnow.pojo.PersonTask;
import com.lonelySnow.pojo.User;
import com.lonelySnow.service.DepartmentService;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;


@WebServlet("/department/*")
public class DepartmentServlet extends BaseServlet {

    private DepartmentService departmentService = new com.lonelySnow.service.impl.DepartmentServiceImpl();


    /**
     * Servlet内部使用，获取session中的user对象
     */
    public User getUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        return user;
    }

    /**
     * 查询部门信息
     * @param request null
     * @param response Department
     * @throws ServletException
     * @throws IOException
     */
    public void selectDepartment(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        int departmentId = getUser(request, response).getDepartmentId();
        Department department = departmentService.selectDepartment(departmentId);
        String jsonString = JSON.toJSONString(department);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

    /**
     * 更新准入码信息
     * @param request Department NewDepartment
     * @throws ServletException
     * @throws IOException
     */
    public void resetManageCode(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        int val = 0;
        try {
            val = Integer.parseInt(request.getParameter("value"));
        } catch (NumberFormatException ignored) {
        }
        BufferedReader br = request.getReader();
        String params = br.readLine();
        Department department = JSON.parseObject(params, Department.class);
        String ManageCode;
        if(department.getDepartmentId()==1){
            ManageCode = val+DigestUtils.sha1Hex(System.currentTimeMillis()+"");
        }
        else {
            ManageCode = DigestUtils.sha1Hex(System.currentTimeMillis() + "");
        }
        department.setManageCode(ManageCode);
        departmentService.resetManageCode(department);
    }

    /**
     *删除用户
     * @param request int userId
     * @throws ServletException
     * @throws IOException
     */
    public void delectUser(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        BufferedReader br = request.getReader();
        String params = br.readLine();
        int userId = Integer.parseInt(params);
        departmentService.delectUser(userId);
    }

    /**
     * 添加任务返回任务ID 创建到total-pt
     * @param request PerSonTask (taskId-->departmentId)
     * @param response int maxId (maxId-->taskId)
     * @throws ServletException
     * @throws IOException
     */
    public void addTotalPersonTask(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        BufferedReader br = request.getReader();
        String params = br.readLine();
        PersonTask pt = JSON.parseObject(params, PersonTask.class);
        int maxId = departmentService.selectMaxTaskId()+1;
        departmentService.addTotalPersonTask(maxId,pt);
        response.getWriter().print(maxId);
    }

    /**
     * 分配任务 创建到 p_task
     * @param request post: int[] users get:taskId
     * @throws ServletException
     * @throws IOException
     */
    public void assignTask(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        int taskId = Integer.parseInt(request.getParameter("taskId"));
        BufferedReader br = request.getReader();
        String params = br.readLine();
        params = params.substring(1,params.length()-1);
        String[] splits = params.split(",");
        int[] users = new int[splits.length];
        for(int i =0;i<splits.length;i++)
            users[i] = Integer. parseInt(splits[i]);
        departmentService.assignTask(users,taskId);
    }

    /**
     * 查询除总经理外所有部门信息
     * @param response Department[]
     * @throws ServletException
     * @throws IOException
     */
    public void selectAllDepartment(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        Department[] departments = departmentService.selectAllDepartment();
        String jsonString = JSON.toJSONString(departments);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

    /**
     * 查询除总经理外所有成员信息
     * @param response Department[]
     * @throws ServletException
     * @throws IOException
     */
    public void selectAllUser(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        User[] users = departmentService.selectAllUser();
        String jsonString = JSON.toJSONString(users);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

    public void addNotice(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        BufferedReader br = request.getReader();
        String params = br.readLine();
        Notice notice =  JSON.parseObject(params, Notice.class);
        departmentService.addNotice(notice);
    }

    public void addTotalDepartmentTask(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        BufferedReader br = request.getReader();
        String params = br.readLine();
        PersonTask pt = JSON.parseObject(params, PersonTask.class);
        System.out.println(pt);
        int maxId = departmentService.selectMaxDepartmentTaskId()+1;
        pt.setTaskId(maxId);
        departmentService.addTotalDepartmentTask(pt);
        response.getWriter().print(maxId);
    }

    /**
     * 分配任务 创建到 p_task
     * @param request post: int[] users get:taskId
     * @throws ServletException
     * @throws IOException
     */
    public void assignDepartmentTask(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        int taskId = Integer.parseInt(request.getParameter("taskId"));
        BufferedReader br = request.getReader();
        String params = br.readLine();
        params = params.substring(1,params.length()-1);
        String[] splits = params.split(",");
        int[] departments = new int[splits.length];
        for(int i =0;i<splits.length;i++)
            departments[i] = Integer.parseInt(splits[i]);
        departmentService.assignDepartmentTask(departments,taskId);
    }

    /**
     * 根据任务id更新状态
     *
     * @param request  任务id
     * @param response success
     * @throws ServletException
     * @throws IOException
     */
    public void updatePersonTaskState(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = getUser(request, response).getDepartmentId();
        BufferedReader br = request.getReader();
        String params = br.readLine();
        int pt_id = Integer.parseInt(params);
        departmentService.updatePersonTaskState(userId, pt_id);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write("success");
    }

    /**
     * 根据搜索栏信息查询任务
     *
     * @param request  get: currentPage,pageSize post:personTask
     * @param response PersonTask[]
     * @throws ServletException
     * @throws IOException
     */
    public void selectPersonTaskByCondition(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = getUser(request, response).getDepartmentId();
        int pageId = Integer.parseInt(request.getParameter("pageId"));
        int currentPage = Integer.parseInt(request.getParameter("currentPage"));
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        BufferedReader br = request.getReader();
        String params = br.readLine();
        PersonTask personTask = JSON.parseObject(params, PersonTask.class);
        PersonTask[] personTasks = departmentService.selectPersonTaskByCondition(pageId, userId, currentPage, pageSize, personTask);
        for (int i = 0; i < personTasks.length; i++) {
            personTasks[i].setState(personTasks[i].getState().equals("0") ? "未完成" : "完成");
            personTasks[i].setStartTime(personTasks[i].getStartTime().split("\\.")[0]);
            personTasks[i].setFinishTime(personTasks[i].getFinishTime().split("\\.")[0]);
        }
        String jsonString = JSON.toJSONString(personTasks);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

    /**
     * 根据搜索栏查询任务总数
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */

    public void selectPersonTaskCountByCondition(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = getUser(request, response).getDepartmentId();
        int pageId = Integer.parseInt(request.getParameter("pageId"));
        int currentPage = Integer.parseInt(request.getParameter("currentPage"));
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        BufferedReader br = request.getReader();
        String params = br.readLine();
        PersonTask personTask = JSON.parseObject(params, PersonTask.class);
        int count = departmentService.selectPersonTaskCountByCondition(pageId, userId, currentPage, pageSize, personTask);
        response.getWriter().print(count);
    }

    /**
     * 任务归档
     *
     * @param request  taskID
     * @param response success
     * @throws ServletException
     * @throws IOException
     */
    public void archiveTask(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = getUser(request, response).getDepartmentId();
        BufferedReader br = request.getReader();
        String params = br.readLine();
        int pt_id = Integer.parseInt(params);
        int pageId = Integer.parseInt(request.getParameter("pageId"));
        departmentService.archiveTask(pageId, userId, pt_id);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write("success");
    }






    /**
     * 获取非法请求详情
     * @throws ServletException
     * @throws IOException
     */
    public void getInvalidRequests(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getRemoteAddr();
        }
        System.out.println(request.getHeader("x-forwarded-for"));
        System.out.println("浏览器基本信息："+request.getHeader("user-agent"));
        System.out.println("客户端系统名称："+System.getProperty("os.name"));
        System.out.println("客户端系统版本："+System.getProperty("os.version"));
        System.out.println("客户端操作系统位数："+System.getProperty("os.arch"));
        System.out.println("HTTP协议版本："+request.getProtocol());
        System.out.println("请求编码格式："+request.getCharacterEncoding());
        System.out.println("Accept："+request.getHeader("Accept"));
        System.out.println("Accept-语言："+request.getHeader("Accept-Language"));
        System.out.println("Accept-编码："+request.getHeader("Accept-Encoding"));
        System.out.println("Connection："+request.getHeader("Connection"));
        System.out.println("Cookie："+request.getHeader("Cookie"));
        System.out.println("客户端发出请求时的完整URL"+request.getRequestURL());
        System.out.println("请求行中的资源名部分"+request.getRequestURI());
        System.out.println("请求行中的参数部分"+request.getRemoteAddr());
        System.out.println("客户机所使用的网络端口号"+request.getRemotePort());
        System.out.println("请求IP地址"+(ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip));
        System.out.println("WEB服务器的IP地址"+request.getLocalAddr());
        System.out.println("WEB服务器的主机名"+request.getLocalName());
        System.out.println("客户机请求方式"+request.getMethod());
        System.out.println("请求的文件的路径"+request.getServerName());
        System.out.println("请求体的数据流"+request.getReader());
        BufferedReader b=request.getReader();
        String res = "";
        while ((res = b.readLine()) != null) {
            System.out.println("request body:" + res);
        }
        System.out.println("请求所使用的协议名称"+request.getProtocol());
        System.out.println("请求中所有参数的名字"+request.getParameterNames());
        Enumeration enumNames= request.getParameterNames();
        while (enumNames.hasMoreElements()) {
            String key = (String) enumNames.nextElement();
            System.out.println("参数名称："+key);
        }
    }
}
