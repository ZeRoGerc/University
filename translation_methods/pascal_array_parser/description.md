## Нетерминалы
 - S - правильное объявление массива
 - V - переменная с именем
 - T - тип

## Терминалы
 - var
 - variable — имя переменной
 - array
 - of
 - type - один из стандартных типов языка pascal (char, integer, int64, ...)
 - number — число означающее границу диапозона 
 - ..
 - [
 - ]
 - :
 - ;
 - ε — пустая строка
 - $ - конец строки

## Грамматика
 - S &rarr; V : T
 - V &rarr; var variable
 - T &rarr; array [ number \.\. number] of type ;
 
 ## Построение FIRST и FOLLOW
 | Нетерминал | FIRST | FOLLOW |
 |------------|-------|--------|
 | S          | var   | $      |
 | V          | var   | :      |
 | T          | array | $      |