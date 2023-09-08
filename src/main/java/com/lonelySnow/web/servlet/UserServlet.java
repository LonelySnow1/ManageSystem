package com.lonelySnow.web.servlet;

import com.alibaba.fastjson.JSON;
import com.lonelySnow.pojo.Department;
import com.lonelySnow.pojo.Notice;
import com.lonelySnow.pojo.PersonTask;
import com.lonelySnow.pojo.User;
import com.lonelySnow.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {

    private UserService userService = new com.lonelySnow.service.impl.UserServiceImpl();


    public User selectUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        int userId = getUser(request, response).getUserId();
        return userService.selectUser(userId);
    }

    /**
     * 检查登录验证码正确与否
     *
     * @param request  get：验证码 post：user对象
     * @param response v-false 或 进入 selectAccountExist
     * @throws ServletException
     * @throws IOException
     */

    public void loginCheck(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session1 = request.getSession();
        response.setContentType("text/json;charset=utf-8");
        HttpSession session = request.getSession();
        Object obj = session.getAttribute("KAPTCHA_SESSION_KEY");
        String params = request.getParameter("vcode");
        System.out.println("主人，这是你的验证码喵：" + obj);
        if (params.equals(obj))
            selectAccountExist(request, response);
        else
            response.getWriter().write("v-false");

    }

    /**
     * 登录
     *
     * @param request  user对象——邮箱密码
     * @param response user对象——全数据 cookie——user:email->password
     * @throws ServletException
     * @throws IOException
     */

    public void selectAccountExist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //获取对应查询条件对象

        BufferedReader br = request.getReader();
        String params = br.readLine();
        String isCheck = request.getParameter("Cookies");
        String password;

        //转为Brand对象
        User user1 = JSON.parseObject(params, User.class);
        password = user1.getPassword();
        //md5盐值加密，密码前+“@”再进行md5加密
        user1.setPassword(DigestUtils.md5Hex("@" + user1.getPassword()));

        //2.调用service查询
        User user = userService.selectAccountExist(user1);

        try{
            user.getEmail();
            user.getPassword();
        }catch (Exception e){
            return;
        }
        //2.转为json
        String jsonString = JSON.toJSONString(user);

        //3.写数据
        response.setContentType("text/json;charset=utf-8");
        Cookie userCookie;
        if (isCheck.equals("true")) {
            userCookie = new Cookie("user", user.getEmail() + "->" + password);
            userCookie.setMaxAge(60 * 60 * 24);
        } else {
            userCookie = new Cookie("user", "");
            userCookie.setMaxAge(0);
        }
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        userCookie.setPath("/ManageSystem/");
        response.addCookie(userCookie);
        response.getWriter().write(jsonString);
    }

    /**
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void selectDepartmentNameFromID(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int departmentId = getUser(request, response).getDepartmentId();
        String departmentName = userService.selectDepartmentNameFromID(departmentId);
        String jsonString = JSON.toJSONString(departmentName);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

    /**
     * 获取cookie数据
     *
     * @param request  cookies
     * @param response 账号->密码
     * @throws ServletException
     * @throws IOException
     */
    public void getCookies(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user")) {
                    response.getWriter().write(cookie.getValue());
                    return;
                }
            }
        } else
            response.getWriter().write("null");
    }

    /**
     * 检查session中是否存在user这项
     *
     * @param request
     * @param response User or null
     * @throws ServletException
     * @throws IOException
     */
    public void checkSession(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String jsonString = JSON.toJSONString(user);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

    /**
     * Servlet内部使用，获取session中的user对象
     */
    public User getUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        return user;
    }

    /**
     * 重置session
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void resetSession(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        User user = selectUser(request, response);
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        session.setAttribute("user",user);
    }

    /**
     * 删除session
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void delectSession(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        HttpSession session = request.getSession();
        session.removeAttribute("user");
    }

    /**
     * 发送邮件验证码
     *
     * @param request  邮箱地址
     * @param response 验证码
     * @throws ServletException
     * @throws IOException
     */
    public void sendEmail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader br = request.getReader();
        String email = br.readLine();
        String achieveCode = userService.sendEmailCode(email);
        System.out.println("主人，这是你的邮箱验证码喵：" + achieveCode);
        String jsonString = JSON.toJSONString(achieveCode);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }


    /**
     * 用户注册
     *
     * @param request  get:准入码 post:user对象
     * @param response success or
     * @throws ServletException
     * @throws IOException
     */
    public void signCheck(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/json;charset=utf-8");
        String managecode = request.getParameter("managecode");
        BufferedReader br = request.getReader();
        String params = br.readLine();
        User user = JSON.parseObject(params, User.class);
        if (userService.selectExistName(user.getName()) != null) {
            response.getWriter().write("该昵称已被使用");
            return;
        }
        if (userService.selectExistEmail(user.getEmail()) != null) {
            response.getWriter().write("该邮箱已被使用");
            return;
        }
        Department department = userService.selectSignDepartment(managecode);
        if (department != null && department.getTime() != 0) {
            String position = department.equals("总经理") ? "部门总管" : "成员";
            user.setPosition(position);
            user.setPassword(DigestUtils.md5Hex("@" + user.getPassword()));
            int departmentId;
            if(position.equals("部门总管"))
                departmentId = managecode.charAt(0);
            else
                departmentId = department.getDepartmentId();
            user.setDepartmentId(departmentId);
            userService.addUser(user);
            userService.updateTime(departmentId);
            response.getWriter().write("success");
        } else
            response.getWriter().write("准入码无效");

    }

    /**
     * 修改密码
     *
     * @param request  user——email+newPasword
     * @param response success
     * @throws ServletException
     * @throws IOException
     */
    public void resetPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader br = request.getReader();
        String params = br.readLine();
        User user = JSON.parseObject(params, User.class);
        user.setPassword(DigestUtils.md5Hex("@" + user.getPassword()));
        userService.resetPassword(user);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write("success");
    }


    /**
     * 查询所有公告
     *
     * @param request  null
     * @param response Notice[]
     * @throws ServletException
     * @throws IOException
     */
    public void selectNotice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Notice[] notices = userService.selectNotice();
        for (Notice notice : notices) {
            if (notice.getDetail()!=null &&notice.getDetail().length() > 25)
                notice.setDetail(notice.getDetail().substring(0, 25) + "...");
        }
        String jsonString = JSON.toJSONString(notices);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

    /**
     * 查询个人任务总数 - 已废弃
     *
     * @param request  null
     * @param response count
     * @throws ServletException
     * @throws IOException
     */
    public void selectPersonTaskCount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = getUser(request, response).getUserId();
        int count = userService.selectPersonTaskCount(userId);
        String jsonString = JSON.toJSONString(count);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

    /**
     * 分页查询个人任务 - 已废弃
     *
     * @param request  当前页数+每页展示数据数
     * @param response PersonTask[]
     * @throws ServletException
     * @throws IOException
     */

    public void selectPersonTask(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int currentPage = Integer.parseInt(request.getParameter("currentPage"));
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        PersonTask[] personTasks = userService.selectPersonTask(currentPage, pageSize);
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
     * 根据任务id更新状态
     *
     * @param request  任务id
     * @param response success
     * @throws ServletException
     * @throws IOException
     */
    public void updatePersonTaskState(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = getUser(request, response).getUserId();
        BufferedReader br = request.getReader();
        String params = br.readLine();
        int pt_id = Integer.parseInt(params);
        userService.updatePersonTaskState(userId, pt_id);
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
        int userId = getUser(request, response).getUserId();
        int pageId = Integer.parseInt(request.getParameter("pageId"));
        int currentPage = Integer.parseInt(request.getParameter("currentPage"));
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        BufferedReader br = request.getReader();
        String params = br.readLine();
        PersonTask personTask = JSON.parseObject(params, PersonTask.class);
        PersonTask[] personTasks = userService.selectPersonTaskByCondition(pageId, userId, currentPage, pageSize, personTask);
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
        int userId = getUser(request, response).getUserId();
        int pageId = Integer.parseInt(request.getParameter("pageId"));
        int currentPage = Integer.parseInt(request.getParameter("currentPage"));
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        BufferedReader br = request.getReader();
        String params = br.readLine();
        PersonTask personTask = JSON.parseObject(params, PersonTask.class);
        int count = userService.selectPersonTaskCountByCondition(pageId, userId, currentPage, pageSize, personTask);
        String jsonString = JSON.toJSONString(count);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
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
        int userId = getUser(request, response).getUserId();
        BufferedReader br = request.getReader();
        String params = br.readLine();
        int pt_id = Integer.parseInt(params);
        int pageId = Integer.parseInt(request.getParameter("pageId"));
        userService.archiveTask(pageId, userId, pt_id);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write("success");
    }

    /**
     * 查询公告
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void selectAllNotice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int currentPage = Integer.parseInt(request.getParameter("currentPage"));
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        Notice[] notices = userService.selectAllNotice(currentPage, pageSize);
        String jsonString = JSON.toJSONString(notices);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

    /**
     * 查询公告数
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void selectNoticeCount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int count = userService.selectNoticeCount();
        String jsonString = JSON.toJSONString(count);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

    /**
     * 根据部门Id查询用户
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void selectUserByDeparmentId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int departmentId = getUser(request, response).getDepartmentId();
        User[] users = userService.selectUserByDeparmentId(departmentId);
        String jsonString = JSON.toJSONString(users);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

    /**
     * 存储头像
     * @param request 文件流
     * @param response null
     * @throws ServletException
     * @throws IOException
     * @throws FileUploadException
     */
    public void loadUserImg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, FileUploadException {
        response.setContentType("text/html;charset=utf-8");
        ServletFileUpload servletFileUpload = new ServletFileUpload(new DiskFileItemFactory());
        int userId = getUser(request, response).getUserId();
        List<FileItem> fileItems = servletFileUpload.parseRequest(request);
        for (FileItem fileItem : fileItems) {
            if(!fileItem.isFormField()){
                //获取上传文件名(带路径)
                String file = fileItem.getName();
                //截取路径名
                String Filename = file.substring(file.indexOf("\\") + 1);
                //保存在服务端
                //创建保存路径
                String Filename2 = userId+".jpg";
                //获取真实路径
                String failPath = "C:\\Users\\H1441400335\\Desktop\\javaweb\\ManageSystem\\src\\main\\webapp\\profilephoto\\"+Filename2;
                File fileNew = new File(failPath);
                fileNew.createNewFile();
                //读取并保存
                BufferedInputStream bis = new BufferedInputStream(fileItem.getInputStream());
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(fileNew));
                byte[] buffer = new byte[1024];
                int len;
                while((len = bis.read(buffer))!=-1){
                    bos.write(buffer,0,len);
                }
                bis.close();
                bos.close();
                System.out.println("上传完成");
            }
        }
    }

    /**
     * 修改name
     * @param request newname
     * @param response null
     * @throws ServletException
     * @throws IOException
     */
    public void updateUserName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        int userId = getUser(request, response).getUserId();
        String newName = request.getParameter("newName");
        try {
            userService.updateUserName(newName,userId);
        } catch (Exception e) {
            response.setContentType("text/json;charset=utf-8");
            response.getWriter().write("用户名已被占用或不合法");
        }
        resetSession(request, response);
    }

    /**
     * 修改email
     * @param request email
     * @param response null
     * @throws ServletException
     * @throws IOException
     */
    public void updateUserEmail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        int userId = getUser(request, response).getUserId();
        String newEmail = request.getParameter("newEmail");
        try {
            userService.updateUserEmail(newEmail,userId);
        } catch (Exception e) {
            response.setContentType("text/json;charset=utf-8");
            response.getWriter().write("邮箱已被占用或不合法");
        }
        resetSession(request, response);
    }
}
