package com.example.littlelemon.presentation.order

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.littlelemon.R
import com.example.littlelemon.components.TopAppBar
import com.example.littlelemon.domain.OrderLineItem
import com.example.littlelemon.domain.OrderSummary
import com.example.littlelemon.presentation.order.OrderViewModel
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun OrderScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: OrderViewModel = hiltViewModel()
) {
    val orders by viewModel.orders.collectAsState()
    val itemsByOrder by viewModel.itemsByOrder.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                iconMiddle = R.drawable.littlelemonimgtxt_nobg,
                navController = navController,
            )
        }
    ) { innerPadding ->
        if (orders.isEmpty()) {
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Text(
                    "No orders yet.",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(orders, key = { it.id }) { order ->
                    OrderCard(
                        order = order,
                        items = itemsByOrder[order.id],
                        onExpand = { viewModel.itemsLoaded(order.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun OrderCard(
    modifier: Modifier = Modifier,
    order: OrderSummary,
    items: List<OrderLineItem>?,
    onExpand: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    //Order Text
                    Text(
                        "Order • ${order.id.take(6).uppercase(Locale.getDefault())}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    //Order time
                    Text(
                        formatTimestamp(order.createdAt),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                //Order Total
                Column(horizontalAlignment = Alignment.End) {
                    Text("Total", style = MaterialTheme.typography.labelMedium)
                    Text("$${money(order.total)}", fontWeight = FontWeight.Bold)
                }
            }
            Spacer(Modifier.height(8.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Items: ${order.itemCount}")
                Text(
                    if (expanded) "Hide items" else "Show items",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .clickable {
                            if (!expanded) onExpand()
                            expanded = !expanded
                        }
                )
            }
            Spacer(Modifier.height(8.dp))
            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    when {
                        items == null -> {
                            Text("Loading items…", style = MaterialTheme.typography.bodySmall)
                        }

                        items.isEmpty() -> {
                            Text("No items", style = MaterialTheme.typography.bodySmall)
                        }

                        else -> {
                            items.forEach { li ->
                                Row(
                                    Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("${li.quantity} × ${li.title}")
                                    Text(
                                        "$${money(li.lineTotal)}",
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                                Spacer(Modifier.height(6.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun money(v: Double) = String.format(Locale.US, "%.2f", v)
private fun formatTimestamp(ts: Timestamp?): String {
    if (ts == null) return "—"
    val date = ts.toDate()
    val fmt = SimpleDateFormat("MMM d, yyyy • h:mm a", Locale.getDefault())
    return fmt.format(date)
}
