package actor.single

import client.SearchClient
import entity.SearchEngine.YANDEX

class YandexSearchActor : SearchActor() {
    override val client: SearchClient
        get() = SearchClient(engine = YANDEX, url = "http://localhost:8081/yandex")
}