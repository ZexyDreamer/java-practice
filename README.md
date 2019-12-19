# Helper telegram bot
 - Чтобы проект запустился: добавить в папку resources файл .properites с содержанием, которое описано в sample.txt
 - Для деплоя на heroku:
    1) собрать .jar-файл для запуска проекта;
    2) прописать в MANIFEST.MF в Сlass-Path библиотеку с плагинами, которые нужны для запука проекта на maven;
    3) создать файлы system.properties, Procfile с содержанием, которое описано в sample.txt;
    4) деплоить на heroku;
    5) profit!
 - Для корректной работы требуется настроить СУБД MySQL:
    1) скачать mysql installer с официального сайта и установить, использовать user: root и password: root; 
    2) в окне Data Sources and Drivers создать новую MySQL Database users с хостом localhost, port: 3306, url: jdbc:mysql://localhost:3306/users;
    3) создать в ней схему bothelper;
    4) в этой схеме создать таблицу users, настройка полей в файле config_bd.txt.
    5) profit!