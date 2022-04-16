package com.adyen.android.assignment.money

import java.util.TreeMap

class Change {
    private val map by lazy {
        TreeMap<MonetaryElement, Int>(Comparator { lhs, rhs ->
            lhs.minorValue.compareTo(rhs.minorValue)
        })
    }

    var total: Long = 0
        private set

    fun getElements(): Set<MonetaryElement> {
        return map.keys
    }

    fun getCount(element: MonetaryElement): Int {
        return map[element] ?: 0
    }

    fun add(element: MonetaryElement, count: Int): Change {
        return modify(element, count)
    }

    fun remove(element: MonetaryElement, count: Int): Change {
        return modify(element, -count)
    }

    private fun modify(element: MonetaryElement, count: Int): Change {
        val newCount = (map[element] ?: 0) + count
        if (newCount < 0) {
            throw IllegalArgumentException("Resulting count is less than zero.")
        }
        if (newCount == 0) {
            map.remove(element)
        } else {
            map[element] = newCount
        }
        total += element.minorValue * count
        return this
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Change) return false
        return map == other.map
    }

    override fun hashCode(): Int {
        return map.hashCode()
    }

    override fun toString(): String {
        return map.toString()
    }

    companion object {
        fun max(): Change {
            val change = Change()
            Bill.values().forEach { change.add(it, Int.MAX_VALUE) }
            Coin.values().forEach { change.add(it, Int.MAX_VALUE) }
            return change
        }

        fun none(): Change =
            Change()
    }
}
