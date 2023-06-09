# CLI - Интерфейс командной строки

Инструмент `cli` является интерфейсом командной строки, который предоставляет набор команд для работы с именованными объектами.

# Оглавление

- [Оглавление](#оглавление)
- [Терминология](#терминология)
  - [Алфавит](#алфавит)
  - [Имя объекта](#имя-объекта)
  - [Команда](#команда)
  - [Объект](#объект)
  - [Регулярное выражение](#регулярное-выражение)
  - [Символ алфавита](#символ-алфавита)
  - [Строка](#строка)
  - [Тип объекта](#тип-объекта)
  - [Язык](#язык)
- [Функциональные требования](#функциональные-требования)
  - [Описание команд](#описание-команд)
- [Реализация](#реализация)
  - [Значения констант](#значения-констант)

# Терминология

## Алфавит 
Алфавит [(вики)](https://ru.wikipedia.org/wiki/Алфавит_(формальный_язык)) - это непустое конечное множество, состоящее из допустимых [символов алфавита](#символ-алфавита).

- Каждый алфавит должен содержать хотя бы один символ.
- Символы в алфавите не повторяются.
- Символы в алфавите упорядочены по возрастанию их порядкового номера в таблице ASCII [(вики)](https://ru.wikipedia.org/wiki/ASCII).

## Имя объекта
Именем объекта может быть любая последовательность символов, удовлетворяющая следующим условиям:
- Имя объекта не может быть пустым
- Имя объекта не может быть длиннее [`MAX_NAME_LENGTH`](#значения-констант)
- Имя объекта может состоять только из пробелов, символов русского и английского алфавитов (в верхнем и нижнем регистре), а также следующих символов: ``~`!@#%()-_+=:;.,?``
- Имя объекта не может начинаться с пробела или заканчиваться на него
- Имя объекта не может содержать более 1 пробела подряд

## Команда
Команда - это основной способ взаимодействия между пользователем и `cli`.
Цикл выполнения команд состоит из следующих этапов:
1. Чтение строки данных от пользователя
2. Разбиение строки на название команды и её аргументы
3. Проверка названия и аргументов команды на корректность
4. Выполнение действий, соответствующих введённой команде (может потребоваться дополнительный ввод данных от пользователя)
5. Ожидание следующей команды

- Чтение строки от пользователя может осуществляться в одном из двух режимов: простом и с автодополнением, который выбирается в зависимости от того, запускается ли `cli` в терминале или из скрипта/другой программы.
- Явно указать режим можно при помощи опций `--no-fancy` и `--fancy` соответственно.
- Список всех доступных команд может быть получен вводом команды `help`.
- В `cli` должны быть реализованы все команды с названиями, указанными в разделе [описание команд](#описание-команд)

## Объект
Именованным объектом (или просто объектом) называется любая созданная сущность, имеющая допустимое [имя](#имя-объекта) и [тип](#тип-объекта)
- Имена существующих объектов не могут совпадать

## Регулярное выражение

Регулярным выражением над [алфавитом](#алфавит) *A* называется, строка, удовлетворяющая следующим условиям:
- Строка состоит только из допустимых [символов алфавита](#символ-алфавита) *A* и/или специальных символов `()*|`
- Длина строки не может быть больше [`MAX_REGEX_LENGTH`](#значения-констант)
- Символ `*` не может стоять в начале строки, после символа `(` или `|`.
- Количество символов `(` должно совпадать с количеством символов `)`
- Каждой закрывающей скобке `)` должна соответствовать некоторая открывающая скобка `(`, находящаяся слева от неё
- Исключением является строка `)EMPTY(`, которая считается корректным регулярным выражением, которому соответствует пустой язык

Таким образом, строка `(aaa|b*a)*` является корректным регулярным выражением, а `(a))`, `)(` или `*` - нет.

## Тип объекта

Любой объект должен принадлежать к одному из допустимых типов:
- `abc` - задаёт [алфавит](#алфавит)
- `regex` - задаёт [регулярное выражение](#регулярное-выражение) [(wiki)](https://ru.wikipedia.org/wiki/Регулярные_выражения)
- `dfa` - задаёт [ДКА](#дка) [(wiki)](https://ru.wikipedia.org/wiki/Детерминированный_конечный_автомат)
- `nfa` - задаёт [НКА](#нка) [(wiki)](https://ru.wikipedia.org/wiki/Недетерминированный_конечный_автомат)

## Символ алфавита
 
Допустимыми символами [алфавита](#алфавит) могут быть символы из таблицы ASCII [(вики)](https://ru.wikipedia.org/wiki/ASCII), за исключением следующих:
- Пробел не может быть символом алфавита.
- Символы `()*|` не могут быть символами алфавита.
- Первые 32 символа из таблицы ASCII не могут быть символами алфавита.
- Символы не из таблицы ASCII не могут быть символами алфавита.

## Строка
Строкой над [алфавитом](#алфавит) *A* называется любая конечная последовательность [символов](#символ-алфавита), входящих в этот алфавит.
- Строка может быть пустой.
- Длина строки не может быть больше константы [`MAX_STRING_LENGTH`](#значения-констант)

## Язык
Языком над [алфавитом](#алфавит) *A* называется любое множество [строк](#строка) над данным алфавитом. Язык может быть пустым, конечным или бесконечным.

# Функциональные требования

- После запуска `cli`, программа должна либо начать цикл чтения [команд](#команда) от пользователя, либо выдать ошибку и завершиться
- Ввод команд осуществляется по одной за раз
- Пользователь должен иметь возможность выполнить любое количество корректных команд
- При попытке ввода некорректной команды должна выдаваться соответствующая ошибка
- При попытке ввода аргументов, не соответствующих требованиям команды, выполнение команды должно быть прервано с ошибкой (например, если передано недостаточно аргументов) или применено преобразование, делающее аргумент допустимым (например, если использованы недопустимые символы, которые можно удалить)
- Если программа запущена с опцией `--fail-on-error`, то при попытке ввода некорректной команды выполнение `cli` должно завершиться
- Если программа запущена с опцией `--no-fail-on-error`, то при попытке ввода некорректной команды `cli` должен ожидать ввода следующей команды
- `cli` должен вести список существующих [объектов](#объект) на протяжении всего выполнения программы
- При запуске программы должны существовать объекты типа `abc` со следующими названиями:
  - *01* - содержит [символы](#символ-алфавита) `01`
  - *ab* - содержит символы `ab`
  - *abc* - содержит символы `abc`
  - *digits* - содержит все цифры от `0` до `9`
  - *latin* - содержит все буквы английского алфавита от `a` до `z` в нижнем регистре
  - *Latin* - содержит все буквы английского алфавита от `a` до `z` и от `A` до `Z`

## Описание команд

### `create`
  В случае успеха команда `create` создаёт новый [объект](#объект), или заменяет существующий.

  - Обязательными аргументами являются:
    1. `<type>` - определяет тип создаваемого [типов](#тип-объекта) объекта
    2. `<name>` - определяет [имя](#имя-объекта) создаваемого объекта
  
  - Если `<type>` или `<name>` не являются корректными, то выполнение команды прекращается с ошибкой
  - Если `<name>` совпадает с именем одного из существующих объектов - программа должна запросить подтверждение на его перезапись
  - Выполнение команды прекращается с ошибкой, если на запрос о перезаписи получен отрицательный ответ
  - Дальнейшее поведение программы зависит от наличия необязательных аргументов:
    1. Если за обязательными аргументами идут аргументы `from <old_name>`, где `<old_name>` - название существующего объекта, то создаётся объект `<name>` типа `<type>`, эквивалентный объекту `<old_name>`
    2. Если необязательных аргументов нет, то программа должна запросить дополнительные данные, достаточные для заполнения значения объекта выбранного типа
  - Программа должна написать содержащее тип и имя созданного объекта сообщение в случае успеха
  - Объект с данным типом и именем должен отображаться командой [`list`](#list) в случае успеха
  - Объект с данным именем должен приниматься командой [`print`](#print) в случае успеха

### `delete`
Команда `delete` принимает 1+ имён объектов в качестве аргументов.
- Команда должна удалить все существующие объекты, имена которых перечислены среди аргументов и выдать соответствующее уведомление
- Для имён, которые не соответствуют существующим объектам, команда должна выдать соответствующую ошибку

### `diff`
Команда `diff` принимает аргументы вида `<name1> <name2> as <new name>`, где `<name1>`, `<name2>` и `<new name>` - корректные [имена объектов](#имя-объекта)
- В случае успеха она создаёт объект `<new name>` того же типа, что и объекты `<name1>` и `<name2>`, который задаёт [разность](https://ru.wikipedia.org/wiki/Разность_множеств) языков двух объектов
- Если объект `<name1>` или `<name2>` не существует, то команда завершается с ошибкой
- Если объекты `<name1>` и `<name2>` имеют разные [типы](#тип-объекта), то команда завершается с ошибкой
- Если `<new name>` совпадает с именем одного из существующих объектов - программа должна запросить подтверждение на его перезапись
- Выполнение команды завершается с ошибкой, если на запрос о перезаписи получен отрицательный ответ
- В случае успеха, язык объекта `<new name>` должен содержать все строки, входящие в язык `<name1>`, но не входящие в язык `<name2>`, что может проверяться командой [samples](#samples)

### `dot`
`dot <name>...` - печатает в консоль содержимое именованного объекта (объектов) в формате [Graphviz](https://graphviz.org/). Для просмотра можно использовать, например, https://dreampuf.github.io/GraphvizOnline/

### `echo`
Команда `echo` принимает в качестве аргумента строку `<text>` и печатает её на экран. Аргумент может содержать пробелы в середине текста, но пробелы в начале и конце текста игнорируются

### `equiv`
Команда `equiv` принимает в качестве аргументов имена двух существующих [объектов](#объект).
- Если указанные объекты не существуют, то команда завершается с ошибкой
- Если объекты имеют типы, которые невозможно сравнить на эквивалентность (например, `abc` и `regex`), то команда завершается с ошибкой
- Если указанные объекты имеют одинаковое значение и/или задают один и тот же [язык](#язык), то команда должна напечатать `Equivalent`, в противном случае - `Not equivalent`

### `help`
Команда `help` выводит в консоль список всех поддерживаемых [команд](#команда)

### `intersect`
`intersect <name> <name> as <new name>` - вычисляет [пересечение](https://ru.wikipedia.org/wiki/Пересечение_множеств) языков двух объектов

### `invert`
`invert <name> [as <new name>]` - вычисляет [дополнение](https://ru.wikipedia.org/wiki/Разность_множеств#Дополнение_множества) к языку именованного объекта

### `lang`
Команда `lang` принимает название языка в качестве единственного аргумента и устанавливает его в качестве текущего.
- Допустимые варианты языка - `RU` (Русский) и `EN` (Английский)
- Язык может быть написан как в нижнем, так и в верхнем регистре
- Если язык не входит в список допустимых, то 
`lang (RU|EN)` - выбирает один из доступных языков

### `latex`
`latex <name>...` - печатает в консоль содержимое именованного объекта (объектов) в формате [LaTeX](https://ru.wikipedia.org/wiki/LaTeX)

### `list`
Команда `list` возвращает список всех существующих объектов, с указанием их типа и имени на отдельной строчке.
- Если команде переданы аргументы с названиями допустимых [типов](#тип-объекта) объектов, то отображаются только объекты указанных типов

### `load`
`load <type> <name> from <filename>` - загружает объект типа `<type>` из файла `<filename>`, ранее созданного при помощи команды `save`

### `minimize`
`minimize <name> [as <new name>]` - минимизирует (для ДКА) или упрощает (для НКА и регулярных выражений) объект

### `print`
Команда `print` принимает 1+ имён объектов в качестве аргументов.
- Команда должна напечатать типы, имена и содержимое существующих объектов, переданных в качестве аргументов
- Для имён, которые не соответствуют существующим объектам, команда должна выдать соответствующую ошибку

### `quit`
Команда `quit` завершает выполнение программы

### `reverse`
`reverse <name> [as <new name>]` - "разворачивает" язык, задаваемый объектом (в язык результата будут входить все исходные строки, записанные в обратном направлении)

### `samples`
Команда `samples` принимает 1+ имён объектов в качестве аргументов, а также может принять число `<number>` в качестве последнего аргумента.
- Если `<number>` не указан, то считается, что он равен [`DEFAULT_SAMPLES_COUNT`](#значения-констант)
- Команда должна напечатать первые `<number>` [строк](#строка), отсортированных по возрастанию длины строки и по алфавиту (позиции в таблице ASCII), входящих в языки указанных объектов
- Если в язык объекта входит меньше `<number>` строк, то печатаются все строки, входящие в язык
- Для эквивалентных объектов гарантируется, что команда samples с одинаковым значением `<number>` напечатает одни и те же строки
- Для неэквивалентных объектов гарантируется, что существует достаточно большое значение `<number>`, при котором набор строк, входящих в объекты, будет отличаться
- Для имён, которые не соответствуют существующим объектам, команда должна выдать соответствующую ошибку
- Для объектов типа [abc](#алфавит) (которые не задают язык), команда должна выдать соответствующую ошибку

### `save`
`save <name> to <filename>` - сохраняет объект в файл `<filename>`, который может быть после загружен при помощи команды `load`

### `symdiff`
`symdiff <name> <name> as <new name>` - вычисляет [симметрическую разность](https://ru.wikipedia.org/wiki/Симметрическая_разность) языков двух объектов

### `union`
`union <name> <name> as <new name>` - вычисляет [объединение](https://ru.wikipedia.org/wiki/Объединение_множеств) языков двух объектов

# Реализация

## Значения констант
- `DEFAULT_SAMPLES_COUNT = 10`
- `MAX_NAME_LENGTH = 30`
- `MAX_REGEX_LENGTH = 8192`
- `MAX_STATE_NAME_LEN = 128`
- `MAX_STRING_LENGTH = 1024`


# TODO
- описать эквивалентность объектов
- NFA и DFA
- описать, какие данные нужны для создания объекта
