package actor

import akka.actor.ActorRef
import akka.actor.ActorSystem

interface SearchActorProvider {
    fun getSearchActors(system: ActorSystem): List<ActorRef>
}