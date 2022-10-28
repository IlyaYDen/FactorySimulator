package com.example.factorysimulation.models.nodes.constructor;

import com.example.factorysimulation.models.details.DetailsEnum;

import java.util.List;

public interface Constructor {
    void setSpeed(int parseInt);
    void setDetails(List<DetailsEnum> d);

    void setDetailOut(DetailsEnum d);

    List<DetailsEnum> getDetailsEnumInput();

    boolean hasOutDetailsEnum();

    DetailsEnum getOutDetailsEnum();

    boolean hasDetailsEnumInput();

}
