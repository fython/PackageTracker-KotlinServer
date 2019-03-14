Package Tracker 包裹动态推送服务
===

## Intro

基于 Firebase 云消息的 Package Tracker 的包裹动态推送服务端

取代 [PackageTracker-NodeServer](https://github.com/fython/PackageTracker-NodeServer) 作为推荐使用的服务端实现。

同时将会补充一些功能并修复 bug，提供不定期的维护。

目前还在施工中，如需立即搭建可用的服务端，请移步到上述的 Node Server 项目中。

## Requirement

对于用户：

- 一台持续运行的服务器
- JDK 8+
- Gradle (通过 `gradlew`/`gradlew.bat` 可以在线下载)

对于开发者还需要额外的：

- 建议使用 IntelliJ IDEA (2018.3+)
- Kotlin 1.3.21+

## Documentation

待补充

### 初次运行

### HTTPS 配置

### 认证配置

### 使用其它的数据库

## References

这个项目使用了以下库：

- Kotlin Standard Library
- [Jetbrains/Exposed](https://github.com/JetBrains/Exposed) - Jetbrains 官方的 Kotlin SQL 框架 / DSL
- [ktorio/ktor](https://github.com/ktorio/ktor) - Jetbrains 自家为 Kotlin 设计的 HTTP Server/Client 实现
- [google/gson](https://github.com/google/gson) - Json 语法支持库
- [xerial/sqlite-jdbc](https://github.com/xerial/sqlite-jdbc) - SQLite JDBC 驱动
- [Logback](https://logback.qos.ch/) - Log4j 的替代实现

## Licenses

```
MIT License

Copyright (c) 2019 Fung Gwo (fython)

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
