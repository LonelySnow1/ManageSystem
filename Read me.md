# 一.版本问题：

当前网站版本：Ver 1.5 

版本说明：实现了网页的基本功能，对网站进行了许多问题的勘误和修改，进行了部分功能的调整

下一版本预告：进一步完善页面的美观性，及更多功能的实现



本项目还包含以下内容，有需要可自行查看

1.项目需求文档

2.SQL建表语句

3.ER图

4.前后端接口文档



# 二.客制化

**在使用本网站前，使用者需要对网站进行个人功能的调整以及修改**

需要修改的地方如下：

1.src/main/resources/mybatis-config.xml ——————数据库链接

![](C:\Users\H1441400335\AppData\Roaming\Typora\typora-user-images\image-20230909151457873.png)



2.src/main/java/com/lonelySnow/service/impl/UserServiceImpl.java ——————邮箱验证码发送

![image-20230909152130715](C:\Users\H1441400335\AppData\Roaming\Typora\typora-user-images\image-20230909152130715.png)

邮件服务器可以去对应官网查看POP3/SMTP服务相关信息

只有开启POP3/SMTP功能之后才可以使用邮箱发送验证码

发送者的昵称可以随意命名，不要求与邮箱名称相同



# 