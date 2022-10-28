package com.example.factorysimulation;

import com.example.factorysimulation.models.details.DetailsEnum;
import com.example.factorysimulation.models.nodes.Connections;
import com.example.factorysimulation.models.nodes.ModelNode;
import com.example.factorysimulation.models.nodes.constructor.CarConstructor;
import com.example.factorysimulation.models.nodes.consumer.DetailConsumer;
import com.example.factorysimulation.models.nodes.controller.DetailController;
import com.example.factorysimulation.models.nodes.providers.DetailProvider;
import com.example.factorysimulation.models.nodes.providers.Provider;
import com.example.factorysimulation.models.nodes.stock.DetailsStock;
import com.example.factorysimulation.models.nodes.stock.Gettable;
import com.example.factorysimulation.view.MainView;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Controller extends Application {
    static final int SCREEN_SIZE_X = 800;
    static final int SCREEN_SIZE_Y = 830;
    static MainView mainView;
    @Override
    public void start(Stage stage) throws IOException {


        StackPane pane = new StackPane();
        pane.setAlignment(Pos.BOTTOM_CENTER);
        pane.getChildren().add(mainView);
        Scene scene = new Scene(pane,SCREEN_SIZE_X,SCREEN_SIZE_Y);
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);

    }

    public static Boolean start = false;

    public static void main(String[] args) {

/*

        DetailProvider engineProvider = new DetailProvider( DetailsEnum.ENGINE,5000);
        DetailProvider accessoryProvider1 = new DetailProvider(DetailsEnum.ACCESSORY,10000);
        DetailProvider accessoryProvider2 = new DetailProvider(DetailsEnum.ACCESSORY,10000);
        DetailProvider carcaseProvider = new DetailProvider(DetailsEnum.CARCASE,5000);

        DetailsStock detailsStock1 = new DetailsStock("ПОСТАВЩИК ДВИГАТЕЛЕЙ: ",new Provider[]{engineProvider},2);
        DetailsStock detailsStock2 = new DetailsStock("ПОСТАВЩИК АКСЕССУАРОВ: ",new Provider[]{accessoryProvider1,accessoryProvider2},2);
        DetailsStock detailsStock3 = new DetailsStock("ПОСТАВЩИК КУЗОВОВ: ",new Provider[]{carcaseProvider},2);
        //detailsStock1.addDetail(new Detail(1,DetailsEnum.ENGINE));

        CarConstructor carConstructor =
                new CarConstructor(
                        new DetailsStock[] {detailsStock1,detailsStock2,detailsStock3},
                        new DetailsEnum[] {DetailsEnum.ENGINE,DetailsEnum.ACCESSORY,DetailsEnum.CARCASE},10000,DetailsEnum.CAR, 1);


        DetailsStock detailsStock4 = new DetailsStock("МАШИНЫ: ",new Provider[]{carConstructor},10);

        DetailConsumer consumer = new DetailConsumer(detailsStock4, 15000,1);

        DetailController controller = new DetailController(detailsStock4, 5);


            Connections ced1 = new Connections(engineProvider, detailsStock1);
            Connections ce1d2 = new Connections(accessoryProvider1, detailsStock2);
            Connections ce2d2 = new Connections(accessoryProvider2, detailsStock2);
            Connections ced3 = new Connections(carcaseProvider, detailsStock3);

            Connections cs1con = new Connections(detailsStock1, carConstructor);
            Connections cs2con = new Connections(detailsStock2, carConstructor);
            Connections cs3con = new Connections(detailsStock3, carConstructor);

            Connections ccons = new Connections(carConstructor, detailsStock4);

            Connections csct = new Connections(detailsStock4, controller);
            Connections cctcon = new Connections(controller, carConstructor);

            Connections cconcs = new Connections(detailsStock4, consumer);

            List<Connections> con = new ArrayList<>();
            con.add(ced1);
            con.add(ce1d2);
            con.add(ce2d2);
            con.add(ced3);

            con.add(cs1con);
            con.add(cs2con);
            con.add(cs3con);

            con.add(ccons);
            con.add(csct);
            con.add(cctcon);
            con.add(cconcs);


            List<ModelNode> nodes = new ArrayList<>();
            nodes.add(engineProvider);
            nodes.add(accessoryProvider1);
            nodes.add(accessoryProvider2);
            nodes.add(carcaseProvider);

            nodes.add(detailsStock1);
            nodes.add(detailsStock2);
            nodes.add(detailsStock3);

            nodes.add(carConstructor);

            nodes.add(detailsStock4);
            nodes.add(consumer);
            nodes.add(controller);//*/
        mainView = new MainView();
        //mainView = new MainView(nodes, con);

        Thread t = new Thread(() -> launch());
        t.start();

/*
            try {
                Thread.sleep(10000);


                ls.add(carConstructor.start(detailsStock4, 1));

                controller.start();

                engineProvider.start(detailsStock1);
                accessoryProvider1.start(detailsStock2);
                accessoryProvider2.start(detailsStock2);
                carcaseProvider.start(detailsStock3);
                Thread.sleep(10000);

                ls.add(consumer.start());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
*/

    }
    /*
    public static void main2(String[] args) {


        EngineProvider engineProvider = new EngineProvider( DetailsEnum.ENGINE,10000);
        EngineProvider accessoryProvider = new EngineProvider(DetailsEnum.ACCESSORY,100);
        EngineProvider carcaseProvider = new EngineProvider(DetailsEnum.CARCASE,100);

        DetailsStock detailsStock1 = new DetailsStock("ПОСТАВЩИК ДВИГАТЕЛЕЙ: ",new Provider[]{engineProvider},10);
        DetailsStock detailsStock2 = new DetailsStock("ПОСТАВЩИК АКСЕССУАРОВ: ",new Provider[]{accessoryProvider},10);
        DetailsStock detailsStock3 = new DetailsStock("ПОСТАВЩИК КУЗОВОВ: ",new Provider[]{carcaseProvider},10);

        CarConstructor carConstructor =
                new CarConstructor(
                        new DetailsStock[] {detailsStock1,detailsStock2,detailsStock3},
                        new DetailsEnum[] {DetailsEnum.ENGINE,DetailsEnum.ACCESSORY,DetailsEnum.CARCASE},300,DetailsEnum.CAR);

        DetailsStock detailsStock4 = new DetailsStock("МАШИНЫ: ",new Provider[]{carConstructor},4);

        DetailConsumer consumer = new DetailConsumer(detailsStock4, 1000,1);

        DetailController controller = new DetailController(detailsStock4, 5);

        //Node node
        //consumer.getControlled()

        //mainView = new MainView(MainView.makeConnections());


        try {
            carConstructor.start(detailsStock4,2);
            controller.start();

            engineProvider.start(detailsStock1);
            accessoryProvider.start(detailsStock2);
            carcaseProvider.start(detailsStock3);
            Thread.sleep(5000);
            consumer.start();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
       // launch();
    }


    public static void main1(String[] args) {
        //EngineStock<Engine> detailsStock = new EngineStock<Engine>(10, new EngineProvider());

        EngineProvider engineProvider1 = new EngineProvider( DetailsEnum.ENGINE,568);
        EngineProvider engineProvider2 = new EngineProvider( DetailsEnum.ENGINE,379);

        EngineProvider accessoryProvider = new EngineProvider(DetailsEnum.ACCESSORY,100);
        EngineProvider carcaseProvider = new EngineProvider(DetailsEnum.CARCASE,100);

        DetailsStock detailsStock1 = new DetailsStock("ПОСТАВЩИК ДВИГАТЕЛЕЙ: ",new Provider[]{engineProvider1,engineProvider2},1000);
        DetailsStock detailsStock2 = new DetailsStock("ПОСТАВЩИК АКСЕССУАРОВ: ",new Provider[]{accessoryProvider},1000);
        DetailsStock detailsStock3 = new DetailsStock("ПОСТАВЩИК КУЗОВОВ: ",new Provider[]{carcaseProvider},1000);

        CarConstructor carConstructor =
                new CarConstructor(
                        new DetailsStock[] {detailsStock1,detailsStock2,detailsStock3},
                        new DetailsEnum[] {DetailsEnum.ENGINE,DetailsEnum.ACCESSORY,DetailsEnum.CARCASE},300,DetailsEnum.CAR);

        DetailsStock detailsStock4 = new DetailsStock("МАШИНЫ: ",new Provider[]{carConstructor},4);

        DetailConsumer consumer = new DetailConsumer(detailsStock4, 1000,1);

        DetailController controller = new DetailController(detailsStock4, 5);

       // DetailConsumer consumer2 = new DetailConsumer(detailsStock1, 1000,1);

        // DetailController controller2 = new DetailController(detailsStock1, 5);

        try {
            carConstructor.start(detailsStock4,2);

            engineProvider1.start(detailsStock1);
            //consumer2.start();
            //controller2.start();
            engineProvider2.start(detailsStock1);
            accessoryProvider.start(detailsStock2);
            carcaseProvider.start(detailsStock3);
            Thread.sleep(5000);
            consumer.start();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //launch();

    }
/*
    public static void createTimer(){
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println("НА СКЛАДАХ ПОСТАВКА");
                for(int a = 0; a < 12*3; a+=3){
                    detailsStock1.addDetail(new Detail(a, DetailsEnum.ENGINE));
                    detailsStock2.addDetail(new Detail(a+1, DetailsEnum.ACCESSORY));
                    detailsStock3.addDetail(new Detail(a+2, DetailsEnum.CARCASE));
                }
            }
        },7000,7000);
    }
*/

}