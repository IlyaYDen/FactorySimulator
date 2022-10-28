package com.example.factorysimulation.models.nodes.stock;

import com.example.factorysimulation.models.details.Detail;
import com.example.factorysimulation.models.nodes.ModelNode;

public interface Gettable {
    String getName();

    boolean isNotFull();

    int getDetailsSize();

    void addDetail(Detail detail);
    public Detail getDetail() throws InterruptedException;

    void addStarted();

    void removeStarted();

    Object getLock();

    boolean isNotFull(int i);
}
