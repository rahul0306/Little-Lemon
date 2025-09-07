package com.example.littlelemon.data.dish

import com.example.littlelemon.R

object DishRepository {
    fun getDish(id: Int) = Dishes.firstOrNull { it.id == id }
    val Dishes = listOf<Dish>(
        Dish(
            1,
            "Greek Salad",
            "The famous greek salad of crispy lettuce, peppers, olives, out Chicago style feta cheese, garnished with crunchy garlic and rosemary croutons.",
            "12.99",
            R.drawable.greeksalad,
            category = "Starters"
        ),
        Dish(
            2,
            "Lemon Dessert",
            "Traditional homemade Italian Lemon Ricotta Cake.",
            "8.99",
            R.drawable.lemondessert,
            category = "Dessert"
        ),
        Dish(
            3,
            "Bruschetta",
            "Our Bruschetta is made from grilled bread that has been smeared with garlic and seasoned with salt and olive oil.",
            "4.99",
            R.drawable.bruschetta,
            category = "Starters"
        ),
        Dish(
            4,
            "Grilled Fish",
            "Fish marinated in fresh orange and lemon juice. Grilled with orange and lemon wedges.",
            "19.99",
            R.drawable.grilledfish,
            category = "Specials"
        ),
        Dish(
            5,
            "Pasta",
            "Penne with fried aubergines, cherry tomatoes, tomato sauce, fresh chilli, garlic, basil & salted ricotta cheese.",
            "8.99",
            R.drawable.pasta,
            category = "Mains"
        ),
        Dish(
            6,
            "Lasagne",
            "Oven-baked layers of pasta stuffed with Bolognese sauce, béchamel sauce, ham, Parmesan & mozzarella cheese .",
            "7.99",
            R.drawable.lasagne,
            category = "Lunch"
        )
    )
}