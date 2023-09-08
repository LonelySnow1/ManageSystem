package com.lonelySnow.mapper;

import com.lonelySnow.pojo.Department;
import com.lonelySnow.pojo.Notice;
import com.lonelySnow.pojo.PersonTask;
import com.lonelySnow.pojo.User;
import org.apache.ibatis.annotations.*;

public interface DepartmentMapper {

   @Select("select * from department where d_id = #{departmentId}")
   @ResultMap("departmentResultMap")
   Department selectDepartment(@Param("departmentId") int departmentId);

   @Update("update department set managecode = #{manageCode},time = #{time} where d_id = #{departmentId}")
   @ResultMap("departmentResultMap")
   void resetManageCode(Department department);

   @Delete("delete from user where u_id=#{userId}")
   void delectUser(@Param("userId") int userId);

   @Select("select max(pt_id) from total_pt")
   int selectMaxTaskId();

   @Select("select max(dt_id) from total_dt")
   int selectMaxDepartmentTaskId();

   @Insert("insert into total_pt values (#{taskId},#{pt.taskId},#{pt.title},#{pt.briefing},#{pt.detail},#{pt.state},#{pt.startTime},#{pt.finishTime})")
   @ResultMap("personTaskResultMap")
   void addTotalPersonTask(@Param("taskId") int taskId,@Param("pt") PersonTask pt);

   @Insert("insert into p_task values (#{userId},#{taskId},0)")
   void assignTask(@Param("userId") int userId,@Param("taskId") int taskId);

   @Select("select * from department where d_id != 1")
   @ResultMap("departmentResultMap")
   Department[] selectAllDepartment();

   @Select("select * from user where u_id != 1 order by d_id,position ")
   @ResultMap("userResultMap")
   User[] selectAllUser();

   @Insert("insert into notice values (null,#{time},#{title},#{detail})")
   void addNotice(Notice notice);

   @Insert("insert into total_dt values (#{taskId},#{title},#{briefing},#{detail},#{state},#{startTime},#{finishTime})")
   @ResultMap("personTaskResultMap")
   void addTotalDepartmentTask(PersonTask pt);

   @Insert("insert into d_task values (#{departmentId},#{taskId},0)")
   void assignDepartmentTask(@Param("departmentId") int departmentId,@Param("taskId") int taskId);

   /**
    * 根据任务ID，切换任务状态
    * @param taskID
    */
   @Update("UPDATE d_task set state = 1 - d_task.state where d_id = #{departmentId} and dt_id = #{taskID}")
   void updatePersonTaskState(@Param("departmentId") int departmentId,@Param("taskID") int taskID);

   /**
    * 按照搜索栏条件查询指定任务
    * @param personTask
    * @return
    */
   PersonTask[] selectPersonTaskByCondition(@Param("pageId") int pageId,@Param("departmentId") int departmentId,@Param("begin") int begin,@Param("size") int size,@Param("PT") PersonTask personTask);

   /**
    * 根据搜索栏条件查询任务总数
    * @param personTask
    * @return
    */
   int selectPersonTaskCountByCondition(@Param("pageId") int pageId,@Param("departmentId") int departmentId,@Param("begin") int begin,@Param("size") int size,@Param("PT") PersonTask personTask);

   /**
    * 根据ID设置任务归档情况
    * @param taskID
    */
   void archiveTask(@Param("pageId") int pageId,@Param("departmentId") int departmentId,@Param("taskID") int taskID);
}
