# WG_Forge
Tasks for WG_Forge 2019

Сборка:  
1)Откройте SQL\SQLWorker.java  
2)Укажите нужные данные для подключения к БД  
3)Скомпилируйте командой gradlew build  

Инструкция:  
0)apt-get update(для последующего получения библиотек)  
1)Установите git(в докер контейнере apt-get install git)  
2)Установите java(тоже в докер контейнере apt-get install default-jre)  
3)Скачайте файл WG_Forge Task-1.0-SNAPSHOT.jar из папки build\libs  
4)Запустите его командой java -jar "WGForge Task-1.0-SNAPSHOT.jar"  
5)Выберите задачу  
6)???  
7)PROFIT  

Задачи:  
1)Запустите решение.jar и после сообщения нажмите 1 и enter. Проверьте БД  
2)Запустите решение.jar и после сообщения нажмите 2 и enter. Проверьте БД  
3-6)Сначала нужно установить curl: apt-get install curl  
Далее, запустите screen и уже в новом скрине запустите решение.jar, введите цифру от 3 до 6 и нажмите enter.  
Ctrl A+D   
И можете использовать curl  
