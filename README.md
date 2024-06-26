# Java File System

## Гайд по запуску:

### 1. Через IDE
Запустить main функцию в классе [Main.java](Main.java)

### 2. Через терминал
1. Установить jdk17
2. Перейти в директорию с [проектом](.)
3. Выполнить команды:
```shell
javac *.java
java Main
```
# Документация
#### ```ls <path>```          - выводит список всех файлов и директорий для `path`
#### ```ls_py <path>```       - выводит список файлов с расширением `.py` в `path`
#### ```is_dir <path>```      - выводит `true`, если `path` это директория, в других случаях `false`
#### ```define <path>```      - выводит `директория` или `файл` в зависимости от типа `path`
#### ```readmod <path>```     - выводит права для файла в формате `rwx` для текущего пользователя
#### ```setmod <path>```      - устанавливает права для файла `path`
Чтобы дать права только для чтения напишите `setmod /home/user/file r` чтобы дать все права напишите `setmod /home/user/file rwx` чтобы забрать все права `setmod /home/user/file 0`
#### ```cat <path>```         - выводит контент файла
#### ```append <path>```      - добавляет строку `# Autogenerated line` в конец `path`
#### ```bc <path>```          - создает копию `path` в директорию `/tmp/${date}.backup` где, date - это дата в формате `dd-mm-yyyy`
#### ```greplong <path>```    - выводит самое длинное слово в файле
#### ```help```               - выводит список команд и их описание
#### ```exit```               - завершает работу программы