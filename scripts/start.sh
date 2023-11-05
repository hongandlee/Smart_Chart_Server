#!/bin/bash

ROOT_PATH="/home/ubuntu/spring-github-action/scripts"
JAR="$ROOT_PATH/application.jar"

APP_LOG="$ROOT_PATH/application.log"
ERROR_LOG="$ROOT_PATH/error.log"
START_LOG="$ROOT_PATH/start.log"

# JAR 파일의 실제 경로로 수정
JAR_SOURCE="/home/ubuntu/spring-github-action/build/libs/application.jar"

NOW=$(date +%c)

echo "[$NOW] $JAR 복사" >> $START_LOG
cp "$JAR_SOURCE" "$JAR"

echo "[$NOW] > $JAR 실행" >> $START_LOG
nohup java -jar "$JAR" > "$APP_LOG" 2> "$ERROR_LOG" &

SERVICE_PID=$(pgrep -f "$JAR")
echo "[$NOW] > 서비스 PID: $SERVICE_PID" >> "$START_LOG"