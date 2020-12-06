package lesson5

/**
 * Множество(таблица) с открытой адресацией на 2^bits элементов без возможности роста.
 */
class KtOpenAddressingSet<T : Any>(private val bits: Int) : AbstractMutableSet<T>() {
    init {
        require(bits in 2..31)
    }

    private val capacity = 1 shl bits

    private val storage = Array<Any?>(capacity) { null }

    override var size: Int = 0

    private enum class Element { Deleted }

    /**
     * Индекс в таблице, начиная с которого следует искать данный элемент
     */
    private fun T.startingIndex(): Int {
        return hashCode() and (0x7FFFFFFF shr (31 - bits))
    }

    /**
     * Проверка, входит ли данный элемент в таблицу
     */
    override fun contains(element: T): Boolean = find(element) != null

    /**
     * Добавление элемента в таблицу.
     *
     * Не делает ничего и возвращает false, если такой же элемент уже есть в таблице.
     * В противном случае вставляет элемент в таблицу и возвращает true.
     *
     * Бросает исключение (IllegalStateException) в случае переполнения таблицы.
     * Обычно Set не предполагает ограничения на размер и подобных контрактов,
     * но в данном случае это было введено для упрощения кода.
     */
    override fun add(element: T): Boolean {
        val startingIndex = element.startingIndex()
        var index = startingIndex
        var current = storage[index]
        while (current != null) {
            if (current == element) {
                return false
            }
            index = (index + 1) % capacity
            check(index != startingIndex) { "Table is full" }
            current = storage[index]
        }
        storage[index] = element
        size++
        return true
    }

    /**
     * Удаление элемента из таблицы
     *
     * Если элемент есть в таблица, функция удаляет его из дерева и возвращает true.
     * В ином случае функция оставляет множество нетронутым и возвращает false.
     * Высота дерева не должна увеличиться в результате удаления.
     *
     * Спецификация: [java.util.Set.remove] (Ctrl+Click по remove)
     *
     * Средняя
     */

    /**
     * Поиск элемента в таблице
     */
    private fun find(element: T): Int? {
        var index = element.startingIndex()
        val start = index
        var current = storage[index]

        while (current != element) {
            index = (index + 1) % capacity
            if (index == start) return null
            current = storage[index]
        }
        return index
    }

    override fun remove(element: T): Boolean {
        if (!contains(element)) return false
        val elementIndex = find(element)

        checkNotNull(elementIndex)

        storage[elementIndex] = null
        size--
        return true
        //время O(n)
    }

    /**
     * Создание итератора для обхода таблицы
     *
     * Не забываем, что итератор должен поддерживать функции next(), hasNext(),
     * и опционально функцию remove()
     *
     * Спецификация: [java.util.Iterator] (Ctrl+Click по Iterator)
     *
     * Средняя (сложная, если поддержан и remove тоже)
     */

    override fun iterator(): MutableIterator<T> = OpenAddressingIterator()

    inner class OpenAddressingIterator : MutableIterator<T> {
        private var lastElement: Any? = null
        private var index = 0
        private var countElements = 0

        //время O(1)
        override fun hasNext(): Boolean = countElements < size

        //время O(n)
        override fun next(): T {
            if (!hasNext())
                throw IllegalStateException()

            while (storage[index] == null || storage[index] == Element.Deleted) {
                index++
            }
            lastElement = storage[index]
            index++
            countElements++

            return lastElement as T
        }

        //время O(1)
        override fun remove() {
            if (lastElement == null) throw IllegalStateException()

            storage[index - 1] = Element.Deleted
            lastElement = null
            size--
            countElements--
        }

    }
}