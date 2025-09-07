package com.example.littlelemon.presentation.cart

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.littlelemon.domain.CartItem

@Composable
fun CartList(
    modifier: Modifier = Modifier,
    items: List<CartItem>,
    onRemove: (Int) -> Unit,
    onClick: (Int) -> Unit
) {
    fun money(v: Double) = String.format(java.util.Locale.US, "%.2f", v)
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(7.dp),
        modifier = Modifier.fillMaxWidth().fillMaxHeight(0.6f)
    ) {
        items(items, key = { it.dishId }) { item ->
            val dismiss = rememberSwipeToDismissBoxState(
                confirmValueChange = { value ->
                    if (value == SwipeToDismissBoxValue.EndToStart || value == SwipeToDismissBoxValue.StartToEnd) {
                        onRemove(item.dishId); true
                    } else false
                }
            )
            //Swipe to delete item
            SwipeToDismissBox(
                state = dismiss,
                backgroundContent = {},
                content = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onClick(item.dishId) },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        //Item Quantity
                        Text(
                            "${item.quantity}",
                            style = TextStyle(
                                fontFamily = FontFamily.Serif,
                                fontWeight = FontWeight.Bold,
                                fontSize = MaterialTheme.typography.titleMedium.fontSize
                            )
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            "X",
                            style = TextStyle(
                                fontFamily = FontFamily.Serif,
                                fontWeight = FontWeight.Bold,
                                fontSize = MaterialTheme.typography.titleMedium.fontSize
                            )
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        //Item name
                        Text(
                            item.title,
                            style = TextStyle(
                                fontFamily = FontFamily.Serif,
                                fontWeight = FontWeight.Bold,
                                fontSize = MaterialTheme.typography.titleMedium.fontSize
                            )
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        //Item price
                        Text(
                            "$${money(item.price * item.quantity)}",
                            style = TextStyle(
                                fontFamily = FontFamily.Serif,
                                fontWeight = FontWeight.Bold,
                                fontSize = MaterialTheme.typography.titleMedium.fontSize
                            ),
                        )
                    }
                }
            )
        }
    }
}