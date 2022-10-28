package com.example.factorysimulation.models.details;

public enum DetailsEnum {
    ENGINE("engine"),
    CARCASE("carcase"),
    ACCESSORY("accessory"),
    CAR("car"),
;
    String engine;
    DetailsEnum(String engine) {
        this.engine = engine;
    }
}
