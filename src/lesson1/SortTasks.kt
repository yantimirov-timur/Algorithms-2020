@file:Suppress("UNUSED_PARAMETER")

package lesson1

import java.io.File
import kotlin.math.abs

/**
 * Сортировка времён
 *
 * Простая
 * (Модифицированная задача с сайта acmp.ru)
 *
 * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС AM/PM,
 * каждый на отдельной строке. См. статью википедии "12-часовой формат времени".
 *
 * Пример:
 *
 * 01:15:19 PM
 * 07:26:57 AM
 * 10:00:03 AM
 * 07:56:14 PM
 * 01:15:19 PM
 * 12:40:31 AM
 *
 * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
 * сохраняя формат ЧЧ:ММ:СС AM/PM. Одинаковые моменты времени выводить друг за другом. Пример:
 *
 * 12:40:31 AM
 * 07:26:57 AM
 * 10:00:03 AM
 * 01:15:19 PM
 * 01:15:19 PM
 * 07:56:14 PM
 *
 * В случае обнаружения неверного формата файла бросить любое исключение.
 */
fun sortTimes(inputName: String, outputName: String) {
    TODO()
}

/**
 * Сортировка адресов
 *
 * Средняя
 *
 * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
 * где они прописаны. Пример:
 *
 * Петров Иван - Железнодорожная 3
 * Сидоров Петр - Садовая 5
 * Иванов Алексей - Железнодорожная 7
 * Сидорова Мария - Садовая 5
 * Иванов Михаил - Железнодорожная 7
 *
 * Людей в городе может быть до миллиона.
 *
 * Вывести записи в выходной файл outputName,
 * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
 * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
 *
 * Железнодорожная 3 - Петров Иван
 * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
 * Садовая 5 - Сидоров Петр, Сидорова Мария
 *
 * В случае обнаружения неверного формата файла бросить любое исключение.
 */
fun sortAddresses(inputName: String, outputName: String) {
    TODO()
}

/**
 * Сортировка температур
 *
 * Средняя
 * (Модифицированная задача с сайта acmp.ru)
 *
 * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
 * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
 * Например:
 *
 * 24.7
 * -12.6
 * 121.3
 * -98.4
 * 99.5
 * -12.6
 * 11.0
 *
 * Количество строк в файле может достигать ста миллионов.
 * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
 * Повторяющиеся строки сохранить. Например:
 *
 * -98.4
 * -12.6
 * -12.6
 * 11.0
 * 24.7
 * 99.5
 * 121.3
 */

fun sortTemperatures(inputName: String, outputName: String) {
    val minTemperature = -2730
    val maxTemperature = 5000
    val lines = File(inputName).readLines()
    val numberList = mutableListOf<Int>()

    for (number in lines) {
        numberList.add((number.toDouble() * 10.0).toInt() - minTemperature)
    }

    val sortedList = countingSort(numberList.toIntArray(), maxTemperature - minTemperature)

    File(outputName).bufferedWriter().use {
        for (element in sortedList) {
            val actual = element + minTemperature

            val integerPartDivision = abs((element + minTemperature) / 10).toString()
            val fractionalPartDivision = abs((element + minTemperature) % 10).toString()

            when (actual.compareTo(0)) {
                -1 -> it.write("-$integerPartDivision.$fractionalPartDivision")
                1 -> it.write("$integerPartDivision.$fractionalPartDivision")
                0 -> it.write("0.0")
            }
            it.newLine()
        }
    }
    //сложность O(n + k)
    //ресурсоемкость O(n + k)
}

/**
 * Сортировка последовательности
 *
 * Средняя
 * (Задача взята с сайта acmp.ru)
 *
 * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
 *
 * 1
 * 2
 * 3
 * 2
 * 3
 * 1
 * 2
 *
 * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
 * а если таких чисел несколько, то найти минимальное из них,
 * и после этого переместить все такие числа в конец заданной последовательности.
 * Порядок расположения остальных чисел должен остаться без изменения.
 *
 * 1
 * 3
 * 3
 * 1
 * 2
 * 2
 * 2
 */
fun sortSequence(inputName: String, outputName: String) {
    val listNumbers = File(inputName).readLines().map { it.toInt() }.toMutableList()
    val countNumbers = mutableMapOf<Int, Int>()

    for (number in listNumbers) {
        if (number in countNumbers) {
            countNumbers[number] = countNumbers[number]!! + 1
        } else {
            countNumbers[number] = 1
        }
    }

// В связи с багом на котоеде (не находит метод minOrNull() и maxOrNull()
// вручную прописал поиск максимума и минимума
    var maxCount = 0
    for (number in countNumbers.values) {
        if (number > maxCount) {
            maxCount = number;
        }
    }

    //заполнение списка повторяющихся одинаковое количество раз значений, чтобы далее выбрать минимальный
    val listOfMaxCount = mutableListOf<Int>()
    for (number in countNumbers) {
        if (number.value == maxCount) {
            listOfMaxCount.add(number.key)
        }
    }

    var minimum = Int.MAX_VALUE
    for (number in listOfMaxCount) {
        if (number < minimum) {
            minimum = number
        }
    }

    val resultList = mutableListOf<Int>()

    for (i in 0 until listNumbers.size) {//O(n)
        if (listNumbers[i] != minimum) {
            resultList.add(listNumbers[i])
        }
    }

    for (i in 0 until listNumbers.size) {//O(n)
        if (listNumbers[i] == minimum) {
            resultList.add(listNumbers[i])
        }
    }

    File(outputName).bufferedWriter().use {
        for (i in resultList) {
            it.write(i.toString())
            it.newLine()
        }
    }
    //сложность O(n)
    //ресурсоемкость O(n)
}

/**
 * Соединить два отсортированных массива в один
 *
 * Простая
 *
 * Задан отсортированный массив first и второй массив second,
 * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
 * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
 *
 * first = [4 9 15 20 28]
 * second = [null null null null null 1 3 9 13 18 23]
 *
 * Результат: second = [1 3 4 9 9 13 15 20 23 28]
 */
fun <T : Comparable<T>> mergeArrays(first: Array<T>, second: Array<T?>) {
    TODO()
}

