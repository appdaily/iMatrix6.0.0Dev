@echo off
set ROOT_DIR=%cd%

rem cas mysql�汾
cd ..
cd expense
echo [INFO] ����imatrix-static war��
jar -cvf imatrix-static.war css/ images/ js/ META-INF/  WEB-INF/ widgets/ templateJs/
pause

echo [INFO]��ѹ���ɵ�war����ɾ��.svn�ļ�
if not exist temp goto createDir
:createDir
mkdir temp

move imatrix-static.war temp
cd ..
cd expense/temp
jar -xvf imatrix-static.war
pause
echo [INFO]�뽫del-svn.bat����imatrix-static/expense/temp�ļ�����
pause
call del-svn.bat
pause
cd ..
cd expense/temp
rd imatrix-static.war

cd ..
cd expense/temp
echo [INFO] ����imatrix-static war��
jar -cvf imatrix-static.war css/ images/ js/ META-INF/  WEB-INF/ widgets/ templateJs/
pause




