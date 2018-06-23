package server

import entity.Currency
import io.netty.buffer.ByteBuf
import io.reactivex.netty.protocol.http.server.HttpServerRequest
import model.ProductsModel
import model.UserModel
import rx.Observable

class RequestsHandler(
        private val userModel: UserModel,
        private val productsModel: ProductsModel
) {

    fun handleUserList(request: HttpServerRequest<ByteBuf>): Observable<String> =
            userModel.getAllUsers().map { users -> users.toString() }


    fun handleUserAdd(
            request: HttpServerRequest<ByteBuf>
    ): Observable<String> {
        val login = request.getParameter("login")
        val currency = request.getParameter("currency")
        return userModel.insertUser(login, Currency.valueOf(currency))
                .andThen(Observable.just("User inserted!"))
    }

    fun handleBrowseProducts(
            request: HttpServerRequest<ByteBuf>
    ): Observable<String> {
        val userLogin = request.getParameter("user")

        return userModel.getAllUsers()
                .map { users -> users.first { user -> user.login == userLogin } }
                .flatMap { user -> productsModel.listProductsForUser(user) }
                .map { products -> products.toString() }
    }

    fun handleProductsList(
            request: HttpServerRequest<ByteBuf>
    ): Observable<String> {
        return productsModel.listProducts()
                .map { products -> products.toString() }
    }

    fun handleProductsAdd(
            request: HttpServerRequest<ByteBuf>
    ): Observable<String> {
        val title = request.getParameter("title")
        val currency = request.getParameter("currency")
        val price = request.getParameter("price").toDouble()

        return productsModel.addProduct(title, Currency.valueOf(currency), price)
                .andThen(Observable.just("Product inserted!"))
    }

    private fun HttpServerRequest<ByteBuf>.getParameter(key: String) = this.queryParameters[key]!![0]
}