package com.example.factorysimulation.models.nodes.stock;

import com.example.factorysimulation.models.nodes.providers.Provider;

import java.util.List;

public interface Stock {

    public Object getLock();

    int getDetailsSize();

    List<Provider> getProviders();

    int getDetailsCount();

    void setSize(int parseInt);

    boolean hasLimit();

    int getLimit();
}
