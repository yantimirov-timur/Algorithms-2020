@file:Suppress("UNUSED_PARAMETER")

package lesson1

import java.io.File

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
    val lines = File(inputName).readLines()
    val temperatures = mutableListOf<Double>()

    File(outputName).bufferedWriter().use {
        for (line in lines) {//O(n)
            temperatures.add(line.toDouble())
        }

        temperatures.sort()//O(n log(N))

        for (i in temperatures) {//O(n)
            it.write(i.toString())
            it.newLine()
        }
    }

    //сложность O(n long(N))
    //ресурсоемкость O(n)
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
    val lines = File(inputName).readLines()
    val listInt = arrayListOf<Int>()

    for (i in lines) {
        listInt.add(i.toInt())
    }

    val max = listInt.maxOrNull()!!

    var arrayNumCount = Array(max + 1) { i -> 0 }

    for (i in listInt) {
        arrayNumCount[i]++
    }
    var max1 = arrayNumCount.maxOrNull()

    val max2 = arrayNumCount.indexOf(max1)

    for (i in 0 until listInt.size) {
        if (listInt[i] == max2) {
            listInt.add(max2)
            listInt.remove(listInt[i])
        }
    }

    File(outputName).bufferedWriter().use {
        for (i in listInt) {
            it.write(i.toString())
            it.newLine()

        }
    }

    /** val lines = File(inputName).readLines()
    val listNumbers = arrayListOf<Int>()
    val countNumbers = mutableMapOf<Int, Int>()

    for (i in lines) {//O(n)
    listNumbers.add(i.toInt())//O(1)
    }

    val numCounts = listNumbers.max()?.let { Array(it) { 0 } }

    for (num in listNumbers) {
    numCounts?.get(num)?.plus(1)
    }

    val sortedArray = Array(listNumbers.size, { 0 })

    var countSortedIndex = 0;


    if (numCounts != null) {
    for (n in 0..numCounts.size) {

    }
    }






    for (number in listNumbers) {
    if (number in countNumbers) {
    countNumbers[number] = countNumbers[number]!! + 1
    } else {
    countNumbers[number] = 1
    }
    }

    val maxCount = countNumbers.values.max()

    val listOfMaxCount = mutableListOf<Int>()

    for (i in countNumbers) {
    if (i.value == maxCount) {
    listOfMaxCount.add(i.key)
    }
    }

    val minimum = listOfMaxCount.min()

    for (i in 0 until listNumbers.size) {//O(n)
    if (listNumbers[i] == minimum) {
    listNumbers.add(minimum)
    listNumbers.remove(listNumbers[i])
    }
    }

    File(outputName).bufferedWriter().use {
    for (i in listNumbers) {
    it.write(i.toString())
    it.newLine()
    }
    }*/
    //сложность O(n)
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

