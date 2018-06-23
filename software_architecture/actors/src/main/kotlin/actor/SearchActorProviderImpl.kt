package actor

import actor.single.BingSearchActor
import actor.single.GoogleSearchActor
import actor.single.YandexSearchActor
import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props

class SearchActorProviderImpl : SearchActorProvider {

    override fun getSearchActors(system: ActorSystem) = listOf(
            getYandexSearchActor(system),
            getGoogleSearchActor(system),
            getBingSearchActor(system)
    )

    private fun getYandexSearchActor(system: ActorSystem): ActorRef =
            system.actorOf(Props.create(YandexSearchActor::class.java), "yandex-search")

    private fun getGoogleSearchActor(system: ActorSystem): ActorRef =
            system.actorOf(Props.create(GoogleSearchActor::class.java), "google-search")

    private fun getBingSearchActor(system: ActorSystem): ActorRef =
            system.actorOf(Props.create(BingSearchActor::class.java), "bing-search")
}