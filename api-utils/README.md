# API Utils 模块

## 模块说明

工具模块，提供常用的工具类。

## 主要内容

### 1. StringUtils
字符串工具类
- `isEmpty(str)` - 判断字符串是否为空
- `isNotEmpty(str)` - 判断字符串是否非空

### 2. IdGenerator
ID生成器，基于雪花算法
- `nextId()` - 生成 Long 类型 ID
- `nextIdStr()` - 生成 String 类型 ID

## 使用方式

```java
import com.mtaobao.api.utils.StringUtils;
import com.mtaobao.api.utils.IdGenerator;

// 判断字符串
if (StringUtils.isNotEmpty(name)) {
    // ...
}

// 生成ID
Long id = IdGenerator.nextId();
```
