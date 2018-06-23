package server

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.mock
import entity.Currency
import entity.Product
import entity.User
import io.netty.buffer.ByteBuf
import io.reactivex.netty.protocol.http.server.HttpServerRequest
import model.ProductsModel
import model.UserModel
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.verify
import rx.Completable
import rx.Observable
import kotlin.math.abs

class RequestsHandlerTest {

    @Test
    fun `product add should call model`() {
        val price = 1.5
        val productsModel = mock<ProductsModel> {
            on { addProduct(eq("title"), eq(Currency.RUB), eq(price)) } doReturn Completable.complete()
        }
        val userModel = mock<UserModel>()

        val requestHandler = RequestsHandler(userModel, productsModel)
        val request = mock<HttpServerRequest<ByteBuf>> {
            on { this.queryParameters } doReturn mapOf(
                    "title" to arrayListOf("title"),
                    "currency" to arrayListOf("RUB"),
                    "price" to arrayListOf("1.5")
            )
        }
        requestHandler.handleProductsAdd(request)

        val priceCaptor = ArgumentCaptor.forClass(Double::class.java)
        verify(productsModel).addProduct(
                eq("title"),
                eq(Currency.RUB),
                priceCaptor.capture()
        )
        assert(abs(priceCaptor.value - price) < 1e-9)
    }

    @Test
    fun `product list should return string of products`() {
        val products = listOf(
                Product("1", "phone1", Currency.USD, 300.0),
                Product("2", "phone2", Currency.EUR, 400.0)
        )
        val userModel = mock<UserModel>()
        val productsModel = mock<ProductsModel> {
            on { listProducts() } doReturn Observable.just(products)
        }

        val requestHandler = RequestsHandler(userModel, productsModel)
        val request = mock<HttpServerRequest<ByteBuf>>()
        val response = requestHandler.handleProductsList(request).toBlocking().first()

        verify(productsModel).listProducts()
        assert(response == products.toString())
    }

    @Test
    fun `user add should call model`() {
        val userModel = mock<UserModel> {
            on { insertUser(eq("user"), eq(Currency.RUB)) } doReturn Completable.complete()
        }
        val productsModel = mock<ProductsModel>()

        val requestHandler = RequestsHandler(userModel, productsModel)
        val request = mock<HttpServerRequest<ByteBuf>> {
            on { this.queryParameters } doReturn mapOf(
                    "login" to arrayListOf("user"),
                    "currency" to arrayListOf("RUB")
            )
        }
        requestHandler.handleUserAdd(request)

        verify(userModel).insertUser("user", Currency.RUB)
    }

    @Test
    fun `user list should return string of users`() {
        val users = listOf(
                User("1", "user1", Currency.RUB),
                User("2", "user2", Currency.EUR)
        )
        val userModel = mock<UserModel> {
            on { getAllUsers() } doReturn Observable.just(users)
        }
        val productsModel = mock<ProductsModel>()

        val requestHandler = RequestsHandler(userModel, productsModel)
        val request = mock<HttpServerRequest<ByteBuf>>()
        val response = requestHandler.handleUserList(request).toBlocking().first()

        verify(userModel).getAllUsers()
        assert(response == users.toString())
    }

    @Test
    fun `user browse should convert currencies`() {
        val user1 = User("1", "user1", Currency.RUB)
        val users = listOf(
                user1,
                User("2", "user2", Currency.EUR)
        )
        val userModel = mock<UserModel> {
            on { getAllUsers() } doReturn Observable.just(users)
        }

        val products = listOf(
                Product("1", "phone1", Currency.RUB, 10000.0),
                Product("2", "phone2", Currency.RUB, 20000.0)
        )
        val productsModel = mock<ProductsModel> {
            on { listProductsForUser(eq(user1)) } doReturn Observable.just(products)
        }

        val requestHandler = RequestsHandler(userModel, productsModel)
        val request = mock<HttpServerRequest<ByteBuf>> {
            on { this.queryParameters } doReturn mapOf(
                    "user" to arrayListOf("user1")
            )
        }
        val response = requestHandler.handleBrowseProducts(request).toBlocking().first()

        verify(userModel).getAllUsers()
        verify(productsModel).listProductsForUser(eq(user1))
        assert(response == products.toString())
    }
}