package server

import com.mongodb.rx.client.MongoClient
import com.mongodb.rx.client.MongoClients
import com.mongodb.rx.client.MongoDatabase
import db.ProductDriver
import model.ProductsModel
import db.UserDriver
import model.UserModel

class ModelsProvider(databaseName: String) {

    private val dbClient: MongoClient = MongoClients.create("mongodb://localhost:27017")

    private val database: MongoDatabase = dbClient.getDatabase(databaseName)

    private val userDriver = UserDriver(database)

    private val productsDriver = ProductDriver(database)

    public val userModel = UserModel(userDriver)

    public val productsModel = ProductsModel(productsDriver)
}