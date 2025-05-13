# iboot-print 打印服务引擎

[![JDK](https://img.shields.io/badge/JDK-17+-green.svg)](https://www.oracle.com/java/technologies/javase-downloads.html)
[![License: GPL v3](https://img.shields.io/badge/License-GPL%203.0-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)

## 项目介绍

iboot-print 是一个基于 vue-plugin-hiprint 的服务端渲染引擎，提供根据模版和打印数据JSON字符串生成HTML字符串功能，便于后端自动生成。本项目使用 Java 17 和 Solon 框架开发，采用 HtmlUnit 实现服务端渲染。

## 主要特性

- 支持服务端模板渲染
- 支持PDF生成，待实现

## 技术栈

- JDK 17+
- Solon 3.3.0
- HtmlUnit 2.70.0
- Hutool 5.8.37
- vue-plugin-hiprint 0.0.58-fix 

## 快速开始

### 环境要求

- JDK 17 或更高版本
- Gradle 8.0 或更高版本

### 构建项目

```bash
# 克隆项目
git clone https://github.com/anganing/iboot-print.git

# 进入项目目录
cd iboot-print

# 构建项目
./gradlew build
```

### 运行项目

```bash
java -jar build/libs/iboot-print-1.0.jar
```

## 使用示例

**HTTP接口调用示例**

生成HTML字符串请求地址：
```
POST http:/{{ip}}:{{port}}/api/print/generateHtml
```

请求参数（JSON格式）：
```json
{
  "templateContent": "vue-plugin-hiprint模板json字符串",
  "printData": "打印数据json字符串，必须是数组（数组表示生成多份）或者对象"
}
```

响应示例（HTML内容）：
```html
html字符串
```

获取vue-plugin-hiprint版本请求地址：
```
GET http:/{{ip}}:{{port}}/api/print/getHiprintVersion
```


响应示例（HTML内容）：
```text
0.0.58-fix
```

## 贡献指南

欢迎提交 Issue 和 Pull Request。

## 开源协议

本项目采用 [GPL 3.0 协议](LICENSE)。