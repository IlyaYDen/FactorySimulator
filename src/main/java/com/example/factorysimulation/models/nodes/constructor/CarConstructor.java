package com.example.factorysimulation.models.nodes.constructor;

import com.example.factorysimulation.models.details.Detail;
import com.example.factorysimulation.models.details.DetailsEnum;
import com.example.factorysimulation.models.nodes.ModelNode;
import com.example.factorysimulation.models.nodes.NodeEnum;
import com.example.factorysimulation.models.nodes.providers.DetailProvider;
import com.example.factorysimulation.models.nodes.providers.Provider;
import com.example.factorysimulation.models.nodes.stock.DetailsStock;
import com.example.factorysimulation.models.nodes.stock.Gettable;

import java.util.*;

public class CarConstructor extends ModelNode implements Constructor, Provider, Gettable {
    List<Provider> detailsStocks = new ArrayList<>();
    List<DetailsEnum> de = new ArrayList<>();
    DetailsEnum detailsEnum;
    final Object lock7 = 1;
    int time;
    Detail d = null;
    int workers;

    ArrayList<Detail> dl = new ArrayList<>();
  // DetailsStock detailsStock;
    Gettable detailsStock;

    public CarConstructor() {

    }
    public CarConstructor(Provider[] detailsStocks, DetailsEnum[] de, int i, DetailsEnum type, int workers) {
        this.detailsStocks = Arrays.stream(detailsStocks).toList();
        this.workers = workers;
        this.de = Arrays.stream(de).toList();
        time = i;
        detailsEnum = type;
    }
    //public void start(DetailsStock detailsStock) {
    //    this.detailsStock = detailsStock;
    //}
/*
    public ExecutorService start(DetailsStock detailsStock, int workers) {
        this.detailsStock = detailsStock;
        ExecutorService tp = Executors.newFixedThreadPool(workers);
        for(int a = 0 ; a < workers ; a ++)
            tp.execute(this);
        return tp;
    }*/

    @Override
    public void run() {

                //System.out.println("worker " + Thread.currentThread().getName());
                synchronized (lock7) {
                    //System.out.println(detailsStock.limit + " " + detailsStock.getDetailsSize());
                    if (detailsStock.isNotFull()) {
                        try {

                            if(detailsStocks.size()==0) return;
                            int a = 0;
                            FORBREAK:
                            for (Provider ds : detailsStocks) {
                                synchronized (ds.getLock7()) {
                                    System.out.println(ds.hasSpeed());
                                    if (!ds.hasDetails()) {
                                        System.out.println("ПРОСИТ   " + ds.getName() + " " + ds.getDetailType().name());
                                        //System.out.println(ep.getDetailType().toString());
                                        ds.askNewDetails();
                                        ds.getLock7().wait();
                                    }
                                    if (de.stream().anyMatch(detailsEnum -> detailsEnum == ds.getDetailType())) {
                                        //FutureTask<Detail> df = new FutureTask(ds.getDetail());
                                        dl.add(ds.getDetail());//df.get());
                                    }
                                    DetailsEnum[] de2 = de.toArray(DetailsEnum[]::new);
                                    for (Detail detail : dl){
                                        for (int f = 0; f < de2.length; f++) {
                                            if (de2[f] == detail.getType()) {
                                                de2[f] = null;
                                                a++;
                                            }
                                        }
                                    }
                                    if (a >= de.size()) {
                                        ds.getLock7().notifyAll();

                                        break FORBREAK;
                                    }
                                }
                            }
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                    }
                    else {
                        try {
                            while (!detailsStock.isNotFull())

                                lock7.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                if(Thread.currentThread().isInterrupted())
                    System.out.println("Interrupted");

                if(detailsStock.isNotFull(1)) {
                    System.out.println("МАШИНЫ: Added " + (detailsStock.getDetailsSize() + 1));
                    construct(dl);
                }
            }

    @Override
    public int getWorkers() {
        return 0;
    }

    @Override
    public void setInputConnections(ModelNode n) {

        System.out.println("----- detailsStocks");
        if(n instanceof Provider) {

            System.out.println("hgfhgfhn detailsStocks");
            detailsStocks.add((Provider) n);
        }
        else
            System.out.println("CarConstr not Provider");
    }

    @Override
    public void setOutputConnections(ModelNode n) {
        System.out.println("test ----");
        if(n instanceof Gettable g){
            System.out.println("hfjhjgh ----");

            detailsStock = g;
        }
    }

    private void construct(ArrayList<Detail> d) {
        try {
            //System.out.println();
            detailsStock.addStarted();
            System.out.println("СБОРКА: Начал собирать ласточку!" + Thread.currentThread().getName());
            //System.out.println();
            Thread.sleep(time);
            detailsStock.addDetail(new Detail(DetailProvider.id++,detailsEnum));
            dl.clear();
            //System.out.println();
            System.out.println("СБОРКА: Машина сделана!"  + Thread.currentThread().getName());
            detailsStock.removeStarted();
            //System.out.println();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public DetailsEnum getDetailType() {
        return detailsEnum;
    }

    @Override
    public Object getLock7() {
        return lock7;
    }
    @Override
    public boolean hasDetails() {
        return d!=null;
    }

    @Override
    public List<DetailsEnum> getDetailsEnumInput() {
        return de;
    }

    @Override
    public boolean hasOutDetailsEnum() {
        return detailsEnum!=null;
    }

    @Override
    public DetailsEnum getOutDetailsEnum() {
        return detailsEnum;
    }

    @Override
    public boolean hasDetailsEnumInput() {
        return de.size()>0;
    }

    @Override
    public void setCons(DetailsStock detailsStock) {
        this.detailsStock = detailsStock;
    }

    @Override
    public String getName() {
        return detailsEnum.name()+"Constructor";
    }

    @Override
    public void askNewDetails() {
        for(Provider p : detailsStocks)
            synchronized (p.getLock7()) {
                p.getLock7().notify();
            }
    }

    @Override
    public boolean isNotFull() {
        return d == null && started==0;
    }

    public boolean isNotFull(int a ) {
        return isNotFull();
    }

    @Override
    public int getDetailsSize() {
        return d==null ? 0 : 1;
    }

    @Override
    public void addDetail(Detail detail) {
        d = detail;
    }

    final Object lock = 1;
    @Override
    public Detail getDetail() throws InterruptedException {

        synchronized (lock) {
            while (d==null) lock.wait();
            Detail det = d;
            d = null;

            for(Provider ep : detailsStocks) {
                synchronized (ep.getLock7()) {
                    ep.getLock7().notify();
                }
            }

            return det;
        }
    }


    @Override
    public void setDetails(DetailsEnum d) {
        de.add(d);
    }
    @Override
    public void setDetailOut(DetailsEnum d) {
        detailsEnum =d;
        System.out.print(d.name() + " | \n");
    }

    @Override
    public void setSpeed(int parseInt) {
        time = parseInt;
    }

    @Override
    public boolean hasSpeed() {
        return time > 100;
    }

    @Override
    public int getSpeed() {
        return time;
    }

    @Override
    public void setDetails(List<DetailsEnum> d) {
        de.clear();
        de.addAll(d);
        de.forEach(detailsEnum1 -> {System.out.print(de.indexOf(detailsEnum1)+ " " + detailsEnum1.name() + " | ");});
    }


    int started = 0;
    @Override
    public void addStarted() {
        started++;
    }

    @Override
    public void removeStarted() {

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
        return NodeEnum.Constructor;
    }

    @Override
    public List<Detail> getDetails() {
        return dl;
    }

    @Override
    public List<ModelNode> getInputNode() {
        List<ModelNode> nlist = new ArrayList<>();
        for(Provider p : detailsStocks){
            nlist.add((ModelNode) p);
        }
        return nlist;
    }



    double x;
    double y;
    @Override
    public void setXY(double x,double y) {
        this.x = x;
        this.y = y;
    }
    @Override
    public double getX(){
        return x;
    }
    @Override
    public double getY(){
        return y;
    }
}
