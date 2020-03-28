package com.kirill.vakhrushev.searchaggregator.service;

import com.kirill.vakhrushev.searchaggregator.model.SearchResult;

import java.util.ArrayList;
import java.util.List;

public abstract class SearchServiceClient {

    public String getServiceName(){
        return "default";
    }

    public List<SearchResult> search(String query) throws InterruptedException {
        List<SearchResult> result = new ArrayList<>();
        result.add(new SearchResult(getServiceName(), "title1", "reference1.com", 1));
        result.add(new SearchResult(getServiceName(), "title2", "reference2.com", 2));
        result.add(new SearchResult(getServiceName(), "title3", "reference3.com", 3));
        result.add(new SearchResult(getServiceName(), "title4", "reference4.com", 4));
        result.add(new SearchResult(getServiceName(), "title5", "reference5.com", 5));
        return result;
    }
}
