@echo off
rem ɾ�����cas war����tempĿ¼
cd ..
cd expense
@echo on
@for /d /r %%c in (temp) do @if exist %%c ( rd /s /q %%c & echo     ɾ��Ŀ¼%%c)
@echo off
echo "imatrix-static�е�tempĿ¼��ɾ��"
pause