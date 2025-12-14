
## 项目简介

这是一个基于JSP、Servlet和MySQL开发的自习室管理系统，采用MVC架构模式，实现了用户管理、自习室管理、座位预约等核心功能。

## 技术栈

- **后端**: Java 11, JSP, Servlet (Jakarta EE)
- **前端**: HTML, CSS, JavaScript
- **数据库**: MySQL 8.0.43
- **服务器**: Tomcat 10.1
- **开发工具**: Eclipse

## 项目结构

```
test1/
├── WebContent/              # Web资源目录
│   ├── css/                # 样式文件
│   │   └── style.css
│   ├── js/                 # JavaScript文件
│   │   └── validation.js
│   ├── WEB-INF/            # Web配置目录
│   │   └── web.xml
│   ├── index.jsp           # 首页
│   ├── login.jsp           # 登录页面
│   ├── register.jsp         # 注册页面
│   ├── room_list.jsp        # 自习室列表
│   ├── room_detail.jsp      # 自习室详情
│   ├── room_add.jsp         # 添加自习室（管理员）
│   ├── room_edit.jsp        # 编辑自习室（管理员）
│   ├── reservation_list.jsp # 预约列表
│   ├── reservation_add.jsp  # 新建预约
│   ├── reservation_edit.jsp # 编辑预约
│   └── profile.jsp          # 个人中心
├── src/                     # Java源代码
│   └── com/studyroom/
│       ├── controller/      # 控制器层（Servlet）
│       ├── service/         # 业务逻辑层
│       ├── dao/             # 数据访问层
│       ├── model/           # 实体类
│       └── util/            # 工具类
│           └── DBUtil.java
└── database/                # 数据库脚本
    └── init.sql
```

## 数据库配置

1. 创建数据库并执行初始化脚本：
   ```sql
   -- 执行 database/init.sql 文件
   ```

2. 修改数据库连接配置：
   编辑 `src/com/studyroom/util/DBUtil.java`，修改以下参数：
   ```java
   private static final String URL = "jdbc:mysql://localhost:3306/studyroom_db?...";
   private static final String USER = "root";
   private static final String PASSWORD = "你的数据库密码";
   ```

## 部署步骤

1. **导入项目到Eclipse**
   - 打开Eclipse
   - File -> Import -> Existing Projects into Workspace
   - 选择test1文件夹

2. **配置Tomcat服务器**
   - 在Eclipse中配置Tomcat 10.1服务器
   - 将项目添加到服务器

3. **添加依赖库**
   - 将MySQL驱动（mysql-connector-java-8.0.43.jar）放入 `WebContent/WEB-INF/lib/` 目录
   - 将JSTL库放入 `Webapp/WEB-INF/lib/` 目录：
     * **方案一（推荐）**：下载 `jstl-1.2.jar` 和 `standard-1.1.2.jar`
       - jstl-1.2.jar: https://repo1.maven.org/maven2/javax/servlet/jstl/1.2/jstl-1.2.jar
       - standard.jar: https://repo1.maven.org/maven2/taglibs/standard/1.1.2/standard-1.1.2.jar
     * **方案二（Tomcat 10.1）**：下载Jakarta版本
       - jakarta.servlet.jsp.jstl-2.0.0.jar
       - jakarta.servlet.jsp.jstl-api-2.0.0.jar
   
   ⚠️ **重要**：如果遇到 "Can not find the tag library descriptor" 错误，请确保：
   1. JAR文件已正确放置在 `Webapp/WEB-INF/lib/` 目录
   2. 在Eclipse中刷新项目（右键项目 -> Refresh）
   3. 重新启动Tomcat服务器
   详细说明请查看 `JSTL配置说明.md` 文件

4. **运行项目**
   - 启动Tomcat服务器
   - 访问 http://localhost:8080/test1/

## 功能说明

### 用户功能
- 用户注册/登录
- 查看自习室列表
- 预约自习室座位
- 查看和管理个人预约
- 修改个人信息

### 管理员功能
- 所有用户功能
- 添加/编辑/删除自习室
- 查看所有用户的预约记录

## 默认账号

- **管理员账号**: 
  - 用户名: admin
  - 密码: admin123

- **学生账号**: 
  - 用户名: student1
  - 密码: 123456

## 注意事项

1. 确保MySQL服务已启动
2. 确保数据库连接配置正确
3. 确保Tomcat版本为10.1（支持Jakarta EE）
4. 项目通过index.jsp启动
5. 所有文件名和目录名使用英文字母开头

## 开发规范

- 采用MVC架构模式
- 所有实体类对应数据库表
- 使用PreparedStatement防止SQL注入
- 前端表单进行JavaScript验证
- 使用JSTL标签库简化JSP代码
