package com.kirill.vakhrushev.searchaggregator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class SearchResult {
    String service;
    String title;
    String reference;
    int rank;
}
