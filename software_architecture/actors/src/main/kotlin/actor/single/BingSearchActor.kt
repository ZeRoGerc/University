package actor.single

import client.SearchClient
import entity.SearchEngine.BING
import entity.SearchRequest
import entity.SearchResponse

class BingSearchActor : SearchActor() {
    override val client: SearchClient
        get() = SearchClient(
                engine = BING,
                url = "http://localhost:8081/bing"
        )

    override fun doRequest(searchRequest: SearchRequest): SearchResponse {
        Thread.sleep(5000)
        return super.doRequest(searchRequest)
    }
}