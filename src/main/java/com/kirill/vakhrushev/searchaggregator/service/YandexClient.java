package com.kirill.vakhrushev.searchaggregator.service;

import com.kirill.vakhrushev.searchaggregator.model.SearchService;

public class YandexClient extends SearchServiceClient {

    public String getServiceName() {
        return SearchService.YANDEX.name();
    }

}
