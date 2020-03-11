#!/bin/sh
#echo '======= Start building and runing SpringBoot Application =======\n'
# 杀死已有进程
kill -9 $(ps aux|grep wechat-serverside-0.0.1-SNAPSHOT.jar) &
sleep 2s

# 切换目录
#cd JD-analysis-wechat-serverside || echo 'cd fail'

# 从GitHub仓库拉取最新分支
#git fetch --all || echo 'git fetch fail'
#git reset --hard origin/master || echo 'git reset fail'
#git status

# 清理项目
mvn clean

# 构建项目
mvn install -Dmaven.test.skip=true

#切换目录
cd target
nohup java -jar wechat-serverside-0.0.1-SNAPSHOT.jar &


