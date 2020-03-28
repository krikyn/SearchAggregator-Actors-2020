package com.kirill.vakhrushev.searchaggregator.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import com.kirill.vakhrushev.searchaggregator.model.SearchResult;
import com.kirill.vakhrushev.searchaggregator.service.SearchServiceClient;
import scala.concurrent.duration.FiniteDuration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MasterSearchActor extends AbstractActor {

    List<SearchServiceClient> clients;
    List<SearchResult> result;
    private ActorRef querySender;
    int completed = 0;

    public MasterSearchActor(List<SearchServiceClient> clients) {
        super();
        this.clients = clients;
        result = new ArrayList<>();
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, query -> {
                    this.querySender = getSender();
                    clients.forEach(client -> getContext()
                            .actorOf(
                                    Props.create(ChildSearchActor.class, client)
                            ).tell(query, self())
                    );

                    context().system().scheduler()
                            .scheduleOnce(
                                    FiniteDuration.apply(100, TimeUnit.MILLISECONDS),
                                    this.self(),
                                    new StopMsg(),
                                    context().system().dispatcher(),
                                    self()
                            );
                })
                .match(List.class, newResults -> {
                    System.out.println("New result");
                    completed++;
                    result.addAll(newResults);
                    if (completed == clients.size()) {
                        self().tell(new StopMsg(), self());
                    }
                })
                .match(StopMsg.class, msg -> {
                    querySender.tell(result, self());
                    System.out.println("killing master");
                    getContext().stop(this.self());
                })
                .build();
    }

    private static final class StopMsg {
    }
}
