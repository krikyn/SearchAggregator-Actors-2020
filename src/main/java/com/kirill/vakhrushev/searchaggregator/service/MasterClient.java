package com.kirill.vakhrushev.searchaggregator.service;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.util.Timeout;
import com.kirill.vakhrushev.searchaggregator.actor.MasterSearchActor;
import com.kirill.vakhrushev.searchaggregator.model.SearchResult;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static akka.pattern.PatternsCS.ask;

public class MasterClient extends SearchServiceClient {

    List<SearchServiceClient> clients;
    ActorSystem system = ActorSystem.create("system");

    public MasterClient(List<SearchServiceClient> clients) {
        this.clients = clients;
    }

    public List<SearchResult> search(String query) {
        ActorRef master = system.actorOf(Props.create(MasterSearchActor.class, clients), "master");
        final List<SearchResult> result = (List<SearchResult>) ask(master, query, Timeout.apply(500, TimeUnit.MILLISECONDS))
                .toCompletableFuture()
                .join();
        return result;
    }
}
