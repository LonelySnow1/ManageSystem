package com.lonelySnow.service.impl;


import com.lonelySnow.mapper.DepartmentMapper;
import com.lonelySnow.mapper.UserMapper;
import com.lonelySnow.pojo.Department;
import com.lonelySnow.pojo.Notice;
import com.lonelySnow.pojo.PersonTask;
import com.lonelySnow.pojo.User;
import com.lonelySnow.service.DepartmentService;
import com.lonelySnow.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class DepartmentServiceImpl implements DepartmentService {

    SqlSessionFactory factory = SqlSessionFactoryUtils.getSqlSessionFactory();


    @Override
    public Department selectDepartment(int departmentId) {
        SqlSession sqlSession = factory.openSession();
        DepartmentMapper mapper = sqlSession.getMapper(DepartmentMapper.class);
        Department department = mapper.selectDepartment(departmentId);
        sqlSession.close();
        return department;
    }

    @Override
    public void resetManageCode(Department department) {
        SqlSession sqlSession = factory.openSession();
        DepartmentMapper mapper = sqlSession.getMapper(DepartmentMapper.class);
        mapper.resetManageCode(department);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void delectUser(int userId) {
        SqlSession sqlSession = factory.openSession();
        DepartmentMapper mapper = sqlSession.getMapper(DepartmentMapper.class);
        mapper.delectUser(userId);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public int selectMaxTaskId() {
        SqlSession sqlSession = factory.openSession();
        DepartmentMapper mapper = sqlSession.getMapper(DepartmentMapper.class);
        int maxId = mapper.selectMaxTaskId();
        sqlSession.close();
        return maxId;
    }

    @Override
    public int selectMaxDepartmentTaskId() {
        SqlSession sqlSession = factory.openSession();
        DepartmentMapper mapper = sqlSession.getMapper(DepartmentMapper.class);
        int maxId = mapper.selectMaxDepartmentTaskId();
        sqlSession.close();
        return maxId;
    }

    @Override
    public void addTotalPersonTask(int taskId,PersonTask pt) {
        SqlSession sqlSession = factory.openSession();
        DepartmentMapper mapper = sqlSession.getMapper(DepartmentMapper.class);
        mapper.addTotalPersonTask(taskId,pt);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void assignTask(int[] users, int taskId) {
        SqlSession sqlSession = factory.openSession();
        DepartmentMapper mapper = sqlSession.getMapper(DepartmentMapper.class);
        for(int userId:users){
            mapper.assignTask(userId,taskId);
            sqlSession.commit();
        }
        sqlSession.close();
    }

    @Override
    public Department[] selectAllDepartment() {
        SqlSession sqlSession = factory.openSession();
        DepartmentMapper mapper = sqlSession.getMapper(DepartmentMapper.class);
        Department[] departments = mapper.selectAllDepartment();
        sqlSession.close();
        return departments;
    }

    @Override
    public User[] selectAllUser() {
        SqlSession sqlSession = factory.openSession();
        DepartmentMapper mapper = sqlSession.getMapper(DepartmentMapper.class);
        User[] users = mapper.selectAllUser();
        sqlSession.close();
        return users;
    }

    @Override
    public void addNotice(Notice notice) {
        SqlSession sqlSession = factory.openSession();
        DepartmentMapper mapper = sqlSession.getMapper(DepartmentMapper.class);
        mapper.addNotice(notice);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void addTotalDepartmentTask(PersonTask pt) {
        SqlSession sqlSession = factory.openSession();
        DepartmentMapper mapper = sqlSession.getMapper(DepartmentMapper.class);
        mapper.addTotalDepartmentTask(pt);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void assignDepartmentTask(int[] departments, int taskId) {
        SqlSession sqlSession = factory.openSession();
        DepartmentMapper mapper = sqlSession.getMapper(DepartmentMapper.class);
        for(int departmentId: departments){
            mapper.assignDepartmentTask(departmentId,taskId);
            sqlSession.commit();
        }
        sqlSession.close();
    }

    @Override
    public void updatePersonTaskState(int departmentId,int pt_id) {
        SqlSession sqlSession = factory.openSession(true);
        DepartmentMapper mapper = sqlSession.getMapper(DepartmentMapper.class);
        mapper.updatePersonTaskState(departmentId,pt_id);
        sqlSession.close();
    }

    @Override
    public PersonTask[] selectPersonTaskByCondition(int pageId,int departmentId,int currentPage, int pageSize, PersonTask PT) {
        SqlSession sqlSession = factory.openSession();
        DepartmentMapper mapper = sqlSession.getMapper(DepartmentMapper.class);
        int begin = (currentPage - 1) * pageSize;
        int size = pageSize;
        PersonTask[] personTasks = mapper.selectPersonTaskByCondition(pageId,departmentId,begin,size,PT);
        sqlSession.close();
        return personTasks;
    }

    @Override
    public int selectPersonTaskCountByCondition(int pageId,int departmentId,int currentPage, int pageSize, PersonTask PT) {
        SqlSession sqlSession = factory.openSession();
        DepartmentMapper mapper = sqlSession.getMapper(DepartmentMapper.class);
        int begin = (currentPage - 1) * pageSize;
        int size = pageSize;
        int count = mapper.selectPersonTaskCountByCondition(pageId,departmentId,begin,size,PT);
        sqlSession.close();
        return count;
    }

    @Override
    public void archiveTask(int pageId,int departmentId,int taskId) {
        SqlSession sqlSession = factory.openSession(true);
        DepartmentMapper mapper = sqlSession.getMapper(DepartmentMapper.class);
        mapper.archiveTask(pageId,departmentId,taskId);
        sqlSession.close();
    }

}
