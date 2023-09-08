package com.lonelySnow.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *  替换httpServlet,根据请求的最后一段路径来进行方法分发
 */

public class BaseServlet extends HttpServlet {

    //根据请求的最后一段路径来进行方法分发
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取请求路径
        String requestURI = req.getRequestURI();
        /*
            /brand-case/brand/selectAll
         */

        //2.获取最后一段路径-方法名
        int index = requestURI.lastIndexOf("/");//获取字符最后出现的路径
        String  methodName = requestURI.substring(index+1);//分割出最后的字符串

        //3.执行方法
        //3.1 获取BrandServlet / UserServlet 字节码对象 Class

        //System.out.println(this);
        //此处this 代表调用service方法的对象，即Baseservlet的子类，有可能是userServlet，有可能是brandServlet
        Class<? extends BaseServlet> cls = this.getClass();



        //3.2 获取方法 Method 对象
        try {
            Method method = cls.getMethod(methodName,HttpServletRequest.class, HttpServletResponse.class);
            //3.3 执行方法
            method.invoke(this,req,resp);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }
}
