package lesson4

import java.util.ArrayDeque

/**
 * Префиксное дерево для строк
 */
class KtTrie : AbstractMutableSet<String>(), MutableSet<String> {

    private class Node {
        val children: MutableMap<Char, Node> = linkedMapOf()
    }

    private var root = Node()

    override var size: Int = 0
        private set

    override fun clear() {
        root.children.clear()
        size = 0
    }

    private fun String.withZero() = this + 0.toChar()

    private fun findNode(element: String): Node? {
        var current = root
        for (char in element) {
            current = current.children[char] ?: return null
        }
        return current
    }

    override fun contains(element: String): Boolean =
        findNode(element.withZero()) != null

    override fun add(element: String): Boolean {
        var current = root
        var modified = false
        for (char in element.withZero()) {
            val child = current.children[char]
            if (child != null) {
                current = child
            } else {
                modified = true
                val newChild = Node()
                current.children[char] = newChild
                current = newChild
            }
        }
        if (modified) {
            size++
        }
        return modified
    }

    override fun remove(element: String): Boolean {
        val current = findNode(element) ?: return false
        if (current.children.remove(0.toChar()) != null) {
            size--
            return true
        }
        return false
    }

    /**
     * Итератор для префиксного дерева
     *
     * Спецификация: [java.util.Iterator] (Ctrl+Click по Iterator)
     *
     * Сложная
     */
    override fun iterator(): MutableIterator<String> = TrieIterator()

    inner class TrieIterator : MutableIterator<String> {
        private var arrayDeque = ArrayDeque<String>()
        private var elementForDelete: String? = null

        init {
            makeWords(root, "")
        }

        /**
         * Рекурсивное заполнение очереди слов
         */
        private fun makeWords(parent: Node, partWord: String) {
            if (parent.children.isNotEmpty()) {
                parent.children.forEach { (char, node) ->
                    if (char == 0.toChar())//когда полностью составили слово, добавляем его в очередь
                        arrayDeque.addFirst(partWord)
                    else
                        makeWords(node, partWord + char)
                }
            }
        }

        override fun hasNext(): Boolean = arrayDeque.isNotEmpty() //время O(1)

        override fun next(): String {
            if (arrayDeque.isEmpty())
                throw IllegalStateException()

            val next = arrayDeque.pollFirst()
            elementForDelete = next

            return next
            //время O(1)
        }

        override fun remove() {
            if (elementForDelete == null) {
                throw IllegalStateException()
            } else {
                remove(elementForDelete)
                elementForDelete = null
            }
            //время O(n)
        }
    }
}