package server

import io.reactivex.netty.protocol.http.server.HttpServer
import rx.Observable


/**
 * Demo:
 *
 * http://localhost:8080/user/list
 * http://localhost:8080/user/add?login=ulad&currency=RUB
 * http://localhost:8080/user/list
 * http://localhost:8080/product/list
 * http://localhost:8080/product/add?title=phone&currency=USD&price=500
 * http://localhost:8080/product/list
 * http://localhost:8080/user/browse?user=ulad
 */
object HttpServer {

    @JvmStatic
    fun main(args: Array<String>) {
        val provider = ModelsProvider("rxserver2")
        val handler = RequestsHandler(
                userModel = provider.userModel,
                productsModel = provider.productsModel
        )

        HttpServer
                .newServer(8080)
                .start({ req, resp ->
                    val path = req.decodedPath

                    val result: Observable<String> = when (path) {
                        "/user/list" -> handler.handleUserList(req)
                        "/user/add" -> handler.handleUserAdd(req)
                        "/user/browse" -> handler.handleBrowseProducts(req)
                        "/product/list" -> handler.handleProductsList(req)
                        "/product/add" -> handler.handleProductsAdd(req)
                        else -> Observable.just("Error occurred!")
                    }
                    resp.writeString(result)
                })
                .awaitShutdown()
    }
}