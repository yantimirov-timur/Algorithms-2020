@file:Suppress("UNUSED_PARAMETER")

package lesson7

/**
 * Наибольшая общая подпоследовательность.
 * Средняя
 *
 * Дано две строки, например "nematode knowledge" и "empty bottle".
 * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
 * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
 * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
 * Если общей подпоследовательности нет, вернуть пустую строку.
 * Если есть несколько самых длинных общих подпоследовательностей, вернуть любую из них.
 * При сравнении подстрок, регистр символов *имеет* значение.
 */
fun longestCommonSubSequence(first: String, second: String): String {
    val firstStringLength = first.length
    val secondStringLength = second.length
    val table = Array(firstStringLength + 1) { Array(secondStringLength + 1) { 0 } }

    //заполнение таблицы длинами
    for (i in 1..firstStringLength) {
        for (j in 1..secondStringLength) {
            if (first[i - 1] == second[j - 1]) {
                table[i][j] = table[i - 1][j - 1] + 1
            } else {
                table[i][j] = maxOf(table[i - 1][j], table[i][j - 1])
            }
        }
    }

    var result = ""

    //составление слова
    var i = firstStringLength
    var j = secondStringLength

    while (i > 0 && j > 0) {
        when {
            first[i - 1] == second[j - 1] -> {
                result += (first[i - 1])
                i -= 1
                j -= 1
            }
            table[i - 1][j] == table[i][j] -> i -= 1
            else -> j -= 1
        }
    }

    return result.reversed()
    //время O(n^2)
    //память O(n)
}

/**
 * Наибольшая возрастающая подпоследовательность
 * Сложная
 *
 * Дан список целых чисел, например, [2 8 5 9 12 6].
 * Найти в нём самую длинную возрастающую подпоследовательность.
 * Элементы подпоследовательности не обязаны идти подряд,
 * но должны быть расположены в исходном списке в том же порядке.
 * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
 * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
 * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
 */
fun longestIncreasingSubSequence(list: List<Int>): List<Int> {
    val n = list.size

    val subSequenceLength = Array(n) { 0 }
    val maxLengths = Array(n) { 0 }

    for (i in 0 until n) {
        subSequenceLength[i] = 1
        maxLengths[i] = -1
        for (j in 0 until i) {
            if (list[j] < list[i]) {
                if (1 + subSequenceLength[j] > subSequenceLength[i]) {
                    subSequenceLength[i] = 1 + subSequenceLength[j]
                    maxLengths[i] = j
                }
            }
        }
    }

    var answer = subSequenceLength[0]
    var index = 0

    for (i in 0 until n) {
        if (subSequenceLength[i] > answer) {
            answer = subSequenceLength[i]
            index = i
        }
    }

    val resultIndexes = mutableListOf<Int>()

    while (index != -1) {
        resultIndexes.add(index)
        index = maxLengths[index]
    }

    resultIndexes.reverse()

    val result = mutableListOf<Int>()
    for (i in resultIndexes) {
        result.add(list[i])
    }

    return result
    //время O(n^2)
    //память O(n)

}

/**
 * Самый короткий маршрут на прямоугольном поле.
 * Средняя
 *
 * В файле с именем inputName задано прямоугольное поле:
 *
 * 0 2 3 2 4 1
 * 1 5 3 4 6 2
 * 2 6 2 5 1 3
 * 1 4 3 2 6 2
 * 4 2 3 1 5 0
 *
 * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
 * В каждой клетке записано некоторое натуральное число или нуль.
 * Необходимо попасть из верхней левой клетки в правую нижнюю.
 * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
 * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
 *
 * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
 */
fun shortestPathOnField(inputName: String): Int {
    TODO()
}

// Задачу "Максимальное независимое множество вершин в графе без циклов"
// смотрите в уроке 5