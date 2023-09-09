# 一.版本问题（Version）：

当前网站版本：Ver 1.5 

版本说明：实现了网页的基本功能，对网站进行了许多问题的勘误和修改，进行了部分功能的调整

下一版本预告：进一步完善页面的美观性，及更多功能的实现



本项目还包含以下内容，有需要可自行查看

1.项目需求文档

2.SQL建表语句

3.ER图

4.前后端接口文档

---

Current website version: Ver 1.5

Version description: To achieve the basic functions of the web page, many problems of the website errata and modification, the adjustment of some functions

Next version preview: Further improve the aesthetics of the page, and more functions

This project also contains the following content, which can be viewed by yourself if necessary

1.Project requirements document

2.SQL table construction clause

3.ER diagram

4.Front and rear interface documents

# 二.客制化（customization）

**在使用本网站前，使用者需要对网站进行个人功能的调整以及修改**

需要修改的地方如下：



**Before using this website, users need to adjust and modify the website's personal functions **

The changes need to be made as follows:



1.src/main/resources/mybatis-config.xml ——————数据库链接（Database link）

```xml
<!--                下面改成你自己的用户名和密码-->
                <property name="username" value="root"/>
                <property name="password" value="123456"/>
```

2.src/main/java/com/lonelySnow/service/impl/UserServiceImpl.java ——————邮箱验证码发送（Send Email）

```java
 public String sendEmailCode(String email) {
        // 创建HtmlEmail对象
        HtmlEmail send = new HtmlEmail();
        // 获取验证码
        String achieveCode = sm.achieveCode();
        try {
            send.setHostName("smtp.office365.com");// 服务器名称
            send.setSmtpPort(587);// 端口号
            send.setStartTLSEnabled(true);
            send.setStartTLSRequired(true);// 开启服务
            send.setCharset("utf-8");// 设置字符集
            System.out.println(email);
            send.addTo(email); // 接收者的Eamil
            send.setFrom("参数是发送者的Eamil", "参数是发送者的昵称");// 第一个参数是发送者的Eamil   第二个参数是发送者的昵称
            // 授权码
            send.setAuthentication("发送者的Eamil", "授权码");// 第一个参数是发送者的Eamil   第二个参数是授权码
            send.setSubject("[用户管理系统] 请通过验证码验证您的邮箱"); // 主题
            send.setMsg("尊敬的用户：\n感谢您使用我们的服务!\n您的验证码如下：\n"+ achieveCode + "\n\t祝您使用愉快!\nLonelySnow[ManageSystem]\n[本邮件为系统自动发送，请勿直接回复]"); // 设置内容
            send.send();// 发送信息
            System.out.println("发送成功");
        } catch (Exception ignored) {}
        return achieveCode;
    }
```

邮件服务器可以去对应官网查看POP3/SMTP服务相关信息

只有开启POP3/SMTP功能之后才可以使用邮箱发送验证码

发送者的昵称可以随意命名，不要求与邮箱名称相同



The mail server can view POP3/SMTP service information on the corresponding official website

The verification code can be sent by email only after the POP3/SMTP function is enabled

The sender's nickname is optional and does not have to be the same as the mailbox name

---