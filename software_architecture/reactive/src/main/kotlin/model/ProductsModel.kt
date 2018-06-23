package model

import db.ProductDriver
import entity.Currency
import entity.Product
import entity.User
import rx.Completable
import rx.Observable

open class ProductsModel(private val productDriver: ProductDriver) {

    fun addProduct(title: String, currency: Currency, price: Double): Completable {
        return productDriver.insertProduct(title, currency, price)
    }

    fun listProducts(): Observable<List<Product>> {
        return productDriver.getAllProducts()
                .collect(
                        { mutableListOf<Product>() },
                        { products, product -> products.add(product) }
                )
                .map { products -> products.toList() }
    }

    fun listProductsForUser(user: User): Observable<List<Product>> {
        return listProducts()
                .map { products ->
                    products.map { product ->
                        Product(
                                id = product.id,
                                title = product.title,
                                currency = user.currency,
                                price = convertPrice(product.currency, user.currency, product.price)
                        )
                    }
                }
    }

    private fun convertPrice(from: Currency, to: Currency, price: Double): Double {
        if (from == to) {
            return price
        }

        val usdPrice = when (from) {
            Currency.RUB -> price / 56
            Currency.USD -> price
            Currency.EUR -> price / 0.82
        }

        return when (to) {
            Currency.RUB -> usdPrice * 56
            Currency.USD -> usdPrice
            Currency.EUR -> usdPrice * 0.82
        }
    }
}