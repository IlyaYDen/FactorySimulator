package com.example.factorysimulation.models.nodes;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public enum NodeEnum {
    Constructor("Constructor", Color.BLACK),
    Consumer("Consumer", Color.RED),
    Controller("Controller", Color.BLUE),
    Provider("Provider", Color.GREEN),
    Stock("Stock", Color.GRAY),
    ;

    String name;
    Color color;
    NodeEnum(String constructor, Color black) {
        name = constructor;
        this.color = black;
    }

    public Paint getColor() {
        return color;
    }


}
