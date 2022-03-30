#! /bin/bash

REPOSITORY=/home/ec2-user/app/step1
PROJECT_NAME=jambe

cd $REPOSITORY/$PROJECT_NAME/

echo "> GIT pull"

git pull

echo "> project build started...."

./gradlew build

echo "> move directory to step1...."

cd $REPOSITORY

echo "> copy build file"

cp $REPOSITORY/$PROJECT_NAME/build/libs/*.jar $REPOSITORY

echo "> check pid of operating application"

CURRENT_PID=$(pgrep -f ${PROJECT_NAME}.*.jar)

echo "operating application pid : $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
	echo "> there is no process"
else 
	echo "> kill -15 $CURRENT_PID"
	sudo kill -15 $CURRENT_PID
	sleep 5
fi

echo "> new application deployment"

JAR_NAME=$(ls -tr $REPOSITORY/ | grep jar | tail -n 1)

echo "> JAR name : $JAR_NAME"

nohup java -jar $REPOSITORY/$JAR_NAME 2>&1 &
