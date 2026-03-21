# API Common 模块

## 模块说明

公共模块，提供基础的实体类、异常类、结果封装等通用组件。

## 主要内容

### 1. 结果封装
- `Result<T>` - 统一响应结果封装类

### 2. 异常处理
- `BusinessException` - 业务异常
- `GlobalExceptionHandler` - 全局异常处理器

### 3. 实体基类
- `BaseEntity` - 基础实体类，包含 id、createTime、updateTime、deleted 字段

### 4. 分页相关
- `PageRequest` - 分页请求参数
- `PageResult<T>` - 分页响应结果

## 使用方式

在其他模块中添加依赖：
```xml
<dependency>
    <groupId>com.mtaobao</groupId>
    <artifactId>api-common</artifactId>
</dependency>
```

## Result 使用示例

```java
// 成功响应
return Result.success(data);
return Result.success();

// 错误响应
return Result.error("错误信息");
return Result.error(500, "错误信息");
```
