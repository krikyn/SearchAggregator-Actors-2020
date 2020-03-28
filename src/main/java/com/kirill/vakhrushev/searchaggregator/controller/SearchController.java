package com.kirill.vakhrushev.searchaggregator.controller;

import com.kirill.vakhrushev.searchaggregator.model.SearchResult;
import com.kirill.vakhrushev.searchaggregator.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class SearchController {

    @GetMapping("/search")
    public String searching(@RequestParam(required = false) String query, Map<String, Object> model) {

        List<SearchServiceClient> clients = new ArrayList<>();
        clients.add(new BingClient());
        clients.add(new GoogleClient());
        clients.add(new YandexClient());

        if (query != null) {
            MasterClient masterClient = new MasterClient(clients);
            List<SearchResult> result = masterClient.search(query);
            model.put("results", result);
            model.put("query", query);
        } else {
            model.put("query", "—");
        }
        return "search";
    }
}