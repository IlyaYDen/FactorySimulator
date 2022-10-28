package com.example.factorysimulation.models.nodes.providers;

import com.example.factorysimulation.models.details.Detail;
import com.example.factorysimulation.models.details.DetailsEnum;
import com.example.factorysimulation.models.nodes.ModelNode;
import com.example.factorysimulation.models.nodes.NodeEnum;
import com.example.factorysimulation.models.nodes.stock.DetailsStock;
import com.example.factorysimulation.models.nodes.stock.Gettable;

import java.util.ArrayList;
import java.util.List;

public class DetailProvider extends ModelNode implements Provider, Runnable {

    public static volatile int id;
    final Object lock = new Object();
    DetailsEnum name;
    int delay;
    //DetailsStock detailsStock;
    Gettable getter;


    public DetailProvider() {};
    public DetailProvider(DetailsEnum s, int i) {
        name = s;
        delay = i;
    }

    @Override
    public Object getLock7(){
        return lock;
    }

    @Override
    public boolean hasDetails() {
        return d != null;
    }

    @Override
    public void setCons(DetailsStock detailsStock) {
        this.getter = detailsStock;
    }

    @Override
    public String getName() {
        return name.name() + "Provider";
    }

    @Override
    public void askNewDetails() {
        synchronized (lock) {
            lock.notifyAll();
        }
    }

    @Override
    public Detail getDetail() throws InterruptedException {
        return null;
    }


    @Override
    public int getWorkers() {
        return 0;
    }

    @Override
    public void setInputConnections(ModelNode n) {
    }

    @Override
    public void setOutputConnections(ModelNode n) {
        System.out.println("setInputConnections provider");
        if(n instanceof Gettable) {
            System.out.println("ok provider");
            getter = (Gettable) n;
        }
    }

    Detail d = null;

    @Override
    public void run() {

        System.out.println(getter.getName() + " start");
        synchronized (lock) {
            d = new Detail(id++,name);
            //System.out.println(detailsStock.name + " Provider adding: " + detailsStock.getDetailType().toString());
            if (getter.isNotFull()) {
                //synchronized (detailsStock.lock2){
                //    detailsStock.lock2.notify();
                //}
            }
            else {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        try {
            Thread.sleep(delay);
            System.out.println(getter.getName() + " added " + (getter.getDetailsSize()+1));
            getter.addDetail(d);
            d = null;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        synchronized (getter.getLock()){
            getter.getLock().notify();
        }

        if(Thread.currentThread().isInterrupted())
            System.out.println("Interrupted");
    }

    //public void start(Gettable gettable) {
    //    //System.out.println(detailsStock.name + " EngineProvider starts: " + detailsStock.getDetailType().toString());
    //    this.getter = gettable;
    //    //Thread thread = new Thread(this);
    //    //thread.start();
    //}

    @Override
    public DetailsEnum getDetailType() {
        return name;
    }

    @Override
    public NodeEnum getNodeName() {
        return NodeEnum.Provider;
    }

    @Override
    public List<Detail> getDetails() {
        List<Detail> det = new ArrayList<>();
        if(d!=null)
            det.add(d);
        return det;
    }

    @Override
    public List<ModelNode> getInputNode() {
        return null;
    }
    @Override
    public void setDetails(DetailsEnum e) {
        name = e;
    }

    @Override
    public void setSpeed(int parseInt) {
        delay = parseInt;
    }

    @Override
    public boolean hasSpeed() {
        return delay > 100;
    }

    @Override
    public int getSpeed() {
        return delay;
    }
}
