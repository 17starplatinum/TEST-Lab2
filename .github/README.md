# Integrine: next level of testing

## Лабораторная работа №2
<p align="center">
    <img src="https://media2.giphy.com/media/v1.Y2lkPTc5MGI3NjExczUxMWFlNzJrcms3OWdtaTVwMHQyOXN4eXBsN210eXh6YWdhZ2lkaSZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/RQig4TJz3WBQk/giphy.gif" alt="I'm fed up"/>
</p>

### Вариант: `881`

## Результат: ${\color{green}100\\%}$

### Текст задания:
Провести интеграционное тестирование программы, осуществляющей вычисление системы функций (в соответствии с вариантом).
```math
f(x) = \begin{cases}
(((((tan(x)\cdot cos(x)) + (cot(x) + sec(x))) + csc(x)) \cdot csc(x)) + sec(x)) & \text{if } x \leq 0 \\
\displaystyle(((((log_{10}(x) - log_{10}(x)) \cdot (log_2(x) - log_3(x))) \cdot (\frac{log_3(x) - log_5(x)}{log_{10}(x)})) ^ 3) + (\frac{ln(x) \cdot log_{10}(x)}{log_2(x)})) & \text{if } x > 0
\end{cases}
```
```
x <= 0 : (((((tan(x) * cos(x)) + (cot(x) + sec(x))) + csc(x)) * csc(x)) + sec(x))
x > 0 : (((((log_10(x) - log_10(x)) * (log_2(x) - log_3(x))) * ((log_3(x) - log_5(x)) / log_10(x))) ^ 3) + ((ln(x) * log_10(x)) / log_2(x)))
```

Правила выполнения работы:
1. Все составляющие систему функции (как тригонометрические, так и логарифмические) должны быть выражены через базовые (тригонометрическая зависит от варианта; логарифмическая - натуральный логарифм).
2. Структура приложения, тестируемого в рамках лабораторной работы, должна выглядеть следующим образом (пример приведён для базовой тригонометрической функции sin(x)):
![diagram](/docs/lab2(dark).png)
3. Обе "базовые" функции (в примере выше - `sin(x)` и `ln(x)`) должны быть реализованы при помощи разложения в ряд с задаваемой погрешностью. Использовать тригонометрические / логарифмические преобразования для упрощения функций ЗАПРЕЩЕНО.
4. Для КАЖДОГО модуля должны быть реализованы табличные заглушки. При этом, необходимо найти область допустимых значений функций, и, при необходимости, определить взаимозависимые точки в модулях.
5. Разработанное приложение должно позволять выводить значения, выдаваемое любым модулем системы, в сsv файл вида «X, Результаты модуля (X)», позволяющее произвольно менять шаг наращивания Х. Разделитель в файле csv можно использовать произвольный.

Порядок выполнения работы:
1. Разработать приложение, руководствуясь приведёнными выше правилами.
2. С помощью JUNIT4 разработать тестовое покрытие системы функций, проведя анализ эквивалентности и учитывая особенности системы функций. Для анализа особенностей системы функций и составляющих ее частей можно использовать сайт https://www.wolframalpha.com/.
3. Собрать приложение, состоящее из заглушек. Провести интеграцию приложения по 1 модулю, с обоснованием стратегии интеграции, проведением интеграционных тестов и контролем тестового покрытия системы функций. \
Дальше идут дополнительные задания от препода ([@Kyoto67](https://github.com/kyoto67)) 
4. Демонстрировать работу частичных заглушек с использованием шпионов на `Mockito`.
5. Реализовать проверку тестового покрытия.

Отчёт по работе должен содержать:

1. Текст задания, систему функций.
2. UML-диаграмму классов разработанного приложения.
3. Описание тестового покрытия с обоснованием его выбора.
4. Графики, построенные csv-выгрузкам, полученным в процессе интеграции приложения.
5. Выводы по работе.

Вопросы к защите лабораторной работы:

1. Цели и задачи интеграционного тестирования. Расположение фазы интеграционного тестирования в последовательности тестов; предшествующие и последующие виды тестирования ПО.
2. Алгоритм интеграционного тестирования.
3. Концепции и подходы, используемые при реализации интеграционного тестирования.
4. Программные продукты, используемые для реализации интеграционного тестирования. Использование JUnit для интеграционных тестов.
5. Автоматизация интеграционных тестов. ПО, используемое для автоматизации интеграционного тестирования.

## Выполнение
| .pdf (Light)                            | .pdf (Dark)                                   | .pdf (AMOLED)                                   |
|-----------------------------------------|-----------------------------------------------|-------------------------------------------------|
| [Тык](../docs/reports/ST_Report_II.pdf) | [Тык](../docs/reports/ST_Report_II(dark).pdf) | [Тык](../docs/reports/ST_Report_II(AMOLED).pdf) |

Тригонометрические функции:

| Класс                                                                   | Зависимости | Формула                                           |
|-------------------------------------------------------------------------|-------------|---------------------------------------------------|
| [**cos(x)**](../src/main/java/ru/itmo/cs/kdot/lab2/trig/Cosine.java)    | —           | $$\sum_{i=0}^{n}(-1)^i\cdot\frac{x^{2i}}{(2i)!}$$ |
| [**sec(x)**](../src/main/java/ru/itmo/cs/kdot/lab2/trig/Secant.java)    | `cos(x)`    | $$\frac{1}{cos(x)}$$                              |
| [**csc(x)**](../src/main/java/ru/itmo/cs/kdot/lab2/trig/Cosecant.java)  | `sec(x)`    | $$sec(x - \frac{\pi}{2})$$                        |
| [**tg(x)**](../src/main/java/ru/itmo/cs/kdot/lab2/trig/Tangent.java)    | `sec(x)`    | $$\pm\sqrt{sec^2(x) - 1}$$                        |
| [**ctg(x)**](../src/main/java/ru/itmo/cs/kdot/lab2/trig/Cotangent.java) | `csc(x)`    | $$\pm\sqrt{csc^2(x) - 1}$$                        |

---
Логарифмические функции:

| Класс                                                                        | Зависимости | Формула                                                     |
|------------------------------------------------------------------------------|-------------|-------------------------------------------------------------|
| [**ln(x)**](../src/main/java/ru/itmo/cs/kdot/lab2/log/NaturalLogarithm.java) | —           | $$2\sum_{i=0}^{n}\frac{z^{2i+1}}{2i+1}, z=\frac{x-1}{x+1}$$ |
| [**logₐ(x)**](../src/main/java/ru/itmo/cs/kdot/lab2/log/BaseNLogarithm.java) | `ln(x)`     | $$\frac{ln(x)}{ln(a)}$$                                     |

> [!IMPORTANT]
> Замечания:
> 1. Я попытался реализовать функцию без использования синуса. Если вас интересует реализация с этой функцией, то загляните в [репу](https://github.com/maxbarsukov-itmo/tpo-2) Макса Барсукова. Там принцип реализации такой же, как и мой.
> 2. В связи с первым пунктом я много страдал с проблемой погрешности (судя по довольно нестандартным формулам). Я рекомендую не страдать хернёй и оставить реализацию как можно проще, так как дисциплина ориентирована на деятельности, связанные с тестированием, а не с вычислительной математикой.
> 3. Также реализовано создание графиков функции с помощью использования JFreeChart. Такого требования нигде не указано (но графики должны рисовать), поэтому рекомендую использовать `matplotlib` из `Python`. [Пример реализации](https://github.com/Kyoto67/vt-labs-3/tree/tpo-lab2) у моего препода.
> 4. У меня в интеграционных тестах также проверяется корректность значении самих функции. Такая практика **строго не рекомендуется**, так как этим по сути занимается не интеграционные тесты, а [модульные](https://github.com/orgs/17starplatinum/TEST-Lab1).
