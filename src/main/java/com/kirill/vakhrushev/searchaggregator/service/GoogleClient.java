package com.kirill.vakhrushev.searchaggregator.service;

import com.kirill.vakhrushev.searchaggregator.model.SearchService;

public class GoogleClient extends SearchServiceClient {

    public String getServiceName() {
        return SearchService.GOOGLE.name();
    }

}
