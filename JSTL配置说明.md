# JSTL标签库配置说明

## 问题描述
错误信息：`Can not find the tag library descriptor for "http://java.sun.com/jsp/jstl/core"`

## 原因分析
项目使用了JSTL标签库，但缺少相应的JAR文件。由于使用的是Tomcat 10.1（Jakarta EE），需要使用Jakarta版本的JSTL。

## 解决方案

### 方案一：使用Jakarta JSTL（推荐，适用于Tomcat 10.1）

1. **下载JAR文件**
   需要下载以下两个JAR文件：
   - `jakarta.servlet.jsp.jstl-2.0.0.jar`（或更新版本）
   - `jakarta.servlet.jsp.jstl-api-2.0.0.jar`（或更新版本）

   下载地址：
   - Maven中央仓库：https://mvnrepository.com/artifact/jakarta.servlet.jsp.jstl/jakarta.servlet.jsp.jstl
   - 或者直接搜索：`jakarta jstl maven`

2. **放置JAR文件**
   将下载的JAR文件放入以下目录：
   ```
   test1/WebContent/WEB-INF/lib/
   ```

3. **修改JSP文件中的taglib声明**
   由于使用Jakarta版本，需要修改JSP文件中的URI。

### 方案二：使用传统JSTL（如果方案一不行）

1. **下载JAR文件**
   下载 `jstl-1.2.jar`
   
   下载地址：
   - https://mvnrepository.com/artifact/javax.servlet/jstl/1.2
   - 或者：https://repo1.maven.org/maven2/javax/servlet/jstl/1.2/jstl-1.2.jar

2. **放置JAR文件**
   将JAR文件放入：
   ```
   test1/WebContent/WEB-INF/lib/jstl-1.2.jar
   ```

3. **同时需要standard.jar**
   还需要下载 `standard.jar`（JSTL标准库）
   - https://mvnrepository.com/artifact/taglibs/standard/1.1.2

## 快速解决步骤（推荐）

### 步骤1：下载JAR文件

**对于Tomcat 10.1（Jakarta EE）：**
1. 访问：https://mvnrepository.com/artifact/jakarta.servlet.jsp.jstl/jakarta.servlet.jsp.jstl/2.0.0
2. 下载 `jakarta.servlet.jsp.jstl-2.0.0.jar`
3. 访问：https://mvnrepository.com/artifact/jakarta.servlet.jsp.jstl/jakarta.servlet.jsp.jstl-api/2.0.0
4. 下载 `jakarta.servlet.jsp.jstl-api-2.0.0.jar`

**或者使用传统版本（如果Jakarta版本不兼容）：**
1. 下载 `jstl-1.2.jar`：https://repo1.maven.org/maven2/javax/servlet/jstl/1.2/jstl-1.2.jar
2. 下载 `standard.jar`：https://repo1.maven.org/maven2/taglibs/standard/1.1.2/standard-1.1.2.jar

### 步骤2：放置文件

将下载的JAR文件复制到：
```
test1/WebContent/WEB-INF/lib/
```

### 步骤3：刷新项目

1. 在Eclipse中右键点击项目
2. 选择 `Refresh`（刷新）
3. 或者 `Build Path` -> `Configure Build Path` -> `Libraries` -> 检查JAR文件是否已添加

### 步骤4：如果使用Jakarta版本，需要修改JSP文件

如果使用Jakarta JSTL，需要将JSP文件中的：
```jsp
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
```

改为：
```jsp
<%@ taglib prefix="c" uri="https://jakarta.ee/taglibs/standard/core" %>
```

或者保持原样，但确保使用正确的JAR文件版本。

## 验证配置

1. 重新启动Tomcat服务器
2. 访问项目首页
3. 如果不再出现错误，说明配置成功

## 常见问题

### Q1: 下载后仍然报错？
A: 确保JAR文件在 `WEB-INF/lib/` 目录下，并且刷新了项目。

### Q2: 应该使用哪个版本？
A: 
- Tomcat 10.x → 使用Jakarta JSTL 2.0+
- Tomcat 9.x及以下 → 使用传统JSTL 1.2

### Q3: 需要同时添加多个JAR吗？
A: 
- Jakarta版本：需要api和impl两个JAR
- 传统版本：需要jstl-1.2.jar和standard.jar

## 备用方案：移除JSTL依赖

如果无法下载JSTL库，可以修改JSP文件，使用标准JSP语法替代JSTL标签，但这需要修改所有使用JSTL的JSP文件。

