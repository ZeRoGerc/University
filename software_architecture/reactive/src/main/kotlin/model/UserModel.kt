package model

import db.UserDriver
import entity.Currency
import entity.User
import rx.Completable
import rx.Observable

open class UserModel(private val userDriver: UserDriver) {

    fun getAllUsers(): Observable<List<User>> =
            userDriver.getAllUsers()
                    .collect(
                            { mutableListOf<User>() },
                            { users, user -> users.add(user) }
                    )
                    .map { users -> users.toList() }

    fun dropAllUsers(): Completable = userDriver.dropAllUsers()

    fun insertUser(login: String, currency: Currency): Completable =
            userDriver.insertUser(login, currency)
}