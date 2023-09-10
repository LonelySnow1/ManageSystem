package com.lonelySnow.service;

import com.lonelySnow.pojo.Department;
import com.lonelySnow.pojo.Notice;
import com.lonelySnow.pojo.PersonTask;
import com.lonelySnow.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface DepartmentService {

    Department selectDepartment(int depaermentId);

    void resetManageCode(Department department);

    void delectUser(int userId);

    int selectMaxTaskId();

    int selectMaxDepartmentTaskId();
    void addTotalPersonTask(int taskId,PersonTask pt);

    void assignTask(int[] users,int taskId);

    Department[] selectAllDepartment();

    User[] selectAllUser();

    void addNotice(Notice notice);

    void addTotalDepartmentTask(PersonTask pt);

    void assignDepartmentTask(int[] departments,int taskId);

    void updatePersonTaskState(int departmentId,int pt_id);

    PersonTask[] selectPersonTaskByCondition(int pageId,int departmentId,int currentPage,int pageSize,PersonTask PT);

    int selectPersonTaskCountByCondition(int pageId,int departmentId,int currentPage,int pageSize,PersonTask PT);

    void archiveTask(int pageId,int departmentId,int taskId);
}
