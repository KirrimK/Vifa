package com.enac.vifa.vifa.vues;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class CameraInfoPane extends VBox {

    private Vue3D vue;
    private Label xrotl;
    private Label yrotl;
    private Label zooml;

    public CameraInfoPane(Vue3D vue){
        super();
        //Box infos débug Caméra
        this.vue = vue;
        Label cml = new Label("Camera:");
        xrotl = new Label("X Rot: "+vue.getXrotprop());
        vue.xrotpropProperty().addListener(((observableValue, number, t1) -> {
            xrotl.setText("X Rot: "+number);
        }));
        yrotl = new Label("Y Rot: "+vue.getYrotprop());
        vue.yrotpropProperty().addListener(((observableValue, number, t1) -> {
            yrotl.setText("Y Rot: "+number);
        }));
        zooml = new Label("Zoom: "+vue.getZoomprop());
        vue.zoompropProperty().addListener(((observableValue, number, t1) -> {
            zooml.setText("Zoom: "+number);
        }));

        getChildren().addAll(cml, xrotl, yrotl, zooml);
        setStyle("-fx-background-color: LIGHTGRAY; -fx-opacity:0.7;");

        //Box boutons caméra
        Button topb = new Button("Top");
        topb.setPrefSize(50, 30);
        topb.setOnAction((actionEvent -> {
            vue.rotateCamera(-90, 0, vue.getZoomprop());
            xrotl.setText("X Rot: -90.0");
            yrotl.setText("Y Rot: 0.0");
        }));
        Button sideb = new Button("Side");
        sideb.setPrefSize(50, 30);
        sideb.setOnAction((actionEvent -> {
            vue.rotateCamera(0, 0, vue.getZoomprop());
            xrotl.setText("X Rot: 0.0");
            yrotl.setText("Y Rot: 0.0");
        }));
        Button backb = new Button("Back");
        backb.setPrefSize(50, 30);
        backb.setOnAction((actionEvent -> {
            vue.rotateCamera(0, 90, vue.getZoomprop());
            xrotl.setText("X Rot: 0.0");
            yrotl.setText("Y Rot: 90.0");
        }));
        Button troisquartb = new Button("3/4");
        troisquartb.setPrefSize(50, 30);
        troisquartb.setOnAction((actionEvent -> {
            vue.rotateCamera(-27, -57, vue.getZoomprop());
            xrotl.setText("X Rot: -27.0");
            yrotl.setText("Y Rot: -57.0");
        }));

        GridPane buttonp = new GridPane();
        buttonp.add(topb, 0, 0);
        buttonp.add(sideb, 0, 1);
        buttonp.add(backb, 1, 0);
        buttonp.add(troisquartb, 1, 1);

        getChildren().add(buttonp);
    }

}
