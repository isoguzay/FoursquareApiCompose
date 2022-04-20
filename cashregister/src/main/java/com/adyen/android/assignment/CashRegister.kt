package com.adyen.android.assignment

import com.adyen.android.assignment.money.Change
import kotlin.math.min

/**
 * The CashRegister class holds the logic for performing transactions.
 *
 * @param change The change that the CashRegister is holding.
 */
class CashRegister(private val change: Change) {
    /**
     * Performs a transaction for a product/products with a certain price and a given amount.
     *
     * @param price The price of the product(s).
     * @param amountPaid The amount paid by the shopper.
     *
     * @return The change for the transaction.
     *
     * @throws TransactionException If the transaction cannot be performed.
     */
    fun performTransaction(price: Long, amountPaid: Change): Change {

        return if (amountPaid.total >= price) {
            var moneyRefund = amountPaid.total - price
            if (moneyRefund == 0L) return Change.none()
            else {
                val changeResult = Change()
                val availableMonetaryElements = change.getElements()
                var index = change.getElements().size - 1

                while (moneyRefund != 0L && 0 <= index) {
                    val currentMoney = availableMonetaryElements.elementAt(index)
                    var currentMoneyIndex = change.getCount(currentMoney)

                    while (0 < currentMoneyIndex) {
                        val moneyCount = moneyRefund.toDouble() / currentMoney.minorValue.toDouble()

                        if (moneyCount >= 1) {
                            val withdrawMoneyCount =min(moneyCount.toInt(), change.getCount(currentMoney))
                            changeResult.add(currentMoney, withdrawMoneyCount)
                            change.remove(currentMoney, withdrawMoneyCount)
                            moneyRefund -= withdrawMoneyCount * currentMoney.minorValue
                        }
                        currentMoneyIndex--
                    }
                    index--
                }

                if (moneyRefund == 0L) return changeResult
                else throw TransactionException("The Cash register has not enough change")
            }
        } else {
            throw TransactionException("Shopper should pay more than product price!")
        }

    }

    class TransactionException(message: String, cause: Throwable? = null) :
        Exception(message, cause)
}

