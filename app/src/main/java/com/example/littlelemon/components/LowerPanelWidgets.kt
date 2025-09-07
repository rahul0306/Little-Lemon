package com.example.littlelemon.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.littlelemon.data.dish.Categories
import com.example.littlelemon.data.dish.Dish
import com.example.littlelemon.data.dish.DishRepository.Dishes
import com.example.littlelemon.navigation.Destinations
import java.util.Locale

@Composable
fun MenuListScreen(modifier: Modifier = Modifier, navController: NavController) {
    val categories = remember { listOf("All") + Categories.distinct() }

    var selected by rememberSaveable { mutableStateOf("All") }
    val filtered = remember(selected) {
        if (selected == "All") Dishes
        else Dishes.filter { it.category.equals(selected, ignoreCase = true) }
    }
    Column {
        LazyRow {
            items(items = Categories, key = { it }) { category ->
                MenuCategory(
                    modifier = Modifier.padding(start = 20.dp),
                    category = category,
                    selected = selected == category,
                    onClick = { selected = category }
                )
            }
        }
        LazyColumn(
            contentPadding = PaddingValues(3.dp)
        ) {
            items(filtered, key = { it.id }) { dish ->
                MenuDish(
                    dish = dish,
                    navController = navController
                )
                HorizontalDivider(modifier = Modifier.padding(start = 20.dp, end = 20.dp))
            }
        }
    }
}

@Composable
fun OrderOnline() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Text(
            "ORDER FOR DELIVERY!",
            style = TextStyle(
                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily.SansSerif
            ),
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
        )
    }
}

@Composable
fun MenuDish(
    modifier: Modifier = Modifier,
    dish: Dish,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .clickable {
                navController.navigate(Destinations.DISH_DETAIL.getDishId(dish.id))
            },
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(0.75f),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.Start
            ) {
                //Dish name
                Text(
                    dish.title.uppercase(Locale.getDefault()),
                    style = TextStyle(
                        fontWeight = FontWeight.Black,
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        fontFamily = FontFamily.SansSerif
                    )
                )
                //Dish description
                Text(
                    dish.description,
                    modifier = Modifier
                        .padding(top = 10.dp, bottom = 10.dp),
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        letterSpacing = 0.7.sp
                    )
                )
                //Dish price
                Text(
                    "$${dish.price}",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                    )
                )
            }
            Image(
                painter = painterResource(id = dish.image),
                "Menu Dish Image",
            )
        }
    }
}

@Composable
fun MenuCategory(
    modifier: Modifier = Modifier,
    category: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.padding(start = 20.dp, end = 20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Button(
            onClick = onClick,
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selected) Color(0xFFF4CE14) else Color.LightGray,
                contentColor = if (selected) Color.Black else Color.DarkGray
            ),
        ) {
            Text(
                category,
                style = TextStyle(
                    color = Color.Black,
                    fontWeight = FontWeight.Normal,
                    letterSpacing = 1.sp
                )
            )
        }
    }
}