package com.lonelySnow.web.servlet;


import com.alibaba.fastjson.JSON;
import com.lonelySnow.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/user/*")
public class UserServlet extends BaseServlet {

    private UserService userService = new com.lonelySnow.service.impl.UserServiceImpl();

    public void HelloWorld(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String Str = userService.HelloWorld();
        String jsonString = JSON.toJSONString(Str);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }
}
