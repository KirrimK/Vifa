package com.enac.vifa.vifa.vues;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

public class RepereControllerPane extends VBox {

    public RepereControllerPane(Vue3D vue){
        //Box sliders repères
        Label rpl = new Label("Repères:");
        Label psil = new Label("Psi: 0");
        Slider psis = new Slider(-183, 183, 0);
        psis.valueProperty().addListener(((observableValue, number, t1) -> {
            vue.rotatePsi(number.intValue());
            psil.setText("Psi: "+number.intValue());
        }));
        Label phil = new Label("Phi: 0");
        Slider phis = new Slider(-183, 183, 0);
        phis.valueProperty().addListener(((observableValue, number, t1) -> {
            vue.rotatePhi(number.intValue());
            phil.setText("Phi: "+number.intValue());
        }));
        Label thetal = new Label("Theta: 0");
        Slider thetas = new Slider(-183, 183, 0);
        thetas.valueProperty().addListener(((observableValue, number, t1) -> {
            vue.rotateTheta(number.intValue());
            thetal.setText("Theta: "+number.intValue());
        }));

        Label alphal = new Label("Alpha: 0");
        Slider alphas = new Slider(-183, 183, 0);
        alphas.valueProperty().addListener(((observableValue, number, t1) -> {
            vue.rotateAlpha(number.intValue());
            alphal.setText("Alpha: "+number.intValue());
        }));
        Label betal = new Label("Beta: 0");
        Slider betas = new Slider(-183, 183, 0);
        betas.valueProperty().addListener(((observableValue, number, t1) -> {
            vue.rotateBeta(number.intValue());
            betal.setText("Beta: "+number.intValue());
        }));

        getChildren().addAll(rpl, psil, psis, phil, phis, thetal, thetas, alphal, alphas, betal, betas);
        setStyle("-fx-background-color: LIGHTGRAY; -fx-opacity:0.7;");
    }
}
