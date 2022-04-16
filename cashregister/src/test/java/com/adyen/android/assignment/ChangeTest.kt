package com.adyen.android.assignment

import com.adyen.android.assignment.money.Bill
import com.adyen.android.assignment.money.Change
import com.adyen.android.assignment.money.Coin
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class ChangeTest {
    @Test
    fun testEquals() {
        val expected = Change()
            .add(Coin.FIVE_CENT, 3)
            .add(Coin.TWO_CENT, 1)
            .add(Bill.FIFTY_EURO, 2)
        val actual = Change()
            .add(Bill.FIFTY_EURO, 2)
            .add(Coin.FIVE_CENT, 3)
            .add(Coin.TWO_CENT, 1)
        assertEquals(expected, actual)
    }

    @Test
    fun testElementsDiffer() {
        val expected = Change()
            .add(Coin.TWO_EURO, 4)
            .add(Bill.TEN_EURO, 1)
            .add(Coin.FIFTY_CENT, 3)
            .add(Coin.TWENTY_CENT, 2)
        val actual = Change()
            .add(Coin.TWO_EURO, 4)
            .add(Coin.TEN_CENT, 1)
            .add(Coin.FIFTY_CENT, 3)
            .add(Coin.TWENTY_CENT, 2)
        assertNotEquals(expected, actual)
    }

    @Test
    fun testCountsDiffer() {
        val expected = Change()
            .add(Coin.TWO_EURO, 4)
            .add(Bill.ONE_HUNDRED_EURO, 1)
            .add(Coin.FIFTY_CENT, 3)
            .add(Coin.TWENTY_CENT, 2)
        val actual = Change()
            .add(Coin.TWO_EURO, 3)
            .add(Coin.TWENTY_CENT, 1)
            .add(Coin.FIFTY_CENT, 2)
            .add(Bill.ONE_HUNDRED_EURO, 1)
        assertNotEquals(expected, actual)
    }
}
