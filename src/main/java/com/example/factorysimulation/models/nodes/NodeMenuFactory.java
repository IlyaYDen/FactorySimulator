package com.example.factorysimulation.models.nodes;

import com.example.factorysimulation.models.details.DetailsEnum;
import com.example.factorysimulation.models.nodes.constructor.Constructor;
import com.example.factorysimulation.models.nodes.consumer.Consumer;
import com.example.factorysimulation.models.nodes.controller.Controller;
import com.example.factorysimulation.models.nodes.providers.Provider;
import com.example.factorysimulation.models.nodes.stock.Stock;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NodeMenuFactory {
    public static List<Node> create(ModelNode modelNode) {
        List<Node> l = new ArrayList<>();
        Button save = new Button("save");
        switch (modelNode.getNodeName()) {
            case Provider -> {
                TextField tf = new TextField();
                tf.setPromptText("enter Speed");

                Provider p = (Provider) modelNode;

                if(p.hasSpeed())
                    tf.setText(String.valueOf(p.getSpeed()));

                List<DetailsEnum> s = new ArrayList<>();
                Arrays.stream(DetailsEnum.values()).forEach(detailsEnum -> s.add(detailsEnum));
                ObservableList<DetailsEnum> langs = FXCollections.observableArrayList(s);
                ComboBox<DetailsEnum> cb = new ComboBox<>(langs);
                cb.setValue(DetailsEnum.ENGINE);

                save.setOnAction(actionEvent -> {
                    p.setDetails(cb.getValue());
                    if(!tf.getText().equals("") && tf.getText().matches("[-+]?\\d+"))
                        p.setSpeed(Integer.parseInt(tf.getText()));
                });


                l.add(tf);
                l.add(cb);
                l.add(save);
            }
            case Consumer -> {

                TextField tf = new TextField();
                tf.setPromptText("enter Speed");

                Consumer p = (Consumer) modelNode;

                if(p.hasSpeed())
                    tf.setText(String.valueOf(p.getSpeed()));

                save.setOnAction(actionEvent -> {
                    if(!tf.getText().equals("") && tf.getText().matches("[-+]?\\d+"))
                        p.setSpeed(Integer.parseInt(tf.getText()));
                });


                l.add(tf);
                l.add(save);
            }
            case Constructor -> {

                Constructor p = (Constructor) modelNode;



                List<DetailsEnum> s = new ArrayList<>();
                List<ComboBox<DetailsEnum>> cbl = new ArrayList<>();

                FlowPane input = new FlowPane(Orientation.HORIZONTAL);


                Arrays.stream(DetailsEnum.values()).forEach(detailsEnum -> s.add(detailsEnum));
                ObservableList<DetailsEnum> langs = FXCollections.observableArrayList(s);
                if(p.hasDetailsEnumInput()) {
                    List<DetailsEnum> dl = p.getDetailsEnumInput();
                    for(DetailsEnum de : dl){
                        ComboBox<DetailsEnum> cb1 = new ComboBox<>(langs);
                        cb1.setValue(de);
                        //l.add(cb1);
                        cbl.add(cb1);
                    }

                    input.getChildren().addAll(cbl);
                }
                else{
                    ComboBox<DetailsEnum> cb = new ComboBox<>(langs);
                    cb.setValue(DetailsEnum.ENGINE);
                    //l.add(cb);
                    cbl.add(cb);

                    input.getChildren().add(cb);

                }
                l.add(input);

               // cb1.setValue(DetailsEnum.ENGINE);
//
               // ComboBox<DetailsEnum> cb2 = new ComboBox<>(langs);
               // cb2.setValue(DetailsEnum.ENGINE);
//
               // ComboBox<DetailsEnum> cb3 = new ComboBox<>(langs);
               // cb3.setValue(DetailsEnum.ENGINE);

                ComboBox<DetailsEnum> cb4 = new ComboBox<>(langs);
                if(p.hasOutDetailsEnum())
                    cb4.setValue(p.getOutDetailsEnum());
                else
                    cb4.setValue(DetailsEnum.CAR);

                Text text = new Text("Output");


                TextField tf = new TextField();
                tf.setPromptText("enter Speed");


                List<DetailsEnum> inputde = new ArrayList<>();
                save.setOnAction(actionEvent -> {
                    inputde.clear();
                    for(ComboBox<DetailsEnum> b : cbl)
                        inputde.add(b.getValue());

                    p.setDetails(inputde);

                    p.setDetailOut(cb4.getValue());

                    if(!tf.getText().equals("") && tf.getText().matches("[-+]?\\d+"))
                        p.setSpeed(Integer.parseInt(tf.getText()));
                });

                Button add = new Button("ADD");
                add.setOnAction(actionEvent -> {
                    ComboBox<DetailsEnum> cb = new ComboBox<>(langs);
                    cb.setValue(DetailsEnum.ENGINE);
                    input.getChildren().add(cb);
                    cbl.add(cb);
                });
                Button remove = new Button("RM");
                remove.setOnAction(actionEvent -> {
                    ComboBox<DetailsEnum> cb = cbl.get(cbl.size()-1);
                    input.getChildren().remove(cb);
                    cbl.remove(cbl.size()-1);

                });

                //l.addAll(cbl);
                l.add(add);
                l.add(remove);
                l.add(text);
                l.add(cb4);
                l.add(tf);
                l.add(save);

            }
            case Controller -> {

                TextField tf = new TextField();
                tf.setPromptText("less then");


                Controller p = (Controller) modelNode;
                if(p.hasLimit())
                    tf.setText(String.valueOf(p.getLimit()));
                save.setOnAction(actionEvent -> {
                    if(!tf.getText().equals("") && tf.getText().matches("[-+]?\\d+"))
                        p.setMin(Integer.parseInt(tf.getText()));
                });


                l.add(tf);
                l.add(save);
            }
            case Stock -> {

                TextField tf = new TextField();
                tf.setPromptText("size");

                Stock p = (Stock) modelNode;

                if(p.hasLimit())
                    tf.setText(String.valueOf(p.getLimit()));

                save.setOnAction(actionEvent -> {
                    if(!tf.getText().equals("") && tf.getText().matches("[-+]?\\d+"))
                        p.setSize(Integer.parseInt(tf.getText()));
                });


                l.add(tf);
                l.add(save);
            }
        }
        return l;
    }
}
