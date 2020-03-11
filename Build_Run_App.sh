#!/bin/sh
echo '======= Start building and runing SpringBoot Application ======='
# 杀死已有进程
kill -9 $(ps aux|grep wechat-serverside-0.0.1-SNAPSHOT.jar)
sleep 2s
# 切换目录
cd /root/JD-analysis-wechat-serverside &&
# 从GitHub仓库拉取最新分支
git pull &&
# 清理项目
mvn clean &&
# 安装项目
mvn install -Dmaven.test.skip=true &&
# 运行项目
cd target &&
nohup java -jar wechat-serverside-0.0.1-SNAPSHOT.jar &
