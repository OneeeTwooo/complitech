# stand

### Сборка проектов
1#### Проверка собранных приложений
   * Приложение server находится по пути:
      * Powershell:
         ```
         .\stand-service\build\libs\stand-server.jar
         ```
      * Unix:
         ```
         ./stand-service/build/libs/stand-server.jar
         ```

2#### Запуск собранных приложений
* Приложение server:
   * Powershell:
      ```
      java -jar stand-server.jar --spring.config.additional-location=<path-to-config.yaml> --server.port=80
      ```
   * Unix:
      ```
      java -jar stand-server.jar --spring.config.additional-location=<path-to-config.yaml> --server.port=80
      ```
   Где ```<path-to-config.yaml>``` - путь к файлу настроек в зависимости от environment (dev, test, prod, etc...),
пример такого файла находится в папке ```./config/config-example.yaml```, 
в нем указываются параметры необходимы для запуска приложения (db url, password, etc...)
  ```server.port``` - порт на котром будет запущено приложение (если http то надо указывать 80, https - 443)