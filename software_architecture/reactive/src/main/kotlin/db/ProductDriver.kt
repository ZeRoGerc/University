package db

import com.mongodb.rx.client.MongoCollection
import com.mongodb.rx.client.MongoDatabase
import entity.Currency
import entity.Product
import org.bson.Document
import rx.Completable
import rx.Observable
import java.util.*

class ProductDriver(private val database: MongoDatabase) {

    fun getAllProducts(): Observable<Product> =
            getProductsCollection()
                    .find()
                    .toObservable()
                    .map { createProductFromDocument(it) }

    fun dropAllProducts(): Completable =
            getProductsCollection().drop().toCompletable()

    fun insertProduct(title: String, currency: Currency, price: Double): Completable =
            getProductsCollection()
                    .insertOne(createProductDocument(title, currency, price))
                    .toCompletable()

    private fun getProductsCollection(): MongoCollection<Document> =
            database.getCollection("product")


    object ProductFields {
        val ID = "id"
        val TITLE = "title"
        val CURRENCY = "currency"
        val PRICE = "price"
    }

    companion object {
        private fun createProductDocument(title: String, currency: Currency, price: Double): Document =
                Document()
                        .append(ProductFields.ID, UUID.randomUUID().toString())
                        .append(ProductFields.TITLE, title)
                        .append(ProductFields.CURRENCY, currency.toString())
                        .append(ProductFields.PRICE, price)

        private fun createProductFromDocument(document: Document): Product =
                Product(
                        document.getString(ProductFields.ID),
                        document.getString(ProductFields.TITLE),
                        Currency.valueOf(document.getString(ProductFields.CURRENCY)),
                        document.getDouble(ProductFields.PRICE)
                )
    }
}