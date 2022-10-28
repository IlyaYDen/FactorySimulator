package com.example.factorysimulation.models.nodes;

import com.example.factorysimulation.models.details.Detail;
import com.example.factorysimulation.models.nodes.stock.Gettable;
import javafx.scene.Node;

import java.util.List;
public abstract class ModelNode {

    abstract public NodeEnum getNodeName();
    abstract public List<Detail> getDetails();
    abstract public List<ModelNode> getInputNode();
    abstract public void run();
    abstract public int getWorkers();/*
    public void setXY(double x,double y);
    public double getX();
    public double getY();
    public void setSelected();
    public boolean isSelected();*/

    double x;
    double y;
    public void setXY(double x,double y) {
        this.x = x;
        this.y = y;
    }
    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }


    abstract public void setInputConnections(ModelNode n);
    abstract public void setOutputConnections(ModelNode n);


}
