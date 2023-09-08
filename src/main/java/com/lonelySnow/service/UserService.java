package com.lonelySnow.service;

import com.lonelySnow.pojo.Department;
import com.lonelySnow.pojo.Notice;
import com.lonelySnow.pojo.PersonTask;
import com.lonelySnow.pojo.User;

public interface UserService {

    User selectUser(int userId);

    User selectAccountExist(User user);

    String selectDepartmentNameFromID(int userId);

    Department selectSignDepartment(String managecode);

    String sendEmailCode(String email);

    void addUser(User user);

    User selectExistName(String String);
    User selectExistEmail(String String);

    void resetPassword(User user);

    Notice[] selectNotice();

    int selectPersonTaskCount(int userId);

    PersonTask[] selectPersonTask(int currentPage,int pageSize);

    void updatePersonTaskState(int userId,int pt_id);

    PersonTask[] selectPersonTaskByCondition(int pageId,int userId,int currentPage,int pageSize,PersonTask PT);

    int selectPersonTaskCountByCondition(int pageId,int userId,int currentPage,int pageSize,PersonTask PT);

    void archiveTask(int pageId,int userId,int taskId);

    Notice[] selectAllNotice(int currentPage,int pageSize);

    int selectNoticeCount();

    User[] selectUserByDeparmentId(int departmentId);

    void updateUserName(String newName,int userId);

    void updateUserEmail(String newEmail,int userId);

    void updateTime(int departmentId);

}
