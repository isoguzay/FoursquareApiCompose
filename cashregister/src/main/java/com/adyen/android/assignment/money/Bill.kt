package com.adyen.android.assignment.money

enum class Bill(override val minorValue: Int) :
    MonetaryElement {
    FIVE_HUNDRED_EURO(500_00),
    TWO_HUNDRED_EURO(200_00),
    ONE_HUNDRED_EURO(100_00),
    FIFTY_EURO(50_00),
    TWENTY_EURO(20_00),
    TEN_EURO(10_00),
    FIVE_EURO(5_00);
}
