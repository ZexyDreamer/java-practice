# Helper telegram bot
 - Чтобы проект запустился:
    добавить в папку resources файл .properites с содержанием, которое описано в sample.txt
 - Для деплоя на heroku:
    1) собрать .jar-файл для запуска проекта;
    2) прописать в MANIFEST.MF в Сlass-Path библиотеку с плагинами, которые нужны для запука проекта на maven;
    3) создать файл system.properties, Procfile;
    4) деплоить на heroku;
    5) profit!
