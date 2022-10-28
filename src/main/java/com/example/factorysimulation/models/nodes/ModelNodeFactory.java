package com.example.factorysimulation.models.nodes;

import com.example.factorysimulation.Controller;
import com.example.factorysimulation.models.details.DetailsEnum;
import com.example.factorysimulation.models.nodes.constructor.CarConstructor;
import com.example.factorysimulation.models.nodes.consumer.DetailConsumer;
import com.example.factorysimulation.models.nodes.controller.DetailController;
import com.example.factorysimulation.models.nodes.providers.DetailProvider;
import com.example.factorysimulation.models.nodes.providers.Provider;
import com.example.factorysimulation.models.nodes.stock.DetailsStock;
import javafx.scene.Node;

public class ModelNodeFactory {
    public ModelNode create(NodeEnum e) {
        switch (e){
            case Constructor -> {
                return new CarConstructor();
            }
            case Consumer -> {
                return new DetailConsumer(null,1000,1);
            }
            case Controller -> {
                return new DetailController();
            }
            case Stock -> {
                return new DetailsStock("constructor ", DetailsEnum.CARCASE,10);
            }
            case Provider -> {
                return new DetailProvider(DetailsEnum.CARCASE, 300);
            }
        }
        throw new RuntimeException("ModelNodeFactory cant create it!");
    }
}
