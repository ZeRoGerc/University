package actor.single

import client.SearchClient
import entity.SearchEngine.GOOGLE

class GoogleSearchActor : SearchActor() {
    override val client: SearchClient
        get() = SearchClient(
                engine = GOOGLE,
                url = "http://localhost:8081/google"
        )
}