package com.example.factorysimulation.models.nodes.consumer;

import com.example.factorysimulation.models.details.Detail;
import com.example.factorysimulation.models.nodes.ModelNode;
import com.example.factorysimulation.models.nodes.NodeEnum;
import com.example.factorysimulation.models.nodes.stock.Gettable;

import java.util.ArrayList;
import java.util.List;

public class DetailConsumer extends ModelNode implements Consumer {

    Gettable gettable;
    int delay;
    int workers;
    List<Detail> det = new ArrayList<>();
    public DetailConsumer(){};
    public DetailConsumer(Gettable gettable, int delay, int workers) {
        this.gettable = gettable;
        this.delay = delay;
        this.workers = workers;
    }

    //public Timer start() {
    //    ExecutorService tp = Executors.newFixedThreadPool(workers);
    //    for(int a = 0 ; a < workers ; a ++)
    //        tp.execute(this);
    //    return tp;
    //}

    @Override
    public void run() {

        try {
            Thread.sleep(delay);
            det.add(gettable.getDetail());
            System.out.println("Consumer забрал ласточку " + gettable.getDetailsSize());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if(Thread.currentThread().isInterrupted())
            System.out.println("Interrupted");
    }

    @Override
    public int getWorkers() {
        return workers;
    }

    @Override
    public void setInputConnections(ModelNode n) {
        if(n instanceof Gettable)
            gettable = (Gettable) n;
    }

    @Override
    public void setOutputConnections(ModelNode n) {

        System.out.println("setInputConnections provider");
        if(n instanceof Gettable) {
            System.out.println("ok provider");
            gettable = (Gettable) n;
        }
    }

    @Override
    public NodeEnum getNodeName() {
        return NodeEnum.Consumer;
    }

    @Override
    public List<Detail> getDetails() {
        return det;
    }

    @Override
    public List<ModelNode> getInputNode() {
        List<ModelNode> nlist = new ArrayList<>();
        nlist.add((ModelNode)gettable);

        return nlist;
    }

    @Override
    public void setSpeed(int speed) {
        delay = speed;
    }

    @Override
    public boolean hasSpeed() {
        return delay>100;
    }

    @Override
    public int getSpeed() {
        return delay;
    }
}
