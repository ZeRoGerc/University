## Нетерминалы
 - S - правильное объявление массива
 - V - переменная с именем
 - T - тип
 - R - диапозон индексов
 - R' - выражение типа x .. y, где x, y — числа
 - A - вспомогательный терминал означающий ", R'" или ничего
 - N - число либо переменная

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
 - ,
 - ε — пустая строка
 - $ - конец строки

## Грамматика
 - S &rarr; V : T
 - V &rarr; var variable
 - T &rarr; array [ R ] of type ;
 - R &rarr; R' , R
 - R &rarr; R'
 - R' &rarr; N \.\. N
 - N &rarr; number
 - N &rarr; variable

 ## Грамматика без правого ветвления
 - S &rarr; V : T
 - V &rarr; var variable
 - T &rarr; array [ R ] of type ;
 - R &rarr; R' A
 - A &rarr; , R
 - A &rarr; ε
 - R' &rarr; N \.\. N
 - N &rarr; number
 - N &rarr; variable
  

 
 ## Построение FIRST и FOLLOW
 | Нетерминал | FIRST  | FOLLOW |
 |------------|--------|--------|
 | S          | var    | $      |
 | V          | var    | :      |
 | T          | array  | $      |
 | R          | number | ]      |
 | R'         | number | , ]    |
 | A          | , ε    | ]      |
 | N          | var number    | , ] ..      |