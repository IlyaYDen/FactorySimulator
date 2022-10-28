package com.example.factorysimulation.models.nodes.stock;

import com.example.factorysimulation.models.details.Detail;
import com.example.factorysimulation.models.details.DetailsEnum;
import com.example.factorysimulation.models.nodes.ModelNode;
import com.example.factorysimulation.models.nodes.NodeEnum;
import com.example.factorysimulation.models.nodes.providers.Provider;

import java.util.*;

public class DetailsStock extends ModelNode implements Stock, Gettable, Provider {

    public int limit;
    //int delay;
    DetailsEnum detail;
    //ArrayList<Detail> details = new ArrayList<>();
    public String name;
    public /*static*/ final Object lock = 1;
    public final Object lock2 = 1;
    List<Detail> details =
            Collections.synchronizedList(new ArrayList<>());
    List<Provider> engineProviders = new ArrayList<>();


    //public DetailsStock(DetailsEnum detail, int i, int ii) {
    //    this.limit = i;
    //    this.delay = ii;
    //    this.detail = detail;
    //}

    public DetailsStock() {};
    public DetailsStock(String s, Provider[] providers, int limit) {
        name = s;
        this.engineProviders = List.of(providers);
        detail = engineProviders.get(0).getDetailType();
        this.limit = limit;
        for(Provider p:providers)
            p.setCons(this);
    }
    public DetailsStock(String s, DetailsEnum en, int limit) {
        name = s;
        detail = en;
        this.limit = limit;
    }



    @Override
    public String getName() {
        return name;
    }

    public boolean isNotFull() {
        return (details.size() + started)<(limit);
    }
    public boolean isNotFull(int a ) {
        return (details.size() + started-a)<(limit);
    }
    public final Object lock7 = 1;

    public void addDetail(Detail detail) {
        details.add(detail);
        //synchronized (lock2) {
        //    lock2.notify();
        //}

    }
    @Override
    public void askNewDetails(){
        for(Provider p : engineProviders)
            synchronized (p.getLock7()) {
                p.getLock7().notify();
            }
    }


    public Detail getDetail() throws InterruptedException {

        synchronized (lock) {
            while(details.size()==0)
                lock.wait();
            Detail dt = details.get(0);
            details.remove(dt);
            //for(Provider ep : engineProviders) {
            //    System.out.println(ep.getDetailType().toString());
            //    synchronized (ep.getLock7()) {
            //        ep.getLock7().notify();
            //    }
            //}
            return dt;
        }
    }

    @Override
    public void setDetails(DetailsEnum e) {
        detail = e;
    }

    @Override
    public void setSpeed(int parseInt) {
    }

    @Override
    public boolean hasSpeed() {
        return false;
    }

    @Override
    public int getSpeed() {
        return 0;
    }

    //for(Provider ep : engineProviders)
    //    synchronized (ep.getLock7()) {
    //        ep.getLock7().notify();
    //    }
    public DetailsEnum getDetailType() {
        return detail;
    }

    @Override
    public Object getLock7() {
        return lock2;
    }

    @Override
    public boolean hasDetails() {
        return details.size()>0;
    }

    @Override
    public void setCons(DetailsStock detailsStock) {

    }

    public void start(String s) {
        name = s;
    }

    public boolean hasDetail() {
        return details.size()>0;
    }
    public int getDetailsSize(){
        return details.size();
    }

    @Override
    public List<Provider> getProviders() {
        return engineProviders;
    }

    @Override
    public int getDetailsCount() {
        return details.size()+started;
    }

    @Override
    public void setSize(int parseInt) {
        limit = parseInt;
    }

    @Override
    public boolean hasLimit() {
        return limit>0;
    }

    @Override
    public int getLimit() {
        return limit;
    }

    int started = 0;
    @Override
    public void addStarted() {
        started++;
    }

    @Override
    public void removeStarted() {
        started--;
    }


    @Override
    public Object getLock() {
        return lock;
    }

    public int getStarted(){
        return started;
    }





    @Override
    public NodeEnum getNodeName() {
        return NodeEnum.Stock;
    }

    @Override
    public List<Detail> getDetails() {
        return details;
    }

    @Override
    public List<ModelNode> getInputNode() {
        List<ModelNode> nlist = new ArrayList<>();
        for(Provider p : engineProviders){
            nlist.add((ModelNode) p);
        }
        return nlist;
    }

    @Override
    public void run() {

    }

    @Override
    public int getWorkers() {
        return 0;
    }

    @Override
    public void setInputConnections(ModelNode n) {
        //System.out.println("setInputConnections provider");
        //if(n instanceof Provider) {
        //    engineProviders.add((Provider) n);
        //}

        if(n instanceof Provider) {
            engineProviders.add((Provider) n);
        }
    }

    @Override
    public void setOutputConnections(ModelNode n) {
    }

}
/*
        for(DetailsProvider<E> a : detailsProvider){
            ;
            System.out.println("DetailsStock for "+a.toString());
            Callable<E> c3 = a.start();

            Future<E> executionResult = threadPool.submit(c3);
            try {
                System.out.println(executionResult.get().getName());
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
 */
