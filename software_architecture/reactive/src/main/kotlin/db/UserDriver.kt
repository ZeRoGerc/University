package db

import com.mongodb.rx.client.MongoCollection
import com.mongodb.rx.client.MongoDatabase
import entity.Currency
import entity.User
import org.bson.Document
import rx.Completable
import rx.Observable
import java.util.*

class UserDriver(private val database: MongoDatabase) {

    fun getAllUsers(): Observable<User> =
            getUsersCollection()
                    .find()
                    .toObservable()
                    .map { createUserFromDocument(it) }

    fun dropAllUsers(): Completable =
            getUsersCollection().drop().toCompletable()

    fun insertUser(login: String, currency: Currency): Completable =
            getUsersCollection()
                    .insertOne(createUserDocument(login, currency))
                    .toCompletable()

    private fun getUsersCollection(): MongoCollection<Document> =
            database.getCollection("user")

    object UserFields {
        val ID = "id"
        val LOGIN = "login"
        val CURRENCY = "currency"
    }

    companion object {
        private fun createUserDocument(login: String, currency: Currency): Document =
                Document()
                        .append(UserFields.ID, UUID.randomUUID().toString())
                        .append(UserFields.LOGIN, login)
                        .append(UserFields.CURRENCY, currency.toString())

        private fun createUserFromDocument(document: Document): User =
                User(
                        document.getString(UserFields.ID),
                        document.getString(UserFields.LOGIN),
                        Currency.valueOf(document.getString(UserFields.CURRENCY))
                )
    }
}