***本文档由 Rap2 (https://github.com/thx/rap2-delos) 生成***

***本项目仓库：[http://rap2.taobao.org/repository/editor?id=314082](http://rap2.taobao.org/repository/editor?id=314082) ***

***生成日期：2023-09-09 08:01:55***

# 仓库：企业人员管理与任务分配系统
## 模块：登录模块
### 接口：登录状态检测
* 地址：/user/checkSession
* 类型：GET
* 状态码：200
* 简介：检查session中是否存在user这项，并返回
* Rap地址：[http://rap2.taobao.org/repository/editor?id=314082&mod=540984&itf=2428619](http://rap2.taobao.org/repository/editor?id=314082&mod=540984&itf=2428619)
* 请求接口格式：

```

```

* 返回接口格式：

```
└─ user: Object 
   ├─ userId: Number 
   ├─ departmentId: Number 
   ├─ name: String 
   ├─ password: String 
   ├─ position: String 
   └─ email: String 

```


### 接口：检查是否存储密码
* 地址：/user/getCookies
* 类型：GET
* 状态码：200
* 简介：获取cookie中user数据
* Rap地址：[http://rap2.taobao.org/repository/editor?id=314082&mod=540984&itf=2428628](http://rap2.taobao.org/repository/editor?id=314082&mod=540984&itf=2428628)
* 请求接口格式：

```

```

* 返回接口格式：

```
└─ userCookies: String 

```


### 接口：登陆检测
* 地址：/user/loginCheck
* 类型：POST
* 状态码：200
* 简介：检查是否可以登录
* Rap地址：[http://rap2.taobao.org/repository/editor?id=314082&mod=540984&itf=2428630](http://rap2.taobao.org/repository/editor?id=314082&mod=540984&itf=2428630)
* 请求接口格式：

```
├─ vcode: String (验证码)
└─ user: Object 
   ├─ email: String 
   └─ password: String 

```

* 返回接口格式：

```
├─ v-false: Boolean (验证码校验错误)
└─ user: Object (成功登录)
   ├─ userId: String 
   ├─ departmentId: String 
   ├─ name: String 
   ├─ password: String 
   ├─ position: String 
   └─ email: String 

```


### 接口：用户注册
* 地址：/user/signCheck
* 类型：POST
* 状态码：200
* 简介：用户根据提供信息注册账号
* Rap地址：[http://rap2.taobao.org/repository/editor?id=314082&mod=540984&itf=2428901](http://rap2.taobao.org/repository/editor?id=314082&mod=540984&itf=2428901)
* 请求接口格式：

```
├─ managecode: String (准入码)
└─ user: Object 
   ├─ name: String 
   ├─ email: String 
   └─ password: String 

```

* 返回接口格式：

```
├─ 该昵称已被使用: String (昵称重复)
├─ 该邮箱已被使用: String (邮箱重复)
├─ 准入码无效: String 
└─ success: String (注册成功)

```


### 接口：发送验证邮件
* 地址：/user/sendEmail
* 类型：POST
* 状态码：200
* 简介：无
* Rap地址：[http://rap2.taobao.org/repository/editor?id=314082&mod=540984&itf=2428904](http://rap2.taobao.org/repository/editor?id=314082&mod=540984&itf=2428904)
* 请求接口格式：

```
└─ email: String (用户邮箱地址)

```

* 返回接口格式：

```
└─ achieveCode: String (邮箱验证码)

```


### 接口：忘记密码
* 地址：/user/resetPassword
* 类型：POST
* 状态码：200
* 简介：无
* Rap地址：[http://rap2.taobao.org/repository/editor?id=314082&mod=540984&itf=2428905](http://rap2.taobao.org/repository/editor?id=314082&mod=540984&itf=2428905)
* 请求接口格式：

```
└─ user: Object 
   ├─ email: String 
   └─ password: String (新密码)

```

* 返回接口格式：

```
└─ success: String (成功)

```


### 接口：退出登录
* 地址：/user/delectSession
* 类型：GET
* 状态码：200
* 简介：删除登录信息
* Rap地址：[http://rap2.taobao.org/repository/editor?id=314082&mod=540984&itf=2428916](http://rap2.taobao.org/repository/editor?id=314082&mod=540984&itf=2428916)
* 请求接口格式：

```

```

* 返回接口格式：

```

```


## 模块：用户模块
### 接口：获取最新公告
* 地址：/user/selectNotice
* 类型：GET
* 状态码：200
* 简介：获取最新的三条公告
* Rap地址：[http://rap2.taobao.org/repository/editor?id=314082&mod=541026&itf=2428907](http://rap2.taobao.org/repository/editor?id=314082&mod=541026&itf=2428907)
* 请求接口格式：

```

```

* 返回接口格式：

```
└─ notices: Array (数组对象)
   ├─ title: String (标题)
   ├─ detail: String (内容)
   └─ time: String (发布时间)

```


### 接口：切换任务状态
* 地址：/user/updatePersonTaskState
* 类型：POST
* 状态码：200
* 简介：切换任务是否完成
* Rap地址：[http://rap2.taobao.org/repository/editor?id=314082&mod=541026&itf=2428909](http://rap2.taobao.org/repository/editor?id=314082&mod=541026&itf=2428909)
* 请求接口格式：

```
└─ taskId: Number (个人任务id)

```

* 返回接口格式：

```
└─ success: String (成功)

```


### 接口：查询任务总数
* 地址：/user/selectPersonTaskCountByCondition
* 类型：POST
* 状态码：200
* 简介：根据搜索栏分页查询任务总数
* Rap地址：[http://rap2.taobao.org/repository/editor?id=314082&mod=541026&itf=2428910](http://rap2.taobao.org/repository/editor?id=314082&mod=541026&itf=2428910)
* 请求接口格式：

```
├─ pageId: Number (网页id)
├─ currentPage: Number (当前页码)
├─ pageSize: Number (每页展示条数)
└─ PerosonTask: Object (查询信息)
   ├─ title: String 
   ├─ startTime: String 
   ├─ finishTime: String 
   └─ state: Number 

```

* 返回接口格式：

```
└─ count: Number (总数)

```


### 接口：查询任务
* 地址：/user/selectPersonTaskByCondition
* 类型：POST
* 状态码：200
* 简介：根据网页ID查询  1：正常 2： 归档<br>
搜索栏分页查询任务
* Rap地址：[http://rap2.taobao.org/repository/editor?id=314082&mod=541026&itf=2428913](http://rap2.taobao.org/repository/editor?id=314082&mod=541026&itf=2428913)
* 请求接口格式：

```
├─ pageId: Number (网页id)
├─ currentPage: Number (当前页码)
├─ pageSize: Number (每页展示条数)
└─ PerosonTask: Object (查询信息)
   ├─ title: String 
   ├─ startTime: String 
   ├─ finishTime: String 
   └─ state: Number 

```

* 返回接口格式：

```
└─ taskData: Array (任务对象数组)
   ├─ startTime: String 
   ├─ finishTime: String 
   ├─ title: String 
   ├─ briefing: String 
   ├─ detail: String 
   ├─ state: Number 
   └─ taskId: Number 

```


### 接口：任务归档
* 地址：/user/archiveTask
* 类型：POST
* 状态码：200
* 简介：根据页面ID
切换任务状态为 是否归档<br>
取消归档后状态为已完成
* Rap地址：[http://rap2.taobao.org/repository/editor?id=314082&mod=541026&itf=2428915](http://rap2.taobao.org/repository/editor?id=314082&mod=541026&itf=2428915)
* 请求接口格式：

```
├─ pageId: Number (网页ID)
└─ taskId: Number (用户任务ID)

```

* 返回接口格式：

```
└─ success: String (成功)

```


### 接口：查询公告总数
* 地址：/user/selectNoticeCount
* 类型：GET
* 状态码：200
* 简介：无
* Rap地址：[http://rap2.taobao.org/repository/editor?id=314082&mod=541026&itf=2428920](http://rap2.taobao.org/repository/editor?id=314082&mod=541026&itf=2428920)
* 请求接口格式：

```

```

* 返回接口格式：

```
└─ count: Number (公告总数)

```


### 接口：查询公告
* 地址：/user/selectAllNotice
* 类型：GET
* 状态码：200
* 简介：无
* Rap地址：[http://rap2.taobao.org/repository/editor?id=314082&mod=541026&itf=2428922](http://rap2.taobao.org/repository/editor?id=314082&mod=541026&itf=2428922)
* 请求接口格式：

```
├─ currentPage: String (当前页码)
└─ pageSize: String (每页条数)

```

* 返回接口格式：

```
└─ notices: Array (公告对象数组)
   ├─ id: String 
   ├─ title: String 
   ├─ detail: String 
   ├─ time: String 
   └─ briefing: String 

```


### 接口：查询同事
* 地址：/user/selectUserByDeparmentId
* 类型：GET
* 状态码：200
* 简介：根据部门Id部门同事和部门总管
* Rap地址：[http://rap2.taobao.org/repository/editor?id=314082&mod=541026&itf=2428923](http://rap2.taobao.org/repository/editor?id=314082&mod=541026&itf=2428923)
* 请求接口格式：

```

```

* 返回接口格式：

```
└─ userData: Array 
   ├─ userId: String 
   ├─ departmentId: String 
   ├─ name: String 
   ├─ password: String 
   ├─ position: String 
   └─ email: String 

```


### 接口：查询部门名称
* 地址：/user/selectDepartmentNameFromID
* 类型：GET
* 状态码：200
* 简介：根据用户部门id查询部门名称
* Rap地址：[http://rap2.taobao.org/repository/editor?id=314082&mod=541026&itf=2428926](http://rap2.taobao.org/repository/editor?id=314082&mod=541026&itf=2428926)
* 请求接口格式：

```

```

* 返回接口格式：

```
└─ departmentName: String (部门名称)

```


### 接口：修改用户名称
* 地址：/user/updateUserName
* 类型：GET
* 状态码：200
* 简介：检测是否重名并修改
* Rap地址：[http://rap2.taobao.org/repository/editor?id=314082&mod=541026&itf=2428927](http://rap2.taobao.org/repository/editor?id=314082&mod=541026&itf=2428927)
* 请求接口格式：

```
└─ newName: String (新名称)

```

* 返回接口格式：

```
└─ 用户名已被占用或不合法: String (用户名重复)

```


### 接口：修改用户邮箱
* 地址：/user/updateUserEmail
* 类型：GET
* 状态码：200
* 简介：查询邮箱重复并修改
* Rap地址：[http://rap2.taobao.org/repository/editor?id=314082&mod=541026&itf=2428928](http://rap2.taobao.org/repository/editor?id=314082&mod=541026&itf=2428928)
* 请求接口格式：

```

```

* 返回接口格式：

```
└─ 邮箱已被占用或不合法: String (邮箱重复)

```


### 接口：修改用户头像
* 地址：/user/loadUserImg
* 类型：GET
* 状态码：200
* 简介：将用户头像存储到项目路径下<br>
命名规则为 用户ID.jpg
* Rap地址：[http://rap2.taobao.org/repository/editor?id=314082&mod=541026&itf=2428933](http://rap2.taobao.org/repository/editor?id=314082&mod=541026&itf=2428933)
* 请求接口格式：

```

```

* 返回接口格式：

```

```


## 模块：管理模块
### 接口：修改部门任务状态
* 地址：/department/updatePersonTaskState
* 类型：POST
* 状态码：200
* 简介：切换任务是否完成状态
* Rap地址：[http://rap2.taobao.org/repository/editor?id=314082&mod=541030&itf=2428952](http://rap2.taobao.org/repository/editor?id=314082&mod=541030&itf=2428952)
* 请求接口格式：

```
└─ taskID: Number (部门任务ID)

```

* 返回接口格式：

```
└─ success: String (成功)

```


### 接口：查询部门任务总数
* 地址：/department/selectPersonTaskCountByCondition
* 类型：POST
* 状态码：200
* 简介：页面id 1： 正常任务<br>
页面id 2： 归档任务<br>
根据搜索栏分页查询部门任务
* Rap地址：[http://rap2.taobao.org/repository/editor?id=314082&mod=541030&itf=2428955](http://rap2.taobao.org/repository/editor?id=314082&mod=541030&itf=2428955)
* 请求接口格式：

```
├─ pageId: Number 
├─ currentPage: Number 
├─ pageSize: Number 
└─ PersonTask: Object 
   ├─ title: String 
   ├─ startTime: String 
   ├─ finishTime: String 
   └─ state: Number 

```

* 返回接口格式：

```
└─ count: Number (任务总数)

```


### 接口：查询部门任务
* 地址：/department/selectPersonTaskByCondition
* 类型：POST
* 状态码：200
* 简介：页面id 1： 正常任务<br>
页面id 2： 归档任务<br>
根据搜索栏分页查询部门任务
* Rap地址：[http://rap2.taobao.org/repository/editor?id=314082&mod=541030&itf=2428959](http://rap2.taobao.org/repository/editor?id=314082&mod=541030&itf=2428959)
* 请求接口格式：

```
├─ pageId: Number 
├─ currentPage: Number 
├─ pageSize: Number 
└─ PersonTask: Object 
   ├─ title: String 
   ├─ startTime: String 
   ├─ finishTime: String 
   └─ state: Number 

```

* 返回接口格式：

```
└─ taskData: Array 
   ├─ startTime: String 
   ├─ finishTime: String 
   ├─ title: String 
   ├─ briefing: String 
   ├─ detail: String 
   ├─ state: String 
   └─ taskId: Number 

```


### 接口：部门任务归档
* 地址：/department/archiveTask
* 类型：POST
* 状态码：200
* 简介：根据页面ID 切换任务状态为 是否归档<br>
取消归档后状态为已完成
* Rap地址：[http://rap2.taobao.org/repository/editor?id=314082&mod=541030&itf=2428960](http://rap2.taobao.org/repository/editor?id=314082&mod=541030&itf=2428960)
* 请求接口格式：

```
├─ pageId: Number (网页ID)
└─ taskID: Number (任务ID)

```

* 返回接口格式：

```
└─ success: String 

```


### 接口：查询部门信息
* 地址：/department/selectDepartment
* 类型：GET
* 状态码：200
* 简介：无
* Rap地址：[http://rap2.taobao.org/repository/editor?id=314082&mod=541030&itf=2428962](http://rap2.taobao.org/repository/editor?id=314082&mod=541030&itf=2428962)
* 请求接口格式：

```

```

* 返回接口格式：

```
└─ department: Object 
   ├─ departmentId: Number 
   ├─ name: String 
   ├─ principal: String (部门负责人)
   ├─ manageCode: String 
   └─ time: String 

```


### 接口：重置准入码
* 地址：/department/resetManageCode
* 类型：POST
* 状态码：200
* 简介：创建准入码
* Rap地址：[http://rap2.taobao.org/repository/editor?id=314082&mod=541030&itf=2428963](http://rap2.taobao.org/repository/editor?id=314082&mod=541030&itf=2428963)
* 请求接口格式：

```
├─ departmentID: Number (总经理创建准入码需要分配部门
此参数为目标部门ID
可为空)
└─ newDepartment: Object 
   ├─ departmentId: String 
   ├─ name: String 
   ├─ principal: String 
   ├─ manageCode: String 
   └─ time: String (使用次数)

```

* 返回接口格式：

```

```


### 接口：删除成员
* 地址：/department/delectUser
* 类型：POST
* 状态码：200
* 简介：根据用户ID删除成员
* Rap地址：[http://rap2.taobao.org/repository/editor?id=314082&mod=541030&itf=2428965](http://rap2.taobao.org/repository/editor?id=314082&mod=541030&itf=2428965)
* 请求接口格式：

```
└─ userID: Number (用户ID)

```

* 返回接口格式：

```

```


### 接口：创建用户总任务
* 地址：/department/addTotalPersonTask
* 类型：POST
* 状态码：200
* 简介：无
* Rap地址：[http://rap2.taobao.org/repository/editor?id=314082&mod=541030&itf=2428966](http://rap2.taobao.org/repository/editor?id=314082&mod=541030&itf=2428966)
* 请求接口格式：

```
└─ Task: Object 
   ├─ departmentID: String 
   ├─ title: String 
   ├─ briefing: String 
   ├─ detail: String 
   ├─ state: Number (固定值0)
   ├─ startTime: String 
   └─ finishTime: String 

```

* 返回接口格式：

```
└─ taskId: Number (新建的任务ID)

```


### 接口：分配成员任务
* 地址：/department/assignTask
* 类型：POST
* 状态码：200
* 简介：无
* Rap地址：[http://rap2.taobao.org/repository/editor?id=314082&mod=541030&itf=2428969](http://rap2.taobao.org/repository/editor?id=314082&mod=541030&itf=2428969)
* 请求接口格式：

```
├─ taskId: Number 
└─ usersID: Array (用户ID数组): Array (用户ID数组)

```

* 返回接口格式：

```

```


### 接口：创建公告
* 地址：/department/addNotice
* 类型：POST
* 状态码：200
* 简介：无
* Rap地址：[http://rap2.taobao.org/repository/editor?id=314082&mod=541030&itf=2428970](http://rap2.taobao.org/repository/editor?id=314082&mod=541030&itf=2428970)
* 请求接口格式：

```

```

* 返回接口格式：

```
```

