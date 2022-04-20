package com.adyen.android.assignment.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.adyen.android.assignment.R

@Composable
fun CategoryChip(
    categoryName: String
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(
                vertical = dimensionResource(id = R.dimen.category_chip_padding_vertical),
                horizontal = dimensionResource(id = R.dimen.category_chip_padding_horizontal)
            )
            .border(
                width = dimensionResource(id = R.dimen.category_chip_border_width),
                color = Color.White,
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.category_chip_border_corner_shape))
            )
            .background(
                color = Color.Transparent,
            )
            .padding(dimensionResource(id = R.dimen.category_chip_padding))
    ) {
        Text(
            text = categoryName,
            fontWeight = FontWeight.Bold,
            fontSize = dimensionResource(id = R.dimen.category_chip_text_size).value.sp,
            modifier = Modifier.padding(dimensionResource(id = R.dimen.category_chip_text_padding))
        )
    }
}