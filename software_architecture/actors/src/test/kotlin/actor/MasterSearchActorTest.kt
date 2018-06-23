package actor

import akka.actor.*
import entity.CombinedResponse
import entity.SearchEngine
import entity.SearchRequest
import entity.SearchResponse
import org.junit.Before
import org.junit.Test
import java.util.concurrent.CountDownLatch

class MasterSearchActorTest {

    @Before
    fun setUp() {
        receiveLatch = CountDownLatch(1)
    }

    @Test
    fun `should return empty on no actors`() {
        runTest(EmptyMasterActor::class.java, EmptyReceiverActor::class.java)
    }

    class EmptyMasterActor : MasterSearchActor() {
        override val provider: SearchActorProvider = object : SearchActorProvider {
            override fun getSearchActors(system: ActorSystem) = emptyList<ActorRef>()
        }
    }

    class EmptyReceiverActor : MockReceiverActor() {
        override fun checkData(response: CombinedResponse) {
            assert(response.data.isEmpty())
        }

        override fun countDown() {
            receiveLatch.countDown()
        }
    }

    @Test
    fun `should work with normal actors`() {
        runTest(NormalMasterActor::class.java, NormalReceiverActor::class.java)
    }

    class NormalMasterActor : MasterSearchActor() {
        override val provider: SearchActorProvider = object : SearchActorProvider {
            override fun getSearchActors(system: ActorSystem) = listOf(
                    system.actorOf(Props.create(MockSearchActor::class.java))
            )
        }
    }

    class NormalReceiverActor : MockReceiverActor() {
        override fun checkData(response: CombinedResponse) {
            assert(!response.data.isEmpty())
        }

        override fun countDown() {
            receiveLatch.countDown()
        }
    }

    private fun runTest(masterClass: Class<*>, receiverClass: Class<*>) {
        val system = ActorSystem.create("iot-system")
        val searchActor = system.actorOf(Props.create(masterClass), "master")

        val request = SearchRequest(query = "cats")
        val receiverActor = system.actorOf(Props.create(receiverClass), "receiver")
        searchActor.tell(request, receiverActor)
        receiveLatch.await()
    }


    class MockSearchActor : AbstractActor() {
        override fun createReceive(): Receive {
            return receiveBuilder()
                    .match(SearchRequest::class.java) {
                        sender.tell(SearchResponse(SearchEngine.YANDEX, "cats!!!"), self)
                    }
                    .build()
        }
    }

    abstract class MockReceiverActor : AbstractActor() {

        abstract fun checkData(response: CombinedResponse)

        abstract fun countDown()

        override fun createReceive(): Receive {
            return receiveBuilder()
                    .match(CombinedResponse::class.java) { response -> handleResponse(response) }
                    .match(Status.Failure::class.java) { terminate() }
                    .build()
        }

        private fun handleResponse(combined: CombinedResponse) {
            context.system.terminate()
            checkData(combined)
            countDown()
        }

        private fun terminate() {
            context.system.terminate()
            assert(false)
        }
    }

    companion object {
        var receiveLatch = CountDownLatch(1)
    }
}
