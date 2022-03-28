# java-hot-reload-agent

java 热更新插件，无需重启 java 进程实现代码更新，提高开发效率，节约时间去陪女朋友！

## 功能模块

- hot-reload-core: 核心处理逻辑，编译&加载 class
- hot-reload-agent: javaagent 入口
- hot-reload-watcher: 监听本地 java 和 class 文件变化，实现本地进程热更新
- hot-reload-server: api server，提供接口实现加载远程 jvm 进程

## 架构

![](doc/java-hot-reload-agent.jpg)

## 安装&使用

### 下载&安装（也可以用自行编译）

```bash
version="1.1.0"
# 从 github 下载
wget https://github.com/fengjx/java-hot-reload-agent/releases/download/hot-reload-agent-all-${version}/hot-reload-agent-bin.zip
# 解压都任意目录
unzip hot-reload-agent-bin.zip -d hot-reload-agent
```

### 启动 watcher

```bash
# 启动
cd hot-reload-agent
bash boot.sh watcher -c /path/to/config.json
[INFO] start hot-reload-watcher
[INFO] JAVA_HOME: /Users/fengjianxin/opt/java/jdk/jdk8
[INFO] AGENT_HOME: /Users/fengjianxin/.hot-reload-agent
[INFO] args: -c /path/to/config.json
[INFO] config: {
  "mode": "server",
  "pid": 0,
  "watchPaths": [
    "/path/to/source/dir"
  ],
  "server": {
    "host": "localhost:8000"
  }
}
[INFO] 输入指令，'h' 查看帮助
```

`config.json` 参考: `hot-reload-watcher/src/main/resources/config.json`

watcher 指令

| 指令       | 参数      | 说明              |
|----------|---------|-----------------|
| h,help,? | -       | 查看帮助            |
| exit,q   | -       | 退出进程            |
| r        | -       | 将变更的文件重新加载到 jvm |
| jps      | -       | 查看 jvm 进程列表     |
| config         | -       | 查看当前配置          |
| set-pid         | \<pid\> | 修改 jvm 进程 id    |

### 本地模式

watcher 配置

```json
// config.json
{
  "mode": "local",  // local: 本地模式
  "watchPaths": [ 监听的文件路径（.java & .class），支持多个路径
    "/path/to/source/dir"
  ],
  "pid": 0  // 可选，支持环境变量指定，或通过指令设置
}
```

演示

![无法加载，请点击视频链接](http://cdn.fengjx.com/java-hot-reload-agent/1.1.0/java-hot-reload-agent-1.1.0-local.gif)

[视频链接](http://cdn.fengjx.com/java-hot-reload-agent/1.1.0/java-hot-reload-agent-1.1.0-local.mp4)

### server 模式

watcher 配置

```json
// config.json
{
  "mode": "server", // 远程模式
  "watchPaths": [ // 监听的文件路径（.java & .class），支持多个路径
    "/path/to/source/dir"
  ],
  "pid": 0, // 可选，支持环境变量指定，或通过指令设置
  "server": {
    "host": "localhost:8000"  // 远程服务 ip:port
  }
}
```

在远程服务器上启动 server

```bash
# -Dserver.port=6000 指定端口号，默认 8000
bash boot.sh server -Dserver.port=6000
```

演示

![无法加载，请点击视频链接](http://cdn.fengjx.com/java-hot-reload-agent/1.1.0/java-hot-reload-agent-1.1.0-server.gif)

[视频链接](http://cdn.fengjx.com/java-hot-reload-agent/1.1.0/java-hot-reload-agent-1.1.0-server.mp4)

## 编译打包

环境依赖

- jdk 1.8+
- maven

```bash
# mac or linux
make package
```

```bash
# windows
mvn --settings=${maven_settings} clean package -Dmaven.test.skip=true -P full
```

打包完成后的文件保存在 `packaging/target/hot-reload-agent-bin.zip`，可以移动到任意目录，解压即可

## 测试覆盖情况

| 操作系统       | jdk 版本         | 说明 |
|------------|----------------|------|
| MacOS 12.3 | oracle-jdk 1.8 | - |

精力有限，其他未覆盖平台自行测试，如果你已测试过，欢迎联系补充

## 基本原理

1. watcher 监听本地文件变更（.class & .java）并缓存变更文件路径
2. 将变更文件上传到 server 并保存在临时目录（本地模式忽略次步骤）
3. 通过 javaagent 技术 attach 到 jvm 进程，拿到`Instrumentation`对象
4. 使用自定义类加载器（与业务代码隔离）加载 `hot-reload-core` 
5. 编译 .java 文件或从文件读取 .clsss，得到字节码 byte
6. 通过`instrumentation.redefineClasses()`方法重新定义并加载 class

## todo list

<https://github.com/fengjx/java-hot-reload-agent/issues?q=is%3Aopen+is%3Aissue>

## 参考项目

- [arthas](https://github.com/alibaba/arthas)
- [lets-hotfix](https://github.com/liuzhengyang/lets-hotfix)
- [HotswapAgent](https://github.com/HotswapProjects/HotswapAgent)

