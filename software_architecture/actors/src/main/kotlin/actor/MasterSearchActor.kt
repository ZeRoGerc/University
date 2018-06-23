package actor

import akka.actor.AbstractActor
import akka.actor.ActorRef
import akka.util.Timeout
import entity.CombinedResponse
import entity.SearchRequest
import entity.SearchResponse
import scala.concurrent.duration.Duration
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException


open class MasterSearchActor : AbstractActor() {

    private val timeout = Timeout(Duration.create(10, TimeUnit.SECONDS))

    private var responses = mutableListOf<SearchResponse>()

    protected open val provider: SearchActorProvider = SearchActorProviderImpl()

    private var actors: List<ActorRef> = emptyList()

    private lateinit var searchSender: ActorRef

    override fun postStop() {
        actors.forEach { actor -> context.stop(actor) }
        super.postStop()
    }

    override fun createReceive(): AbstractActor.Receive {
        return receiveBuilder()
                .match(SearchRequest::class.java) { request -> doSearchRequest(request) }
                .match(SearchResponse::class.java) { response -> handleSingleResponse(response) }
                .match(SearchTimeout::class.java) { terminateByTimeout() }
                .build()
    }


    private fun doSearchRequest(request: SearchRequest) {
        searchSender = sender
        actors = provider.getSearchActors(context.system)
        actors.forEach { actorRef -> actorRef.tell(request, self) }

        context.system.scheduler().scheduleOnce(
                timeout.duration(),
                self,
                SearchTimeout(),
                context.dispatcher(),
                null
        )

        if (actors.isEmpty()) {
            searchSender.tell(CombinedResponse(responses), self)
        }
    }

    private fun handleSingleResponse(response: SearchResponse) {
        responses.add(response)
        if (responses.size == actors.size) {
            searchSender.tell(CombinedResponse(responses), self)
        }
    }

    private fun terminateByTimeout() {
        val ex = TimeoutException("Target actor timed out after " + timeout.toString())
        searchSender.tell(CombinedResponse(responses), self)
    }

    private class SearchTimeout
}