package com.adyen.android.assignment.money

enum class Coin(override val minorValue: Int) :
    MonetaryElement {
    TWO_EURO(2_00),
    ONE_EURO(1_00),
    FIFTY_CENT(50),
    TWENTY_CENT(20),
    TEN_CENT(10),
    FIVE_CENT(5),
    TWO_CENT(2),
    ONE_CENT(1)
}
