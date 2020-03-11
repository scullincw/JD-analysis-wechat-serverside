#!/bin/sh
#curl -X POST localhost:8081/shutdown
cd /root/JD-analysis-wechat-serverside && git status && git pull && mvn clean && mvn install -Dmaven.test.skip=true && cd target && nohup java -jar wechat-serverside-0.0.1-SNAPSHOT.jar &