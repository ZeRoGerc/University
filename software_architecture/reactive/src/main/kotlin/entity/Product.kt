package entity

data class Product(
        val id: String,
        val title: String,
        val currency: Currency,
        val price: Double
)