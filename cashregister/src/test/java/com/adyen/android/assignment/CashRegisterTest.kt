package com.adyen.android.assignment

import com.adyen.android.assignment.money.Bill
import com.adyen.android.assignment.money.Change
import com.adyen.android.assignment.money.Coin
import org.junit.Assert
import org.junit.Test

class CashRegisterTest {

    @Test
    fun verify_change_should_be_equals_to_amount_paid() {
        val cashRegisterChange = Change().apply {
            Bill.values().forEach { add(it, 5) }
            Coin.values().forEach { add(it, 5) }
        }
        val cashRegister = CashRegister(cashRegisterChange)

        val price = 2750L

        val amountPaid = Change().apply {
            add(Bill.TWENTY_EURO, 1) // 2000
            add(Bill.FIVE_EURO, 1) // 500
            add(Coin.ONE_EURO, 2) // 200
            add(Coin.TEN_CENT, 5) // 50
        }

        val actual = cashRegister.performTransaction(price, amountPaid)

        Assert.assertEquals(0L, actual.total)
    }

    @Test
    fun verify_change_should_be_with_expected_refund_amount() {
        val cashRegisterChange = Change().apply {
            Bill.values().forEach { add(it, 5) }
            Coin.values().forEach { add(it, 5) }
        }
        val cashRegister = CashRegister(cashRegisterChange)

        val price = 2680L

        val amountPaid = Change().apply {
            add(Bill.TWENTY_EURO, 1) // 2000
            add(Bill.FIVE_EURO, 1) // 500
            add(Coin.ONE_EURO, 2) // 200
        }

        val actual = cashRegister.performTransaction(price, amountPaid)

        Assert.assertEquals(20L, actual.total)
    }

    @Test
    fun verify_change_should_not_be_with_expected_refund_amount() {
        val cashRegisterChange = Change().apply {
            Bill.values().forEach { add(it, 3) }
            Coin.values().forEach { add(it, 3) }
        }
        val cashRegister = CashRegister(cashRegisterChange)

        val price = 33500L

        val amountPaid = Change().apply {
            add(Bill.ONE_HUNDRED_EURO, 1) // 10000
            add(Bill.FIFTY_EURO, 4) // 20000
            add(Bill.TEN_EURO, 3) // 3000
            add(Bill.FIVE_EURO, 4) // 2000
        }

        val actual = cashRegister.performTransaction(price, amountPaid)

        Assert.assertNotEquals(400L, actual.total)
    }

    @Test
    fun verify_change_refund_should_contains_bill_and_coin_equals_expected() {
        val cashRegisterChange = Change().apply {
            Bill.values().forEach { add(it, 2) }
            Coin.values().forEach { add(it, 2) }
        }
        val cashRegister = CashRegister(cashRegisterChange)

        val price = 3354L

        val amountPaid = Change().apply {
            add(Bill.TWENTY_EURO, 1) // 2000
            add(Bill.TEN_EURO, 1) // 1000
            add(Bill.FIVE_EURO, 1) // 500
        }

        val actual = cashRegister.performTransaction(price, amountPaid)

        Assert.assertEquals(146L, actual.total)
    }

    @Test
    fun verify_change_shopper_money_to_minimal_amount() {
        val cashRegisterChange = Change().apply {
            Bill.values().forEach { add(it, 2) }
            Coin.values().forEach { add(it, 2) }
        }
        val cashRegister = CashRegister(cashRegisterChange)

        val price = 0L

        val amountPaid = Change().apply {
            add(Bill.TWENTY_EURO, 5) // 10000
            add(Bill.TEN_EURO, 20) // 20000
            add(Bill.FIVE_EURO, 2) // 1000
        }

        val expectedMinimal = Change().apply {
            add(Bill.TWO_HUNDRED_EURO, 1)
            add(Bill.ONE_HUNDRED_EURO, 1)
            add(Bill.TEN_EURO, 1)
        }

        val actual = cashRegister.performTransaction(price, amountPaid)

        Assert.assertEquals(expectedMinimal, actual)
    }

    @Test
    fun verify_cash_register_should_tracks_change_expected() {
        val cashRegisterChange = Change().apply {
            Bill.values().forEach { add(it, 2) }
            Coin.values().forEach { add(it, 2) }
        }
        val cashRegister = CashRegister(cashRegisterChange)

        val firstPrice = 640L // amountPaid 650 = -10

        val firstAmountPaid = Change().apply {
            add(Bill.FIVE_EURO, 1) // 500
            add(Coin.ONE_EURO, 1) // 100
            add(Coin.FIFTY_CENT, 1) // 50
        }

        val secondPrice = 1550L // amountPaid 1600 = -50

        val secondAmountPaid = Change().apply {
            add(Bill.TEN_EURO, 1) // 1000
            add(Bill.FIVE_EURO, 1) // 500
            add(Coin.ONE_EURO, 1) // 100
        }

        val expectedChange = Change().apply {
            Bill.values().forEach { add(it, 2) }
            Coin.values().forEach { add(it, 2) }
            add(Bill.FIVE_EURO, 1)
            add(Coin.ONE_EURO, 1)
            add(Coin.FIFTY_CENT, 1)
            add(Bill.TEN_EURO, 1)
            add(Bill.FIVE_EURO, 1)
            add(Coin.ONE_EURO, 1)
            remove(Coin.TEN_CENT, 1)
            remove(Coin.FIFTY_CENT, 1)
        }

        cashRegister.performTransaction(firstPrice, firstAmountPaid)
        cashRegister.performTransaction(secondPrice, secondAmountPaid)

        Assert.assertEquals(expectedChange, cashRegisterChange)
    }

    @Test
    fun verify_cash_register_pay_back_price_to_shopper_money_to_expected() {
        val cashRegisterChange = Change().apply {
            Bill.values().forEach { add(it, 2) }
            Coin.values().forEach { add(it, 2) }
        }
        val cashRegister = CashRegister(cashRegisterChange)

        val price = -15650L // 156,50

        val amountPaid = Change.none()

        val expectedPayBackMinimal = Change().apply {
            add(Bill.ONE_HUNDRED_EURO, 1) // 10000
            add(Bill.FIFTY_EURO, 1) // 5000
            add(Bill.FIVE_EURO, 1) // 500
            add(Coin.ONE_EURO, 1) // 100
            add(Coin.FIFTY_CENT, 1) // 50
        }

        val actual = cashRegister.performTransaction(price, amountPaid)

        Assert.assertEquals(expectedPayBackMinimal, actual)
    }

    @Test(expected = CashRegister.TransactionException::class)
    fun verify_cash_register_does_not_have_enough_change_should_give_exception() {
        val cashRegisterTotal = Change().apply {
            Bill.values().forEach { add(it, 1) }
        }
        val cashRegister = CashRegister(cashRegisterTotal)

        val price = 640L // amountPaid 650 = -10

        val amountPaid = Change().apply {
            add(Bill.FIVE_EURO, 1) // 500
            add(Coin.ONE_EURO, 1) // 100
            add(Coin.FIFTY_CENT, 1) // 50
        }

        cashRegister.performTransaction(price, amountPaid)

        Assert.fail("An Exception CashRegister: Not enough change in cash register")
    }

    @Test(expected = CashRegister.TransactionException::class)
    fun verify_shopper_does_not_have_enough_amount_paid_should_give_exception() {
        val cashRegisterTotal = Change.max()
        val cashRegister = CashRegister(cashRegisterTotal)

        val price = 7999L

        val amountPaid = Change().apply {
            add(Bill.FIFTY_EURO, 1) // 5000
            add(Bill.FIVE_EURO, 3) // 1500
            add(Coin.TWO_EURO, 2) // 400
        }

        cashRegister.performTransaction(price, amountPaid)

        Assert.fail("An Exception : Shopper should pay more than product price! ")
    }

}
