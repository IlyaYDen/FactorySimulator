package com.example.factorysimulation.view;

import com.example.factorysimulation.models.details.Detail;
import com.example.factorysimulation.models.nodes.*;
import com.example.factorysimulation.models.nodes.constructor.Constructor;
import javafx.event.ActionEvent;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainView extends VBox {
    private Canvas canvas;
    int Screen = 800; //LifeGame.SIZE*SIZEX;
    List<Connections> con = new ArrayList<>();
    List<ModelNode> modelNodes = new ArrayList<>();
    int nodeSize = 26;

    FlowPane menu = new FlowPane(Orientation.HORIZONTAL);
    FlowPane down = new FlowPane(Orientation.HORIZONTAL);
    Button start;
    Button stop;
    public MainView(List<ModelNode> modelNodes, List<Connections> con) {
        this();
        this.con = con;
        this.modelNodes = modelNodes;

        int b=1;
        for(int a = 0; a < modelNodes.size(); a++){
            modelNodes.get(a).setXY(b*12,40+(b*32*(2%b)));
            b++;
        }
    }

    Button b = new Button("save");
    TextField tf = new TextField();

    public MainView() {

        this.canvas = new Canvas(Screen,Screen+24);

        start = new Button("Start");
        stop = new Button("Stop");

        down.getChildren().addAll(this.start , this.stop);
        addNodes(down);
        down.getChildren().addAll(menu);


        this.getChildren().addAll(canvas,down);

        
        start.setOnAction(this::startSim);
        stop.setOnAction(this::stopSim);

        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                draw();
            }
        },100,100);
        setOnMouseDragged(this::nodeDrag);
        setOnMouseClicked(this::nodeClick);

    }

    private void stopSim(ActionEvent actionEvent) {
        for(Timer t : timers)
            t.cancel();
        for(ExecutorService t : executorService)
            t.shutdownNow();
    }


    List<Timer> timers = new ArrayList<>();
    List<ExecutorService> executorService = new ArrayList<>();
    private void startSim(ActionEvent actionEvent) {

        for(ModelNode n: modelNodes)
            if(n.getWorkers()<=1)
                timers.add(CreateChadulerForNode(n));
            else
                timers.add(CreateChadulerPoolForNode(n));
    }

    private Timer CreateChadulerPoolForNode(ModelNode n) {
        int w = n.getWorkers();
        ExecutorService tp = Executors.newScheduledThreadPool(w);
        executorService.add(tp);

        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                for(int a = 0 ; a < w ; a ++)
                    tp.execute(n::run);
            }
        },100,100);
        return t;
    }

    private Timer CreateChadulerForNode(ModelNode n) {
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                n.run();
            }
        },100,100);
        return t;
    }


    boolean drag = false;
    boolean doubleClickL = false;
    boolean doubleClickR = false;
    List<ModelNode> selectedModes = new ArrayList<>();
    private void nodeClick(MouseEvent mouseEvent) {
        if(!drag){
            double x = mouseEvent.getX();
            double y = mouseEvent.getY();
            if(mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                boolean nodeclicked = false;
                for (ModelNode n : modelNodes) {
                    if ((n.getX() <= x && n.getX() + nodeSize >= x) && (n.getY() <= y && n.getY() + nodeSize >= y)) {
                        nodeclicked = true;
                        if(selectedModes.contains(n)) {
                            selectedModes.remove(n);
                        }
                        else {
                            selectedModes.add(n);
                        }


                        if(selectedModes.size()==1){
                            System.out.println("test " + selectedModes.get(0).getNodeName());
                            menu.getChildren().addAll(NodeMenuFactory.create(selectedModes.get(0)));
                        }
                        else{
                            menu.getChildren().clear();
                        }
                    }
                }
                if(!nodeclicked){
                    System.out.println("test");
                    Thread t = new Thread(() -> {
                        try {
                            Thread.sleep(300);
                            System.out.println("test3");
                            doubleClickL = false;
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    t.start();
                    System.out.println("test2");
                    if(doubleClickL){
                         selectedModes.clear();
                        System.out.println("test1");
                        menu.getChildren().clear();
                    }
                    doubleClickL = true;
                }
            } else if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {

                ModelNode mn = null;


                for (ModelNode n : modelNodes) {
                        if ((n.getX() <= x && n.getX() + nodeSize >= x) && (n.getY() <= y && n.getY() + nodeSize >= y) && !selectedModes.contains(n)) {
                            mn = n;
                        }
                }

                if(mn!=null){
                    for (ModelNode n : selectedModes) {
                        if(selectedModes.contains(n)) {
                            Connections f = new Connections(n,mn);
                            if(!con.contains(f)) {
                                System.out.println("test1 ");
                                con.add(new Connections(n, mn));
                                System.out.println("test2 " + mn.getNodeName());
                                mn.setInputConnections(n);
                                n.setOutputConnections(mn);
                                System.out.println("test3 ");
                            }

                        }
                    }
                }

                    Thread t = new Thread(() -> {
                        try {
                            Thread.sleep(300);
                            System.out.println("test3");
                            doubleClickR = false;
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    t.start();
                    System.out.println("test2");
                    Connections cf = null;
                    if(doubleClickR){
                        for(Connections c : con)
                            if(c.contains(mn))
                                {
                                    c.getfirst().setInputConnections(null);
                                    c.getfirst().setOutputConnections(null);
                                    c.getSecond().setInputConnections(null);
                                    c.getSecond().setOutputConnections(null);
                                    cf=c;
                                }

                        //mn.setOutputConnections(null);
                        //mn.setInputConnections(null);
                    }
                    if(cf!=null)
                        con.remove(cf);
                    doubleClickR = true;

            }
        }
        drag = false;
        dragged = null;
        //System.out.println(drag + "  " + dragged);
    }

    double xdelt = 0;
    double ydelt = 0;
    ModelNode dragged = null;
    private void nodeDrag(MouseEvent mouseEvent) {
        double x = mouseEvent.getX();
        double y = mouseEvent.getY();
        if(dragged == null && !drag)
            for(ModelNode n : modelNodes){
                if( (n.getX()<=x && n.getX()+nodeSize>=x) && (n.getY()<=y && n.getY()+nodeSize>=y)){
                        drag=true;
                        xdelt = x-n.getX();
                        ydelt = y-n.getY();
                        dragged = n;
                    }
            }
        if(drag) {

            //System.out.println(x + " " + y);
            dragged.setXY(
                    (x - nodeSize / 2.), (y - nodeSize / 2.)
            );
            draw();
        }
    }

    private void draw() {
        GraphicsContext g = this.canvas.getGraphicsContext2D();

        g.clearRect(0, 0, Screen, Screen);

        g.setFill(Color.LIGHTBLUE);
        g.fillRect(0, 0, 2 * Screen, Screen * 2);


        if(modelNodes.size()>0) {
            g.setFill(Color.BLACK);
            for (int a = 0; a < modelNodes.size(); a++) {
                nodeDrawer(g, modelNodes.get(a));
            }
        }

        if(con.size()>0) {
            g.setFill(Color.BLACK);
            for (Connections c : con) {
                g.strokeLine(c.getfirst().getX() + (nodeSize / 2.), c.getfirst().getY() + (nodeSize / 2.), c.getSecond().getX() + (nodeSize / 2.), c.getSecond().getY() + (nodeSize / 2.));
            }
        }
    }

    private void nodeDrawer(GraphicsContext g, ModelNode modelNode) {
        double nodeX = modelNode.getX();
        double nodeY = modelNode.getY();

        if(selectedModes.contains(modelNode)){

            g.setFill(Color.RED);
            g.fillRect(nodeX-2, nodeY-2, nodeSize+4, nodeSize+4);
            g.setFill(Color.LIGHTBLUE);
            g.fillRect(nodeX-1, nodeY-1, nodeSize+2, nodeSize+2);
        }

        g.setFill(modelNode.getNodeName().getColor());
        g.fillRect(nodeX, nodeY, nodeSize, nodeSize);
        int textl = (int) (nodeY+nodeSize+11);

        g.setFont(new Font(12));
        g.fillText(modelNode.getNodeName().name(), nodeX, textl);
        textl+=12;

        if(modelNode.getDetails()!=null) {
            g.fillText("Количество деталей: " + modelNode.getDetails().size(), nodeX, textl);
            textl+=12;
            for(Detail d : modelNode.getDetails()){
                g.fillText("Деталь: " + d.getType().name() + " ID: " + d.getId(), nodeX, textl);
                textl+=12;
            }
        }
        //switch ()
    }




    private void addNodes(FlowPane fp) {
        List<Node> nodes1 = new ArrayList<>();
        int x = 1;
        for(NodeEnum e : NodeEnum.values()) {
            System.out.println(e.name());
            Canvas c = new Canvas(20,20);
            GraphicsContext g = c.getGraphicsContext2D();
            g.setFill(e.getColor());
            g.fillRect(x, 1, nodeSize, nodeSize);
            int textl = (nodeSize+12);

            g.setFont(new Font(12));
            g.fillText(e.name(), x, textl);
            textl+=12;
            ModelNodeFactory factory = new ModelNodeFactory();

            c.setOnMouseClicked(mouseEvent -> {
                ModelNode mn = factory.create(e);
                mn.setXY(300,300);
                modelNodes.add(mn);
                System.out.println("test " + mn.getNodeName().name());
            });

            nodes1.add(c);

        }
        fp.getChildren().addAll(nodes1);
    }

}
