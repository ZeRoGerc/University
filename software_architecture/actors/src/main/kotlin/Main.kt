import actor.MasterSearchActor
import akka.actor.AbstractActor
import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.Status
import entity.CombinedResponse
import entity.SearchRequest

class ReceiverActor : AbstractActor() {
    override fun createReceive(): Receive {
        return receiveBuilder()
                .match(CombinedResponse::class.java) { response -> handleResponse(response) }
                .match(Status.Failure::class.java) { terminate() }
                .build()
    }

    private fun handleResponse(combined: CombinedResponse) {
        combined.data.forEach(::println)
        context.system.terminate()
    }

    private fun terminate() {
        println("Terminated due to timeout")
        context.system.terminate()
    }
}

fun doRequest(request: SearchRequest) {
    val system = ActorSystem.create("iot-system")
    val searchActor = system.actorOf(Props.create(MasterSearchActor::class.java), "master")
    val receiverActor = system.actorOf(Props.create(ReceiverActor::class.java), "receiver")
    searchActor.tell(request, receiverActor)
}

fun main(args: Array<String>) {
    val request = SearchRequest(query = "cats")
    doRequest(request)
}