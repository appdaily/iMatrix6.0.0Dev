@echo off
cd..
echo [INFO] ����war��
call mvn clean compile war:war -Dmaven.test.skip=true
pause