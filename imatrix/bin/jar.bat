@echo off
cd..
echo [INFO] ����jar�����ϴ�jar����Nexus
call mvn clean deploy -Dmaven.test.skip=true
pause