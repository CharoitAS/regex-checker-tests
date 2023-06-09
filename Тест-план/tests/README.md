## Установка

Для выполнения тестов необходимо наличие интерпретатора языка `python` и модуля `pytest`

Для установки `pytest` можно использовать одну из этих команд:

`pip install pytest`

`pip install -r requirements.txt`

## Запуск

Запуск тестов осуществляется командой `pytest` с опцией `--cli`, указывающей на путь к тестируемой программе `cli`.

Если опция `--cli` не указана, то будут проверены следующие пути: `./`, `dir_build/`, `build/`, `bin/`, `../`, `../dir_build/`, `../build/`, `../bin/`.

## Тестировщику

Требования к тестам:
- Все файлы тестов должны находиться в папке `tests` или в одной из её подпапок.
- Названия всех файлов тестов должны содержать слово `test`.
- Каждый тестовый файл должен содержать 1 или более тестовую функцию со словом `test` в названии, при этом названия функций в одном файле не могут повторяться.
- Тестовая функция может принимать в качестве аргумента одну или более из зарегистрированных фикстур (см. ниже), упрощающих взаимодействие с тестируемой программой `cli`

### Доступные фикстуры

- `run_cli` - функция, запускащая тестируемую программу `cli` с заданными параметрами и возвращающая результат её выполнения. Автоматически объявляет тест проваленным, если запущенная программа вернула ненулевой код ошибки (это поведение может быть переопределено параметром `fail_allowed`)

  Принимает следующие параметры:
  - `test` - строка или список строк, которые должны быть переданы `cli` после его запуска.
  
    Примеры: `"quit"` или `["create regex A", "a|b", "delete A"]`
  - `options` - список строк, каждая из которых задаёт один аргумент командной строки, с которым должен быть запущен `cli`. Если параметр `options` не задан, то `cli` будет запущен без дополнительных опций командной строки.
  
    Пример: `["--ru", "--help"]`
  - `fail_allowed` - булевое значение, по умолчанию установлено в `False`. Если установить значение в `True`, то `run_cli()` не будет автоматически объявлять тест проваленным в случае некорректного завершения вызываемой программы

    Примеры: `False` или `True`
  
  Вызов функции возвращает объект со следующими атрибутами:
  - `output`  - текст, который программа вывела через поток вывода (stdout)
  - `errors`  - текст, который программа вывела через поток ошибок (stderr)
  - `retcode` - целочисленный код, с которым завершилась программа

  Пример использования:
  ```
  result = run_cli("wrong-command", fail_allowed=True)
  assert "Could not parse the command 'wrong-command'" in result.errors
  ```