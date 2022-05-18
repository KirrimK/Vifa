package com.enac.vifa.vifa.vues;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class CameraInfoPane extends VBox {

    private final Label xrotl;
    private final Label yrotl;
    private final Label zooml;

    public CameraInfoPane(Vue3D vue){
        super();
        //Box infos débug Caméra
        Label cml = new Label("Camera:");
        xrotl = new Label("X Rot (deg): "+vue.getXrotprop());
        vue.xrotpropProperty().addListener(((observableValue, number, t1) -> xrotl.setText("X Rot (deg): "+t1)));
        yrotl = new Label("Y Rot (deg): "+vue.getYrotprop());
        vue.yrotpropProperty().addListener(((observableValue, number, t1) -> yrotl.setText("Y Rot (deg): "+t1)));
        zooml = new Label("Zoom: "+vue.getZoomprop());
        vue.zoompropProperty().addListener(((observableValue, number, t1) -> zooml.setText("Zoom: "+t1)));

        getChildren().addAll(cml, xrotl, yrotl, zooml);
        setStyle("-fx-background-color: LIGHTGRAY; -fx-opacity:0.7; -fx-background-radius: 5px;");

        //Box boutons caméra
        Button topb = new Button("Top");
        topb.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        topb.setOnAction((actionEvent -> {
            vue.rotateCamera(-90, 0, vue.getZoomprop());
            xrotl.setText("X Rot (deg): -90.0");
            yrotl.setText("Y Rot (deg): 0.0");
        }));
        Button sideb = new Button("Side");
        sideb.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        sideb.setOnAction((actionEvent -> {
            vue.rotateCamera(0, 0, vue.getZoomprop());
            xrotl.setText("X Rot (deg): 0.0");
            yrotl.setText("Y Rot (deg): 0.0");
        }));
        Button backb = new Button("Back");
        backb.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        backb.setOnAction((actionEvent -> {
            vue.rotateCamera(0, 90, vue.getZoomprop());
            xrotl.setText("X Rot (deg): 0.0");
            yrotl.setText("Y Rot (deg): 90.0");
        }));
        Button troisquartb = new Button("3/4");
        troisquartb.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        troisquartb.setOnAction((actionEvent -> {
            vue.rotateCamera(-27, -57, vue.getZoomprop());
            xrotl.setText("X Rot (deg): -27.0");
            yrotl.setText("Y Rot (deg): -57.0");
        }));

        GridPane buttonp = new GridPane();
        buttonp.add(topb, 0, 0);
        buttonp.add(sideb, 0, 1);
        buttonp.add(backb, 1, 0);
        buttonp.add(troisquartb, 1, 1);

        for (Node node: buttonp.getChildren()){
            GridPane.setVgrow(node, Priority.ALWAYS);
            GridPane.setHgrow(node, Priority.ALWAYS);
        }

        getChildren().add(buttonp);
        setSpacing(5);
    }

}
