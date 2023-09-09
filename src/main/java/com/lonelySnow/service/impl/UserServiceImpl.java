package com.lonelySnow.service.impl;


import com.lonelySnow.mapper.UserMapper;
import com.lonelySnow.pojo.Department;
import com.lonelySnow.pojo.Notice;
import com.lonelySnow.pojo.PersonTask;
import com.lonelySnow.pojo.User;
import com.lonelySnow.service.UserService;
import com.lonelySnow.util.SendMailUtils;
import com.lonelySnow.util.SqlSessionFactoryUtils;
import org.apache.commons.mail.HtmlEmail;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class UserServiceImpl implements UserService {

    //1.初始化工厂-创建对应的SqlSessionFactory 工厂对象
    SqlSessionFactory factory = SqlSessionFactoryUtils.getSqlSessionFactory();
    SendMailUtils sm = new SendMailUtils();

    @Override
    public User selectUser(int userId) {
        SqlSession sqlSession = factory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.SelectUser(userId);
        sqlSession.close();
        return user;
    }

    @Override
    public User selectAccountExist(User user1) {
        //2.获取 SqlSession对象
        SqlSession sqlSession = factory.openSession();
        //3.获取BrandMapper
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        //4.调用方法
        User user = mapper.selectAccountExist(user1);

        //5.释放资源
        sqlSession.close();

        return user;
    }

    @Override
    public String selectDepartmentNameFromID(int userId) {
        SqlSession sqlSession = factory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        String departmentName = mapper.selectDepartmentNameFromID(userId);
        sqlSession.close();
        return departmentName;
    }

    @Override
    public Department selectSignDepartment(String managecode) {
        //2.获取 SqlSession对象
        SqlSession sqlSession = factory.openSession();
        //3.获取BrandMapper
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        //4.调用方法
        Department department = mapper.selectSignDepartment(managecode);
        sqlSession.close();
        return department;
    }

    @Override
    public String sendEmailCode(String email) {
        // 创建HtmlEmail对象
        HtmlEmail send = new HtmlEmail();
        // 获取验证码
        String achieveCode = sm.achieveCode();
        try {
            send.setHostName("smtp.office365.com");// 服务器名称
            send.setSmtpPort(587);// 端口号
            send.setStartTLSEnabled(true);
            send.setStartTLSRequired(true);// 开启服务
            send.setCharset("utf-8");// 设置字符集
            System.out.println(email);
            send.addTo(email); // 接收者的Eamil
            send.setFrom("参数是发送者的Eamil", "参数是发送者的昵称");// 第一个参数是发送者的Eamil   第二个参数是发送者的昵称
            // 授权码
            send.setAuthentication("发送者的Eamil", "授权码");// 第一个参数是发送者的Eamil   第二个参数是授权码
            send.setSubject("[用户管理系统] 请通过验证码验证您的邮箱"); // 主题
            send.setMsg("尊敬的用户：\n感谢您使用我们的服务!\n您的验证码如下：\n"+ achieveCode + "\n\t祝您使用愉快!\nLonelySnow[ManageSystem]\n[本邮件为系统自动发送，请勿直接回复]"); // 设置内容
            send.send();// 发送信息
            System.out.println("发送成功");
        } catch (Exception ignored) {}
        return achieveCode;
    }

    @Override
    public void addUser(User user) {
        SqlSession sqlSession = factory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        mapper.addUser(user);
        sqlSession.commit();//提交事务
        sqlSession.close();
    }

    @Override
    public User selectExistName(String name) {
        SqlSession sqlSession = factory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User s = mapper.selectExistName(name);
        sqlSession.close();
        return s;
    }

    @Override
    public User selectExistEmail(String email) {
        SqlSession sqlSession = factory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User s = mapper.selectExistEmail(email);
        sqlSession.close();
        return s;
    }

    @Override
    public void resetPassword(User user) {
        SqlSession sqlSession = factory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        mapper.resetPassword(user);
        System.out.println(user.getEmail());
        sqlSession.commit();//提交事务
        sqlSession.close();
    }

    @Override
    public Notice[] selectNotice() {
        SqlSession sqlSession = factory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        Notice[] notices = mapper.selectNotice();
        sqlSession.close();
        return notices;
    }

    @Override
    public int selectPersonTaskCount(int userId) {
        SqlSession sqlSession = factory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        int count = mapper.selectPersonTaskCount(userId);
        sqlSession.close();
        return count;
    }

    @Override
    public PersonTask[] selectPersonTask(int currentPage,int pageSize) {
        int begin = (currentPage - 1) * pageSize;
        int size = pageSize;
        SqlSession sqlSession = factory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        PersonTask[] pt = mapper.selectPersonTask(begin,size);
        sqlSession.close();
        return pt;
    }

    @Override
    public void updatePersonTaskState(int userId,int pt_id) {
        SqlSession sqlSession = factory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        mapper.updatePersonTaskState(userId,pt_id);
        sqlSession.commit();//提交事务
        sqlSession.close();
    }

    @Override
    public PersonTask[] selectPersonTaskByCondition(int pageId,int userId,int currentPage, int pageSize, PersonTask PT) {
        SqlSession sqlSession = factory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        int begin = (currentPage - 1) * pageSize;
        int size = pageSize;
        PersonTask[] personTasks = mapper.selectPersonTaskByCondition(pageId,userId,begin,size,PT);
        sqlSession.close();
        return personTasks;
    }

    @Override
    public int selectPersonTaskCountByCondition(int pageId,int userId,int currentPage, int pageSize, PersonTask PT) {
        SqlSession sqlSession = factory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        int begin = (currentPage - 1) * pageSize;
        int size = pageSize;
        int count = mapper.selectPersonTaskCountByCondition(pageId,userId,begin,size,PT);
        sqlSession.close();
        return count;
    }

    @Override
    public void archiveTask(int pageId,int userId,int taskId) {
        SqlSession sqlSession = factory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        mapper.archiveTask(pageId,userId,taskId);
        sqlSession.commit();//提交事务
        sqlSession.close();
    }

    @Override
    public Notice[] selectAllNotice(int currentPage, int pageSize) {
        int begin = (currentPage - 1) * pageSize;
        int size = pageSize;
        SqlSession sqlSession = factory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        Notice[] nt = mapper.selectAllNotice(begin,size);
        sqlSession.close();
        return nt;
    }

    @Override
    public int selectNoticeCount() {
        SqlSession sqlSession = factory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        int count = mapper.selectNoticeCount();
        sqlSession.close();
        return count;
    }

    @Override
    public User[] selectUserByDeparmentId(int departmentId) {
        SqlSession sqlSession = factory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User[] users = mapper.selectUserByDeparmentId(departmentId);
        sqlSession.close();
        return users;
    }

    @Override
    public void updateUserName(String newName, int userId) {
        SqlSession sqlSession = factory.openSession(true);
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        mapper.updateUserName(newName,userId);
        sqlSession.close();
    }

    @Override
    public void updateUserEmail(String newEmail, int userId) {
        SqlSession sqlSession = factory.openSession(true);
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        mapper.updateUserEmail(newEmail,userId);
        sqlSession.close();
    }

    @Override
    public void updateTime(int departmentId) {
        SqlSession sqlSession = factory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        mapper.updateTime(departmentId);
        sqlSession.commit();//提交事务
        sqlSession.close();
    }

}
