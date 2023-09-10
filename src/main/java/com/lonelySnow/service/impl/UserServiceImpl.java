package com.lonelySnow.service.impl;

import com.lonelySnow.mapper.UserMapper;
import com.lonelySnow.service.UserService;
import com.lonelySnow.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class UserServiceImpl implements UserService {

    SqlSessionFactory factory = SqlSessionFactoryUtils.getSqlSessionFactory();

    @Override
    public String HelloWorld() {
        SqlSession sqlSession = factory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        String Str = mapper.HelloWorld();
        return Str;
    }
}
