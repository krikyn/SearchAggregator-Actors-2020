package com.kirill.vakhrushev.searchaggregator.actor;

import akka.actor.AbstractActor;
import com.kirill.vakhrushev.searchaggregator.service.SearchServiceClient;

public class ChildSearchActor extends AbstractActor {

    private final SearchServiceClient client;

    public ChildSearchActor(SearchServiceClient client) {
        super();
        this.client = client;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class,
                        query -> {
                            System.out.println("New child");
                            getSender().tell(client.search(query), getSender());
                        }
                ).build();
    }
}
