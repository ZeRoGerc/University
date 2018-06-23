package actor.single

import akka.actor.AbstractActor
import client.SearchClient
import entity.SearchRequest
import entity.SearchResponse

abstract class SearchActor : AbstractActor() {

    abstract val client: SearchClient

    override fun createReceive(): Receive {
        return receiveBuilder()
                .match(SearchRequest::class.java) { request ->
                    sender.tell(doRequest(request), self)
                }
                .build()
    }

    open fun doRequest(searchRequest: SearchRequest): SearchResponse {
        val json = client.doRequest(query = searchRequest.query)
        return SearchResponse(
                engine = client.engine,
                response = json
        )
    }
}