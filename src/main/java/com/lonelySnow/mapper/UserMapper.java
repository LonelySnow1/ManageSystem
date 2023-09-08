package com.lonelySnow.mapper;

import com.lonelySnow.pojo.Department;
import com.lonelySnow.pojo.Notice;
import com.lonelySnow.pojo.PersonTask;
import com.lonelySnow.pojo.User;
import org.apache.ibatis.annotations.*;

public interface UserMapper {

   /**
    * 根据Id查询user对象
    */
   @Select("select * from user where u_id = #{userId}")
   @ResultMap("userResultMap")
   User SelectUser(@Param("userId") int userId);

   /**
    * 检查邮箱密码对应的 user 对象
    */
   @Select("Select * from user where email = #{email} and password = #{password}")
   @ResultMap("userResultMap")
   User selectAccountExist (User user);

   @Select("select name from department  where d_id = #{departmentId}")
   String selectDepartmentNameFromID(@Param("departmentId") int departmentId);

   /**
    * 查找准入码对应的 department 对象
    */
   @Select("SELECT * from department where managecode = #{managecode}")
   @ResultMap("departmentResultMap")
   Department selectSignDepartment(String managecode);

   /**
    * 添加成员
    */
   @Insert("INSERT INTO user VALUES (null,#{departmentId},#{name},#{password},#{position},#{email})")
   @ResultMap("userResultMap")
   void addUser(User user);

   /**
    * 查询是否重复
    * @param String name/email
    * @return
    */
   @Select("SELECT u_id from user where name = #{name}")
   @ResultMap("userResultMap")
   User selectExistName(String String);

   @Select("SELECT u_id from user where email=#{email}")
   @ResultMap("userResultMap")
   User selectExistEmail(String String);

   /**
    * 根据邮箱修改密码
    * @param user 邮箱对应的用户
    */
   @Update("UPDATE user set password = #{password} where email = #{email}")
   @ResultMap("userResultMap")
   void resetPassword(User user);

   /**
    * 查询三条最新的公告
    * @return
    */
   @Select("SELECT * FROM notice ORDER BY id DESC limit 3")
   Notice[] selectNotice();

   /**
    * 查询任务总数
    * @return
    */
   @Select("SELECT count(*) from p_task where u_id = #{userId}")
   int selectPersonTaskCount(@Param("userId") int userId);

   /**
    * 查询个人任务
    * @param begin 开始序号
    * @param size 查几个
    * @return
    */

   @Select("select tp.pt_id,tp.title,tp.briefing,tp.detail,tp.start_time,tp.finish_time,pt.state from p_task pt join total_pt tp on pt.pt_id = tp.pt_id where u_id = 2 ORDER BY pt.state limit #{begin},#{size}")
   @ResultMap("personTaskResultMap")
   PersonTask[] selectPersonTask(@Param("begin") int begin, @Param("size") int size);

   /**
    * 根据任务ID，切换任务状态
    * @param taskID
    */
   @Update("UPDATE p_task set state = 1 - p_task.state where u_id = #{userId} and pt_id = #{taskID}")
   void updatePersonTaskState(@Param("userId") int userId,@Param("taskID") int taskID);

   /**
    * 按照搜索栏条件查询指定任务
    * @param personTask
    * @return
    */
   PersonTask[] selectPersonTaskByCondition(@Param("pageId") int pageId,@Param("userId") int userId,@Param("begin") int begin,@Param("size") int size,@Param("PT") PersonTask personTask);

   /**
    * 根据搜索栏条件查询任务总数
    * @param personTask
    * @return
    */
   int selectPersonTaskCountByCondition(@Param("pageId") int pageId,@Param("userId") int userId,@Param("begin") int begin,@Param("size") int size,@Param("PT") PersonTask personTask);

   /**
    * 根据ID设置任务归档情况
    * @param taskID
    */
   void archiveTask(@Param("pageId") int pageId,@Param("userId") int userId,@Param("taskID") int taskID);

   /**
    * 根据分页数查询公告
    * @param begin
    * @param size
    * @return
    */
   @Select("select * from notice limit #{begin},#{size}")
   Notice[] selectAllNotice(@Param("begin") int begin, @Param("size") int size);

   /**
    * 查询公告总数
    * @return
    */
   @Select("SELECT count(*) from notice")
   int selectNoticeCount();

   /**
    * 根据部门id查询成员
    * @param departmentId
    * @return
    */
   @Select("select * from user where d_id = #{departmentId} ORDER BY position DESC")
   @ResultMap("userResultMap")
   User[] selectUserByDeparmentId(@Param("departmentId") int departmentId);

   @Update("update user set name = #{newName} where u_id = #{userId}")
   void updateUserName(@Param("newName") String newName, @Param("userId") int userId);

   @Update("update user set email = #{newEmail} where u_id = #{userId}")
   void updateUserEmail(@Param("newEmail") String newEmail, @Param("userId") int userId);

   @Update("update department set time = time-1 where d_id = #{departmentId}")
   @ResultMap("departmentResultMap")
   void updateTime(@Param("departmentId")int departmentId);
}
