package com.example.factorysimulation.models.details;

public class Detail {
    public Detail(int id, DetailsEnum name) {
        this.id = id;
        this.name = name;
    }

    private int id;

    private DetailsEnum name;

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Detail{" +
                "id=" + id +
                ", name='" + name.toString() + '\'' +
                '}';
    }

    public DetailsEnum getType() {
        return name;
    }
}
