package com.example.factorysimulation.models.nodes.controller;

import com.example.factorysimulation.models.details.Detail;
import com.example.factorysimulation.models.nodes.ModelNode;
import com.example.factorysimulation.models.nodes.NodeEnum;
import com.example.factorysimulation.models.nodes.providers.Provider;
import com.example.factorysimulation.models.nodes.stock.Stock;

import java.util.List;

public class DetailController extends ModelNode implements Controller {

    Stock stock;
    int less;
    public DetailController(){};
    public DetailController(Stock Stock, int less){
        this.stock = Stock;
        this.less = less;

    }
    boolean a = true;
/*
    public Timer start(){
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if( (stock.getDetailsCount() < less) && a){
                    for(Provider ep : stock.getProviders()) {
                        //System.out.println(ep.getDetailType().toString());
                        synchronized (ep.getLock7()) {
                            ep.getLock7().notify();
                        }
                    }
                    a = false;
                } else {
                    a = true;
                }
            }
        },100,100);
        return t;
    }*/

    @Override
    public NodeEnum getNodeName() {
        return NodeEnum.Controller;
    }

    @Override
    public List<Detail> getDetails() {
        return null;
    }

    @Override
    public List<ModelNode> getInputNode() {
        return null;
    }

    @Override
    public void run() {
        if( (stock.getDetailsCount() < less) && a){
            for(Provider ep : stock.getProviders()) {
                //System.out.println(ep.getDetailType().toString());
                synchronized (ep.getLock7()) {
                    ep.getLock7().notify();
                }
            }
            a = false;
        } else {
            a = true;
        }

        if(Thread.currentThread().isInterrupted())
            System.out.println("Interrupted");
    }

    @Override
    public int getWorkers() {
        return 0;
    }

    @Override
    public void setInputConnections(ModelNode n) {
        if(n instanceof Stock)
            stock = (Stock) n;
    }

    @Override
    public void setOutputConnections(ModelNode n) {
        if(n instanceof Stock)
            stock = (Stock) n;
    }

    @Override
    public void setMin(int parseInt) {
        less = parseInt;
    }

    @Override
    public boolean hasLimit() {
        return less >0;
    }

    @Override
    public int getLimit() {
        return less;
    }
}
