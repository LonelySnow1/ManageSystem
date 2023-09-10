package com.lonelySnow.mapper;


import org.apache.ibatis.annotations.Select;

public interface UserMapper {
   @Select("select * from helloworld")
   String HelloWorld();
}
