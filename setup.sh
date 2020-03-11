#!/bin/sh
echo '======= Start building and runing SpringBoot Application =======\n'
# 杀死已有进程
kill -9 $(ps aux|grep wechat-serverside-0.0.1-SNAPSHOT.jar) || echo 'Nothing to kill' >log/$(datatime).log 2>&1
sleep 2s

# 切换目录
cd JD-analysis-wechat-serverside || echo 'cd fail' >log/$(datatime).log 2>&1

# 从GitHub仓库拉取最新分支
git fetch --all || echo 'git fetch fail' >log/$(datatime).log 2>&1
git reset --hard origin/master || echo 'git reset fail' >log/$(datatime).log 2>&1
git status >log/$(datatime).log 2>&1

# 清理项目
mvn clean >log/$(datatime).log 2>&1

# 构建项目
mvn install -Dmaven.test.skip=true >log/$(datatime).log 2>&1

#切换目录
cd target
nohup java -jar wechat-serverside-0.0.1-SNAPSHOT.jar &


