package com.kirill.vakhrushev.searchaggregator.service;

import com.kirill.vakhrushev.searchaggregator.model.SearchService;

public class BingClient extends SearchServiceClient {

    public String getServiceName() {
        return SearchService.BING.name();
    }

}
