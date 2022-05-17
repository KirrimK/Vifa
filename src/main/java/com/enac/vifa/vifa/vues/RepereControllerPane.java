package com.enac.vifa.vifa.vues;

import com.enac.vifa.vifa.Modele;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Priority;

public class RepereControllerPane extends ControllerPane{

    private final Label psil;
    private final Slider psis;

    private final Label phil;
    private final Slider phis;

    private final Label thetal;
    private final Slider thetas;

    private final Label alphal;
    private final Slider alphas;

    private final Label betal;
    private final Slider betas;

    public RepereControllerPane(Vue3D vue){
        //Box sliders repères
        super(vue);
        Label rpl = new Label("Repères:");

        setRowIndex(rpl, 0);
        psil = new Label("Psi (deg): 0.0");
        setRowIndex(psil, 1);
        psis = new Slider(-180, 180, 0);
        setRowIndex(psis, 2);
        psis.valueProperty().bindBidirectional(Modele.getInstance().getPsiProperty());
        psis.valueProperty().addListener(((observableValue, number, t1) -> {
            vue.rotatePsi(t1.doubleValue());
            genericSliderListener("Psi (deg): ", psil, t1);
        }));

        phil = new Label("Phi (deg): 0.0");
        setRowIndex(phil, 3);
        phis = new Slider(-180, 180, 0);
        setRowIndex(phis, 4);
        phis.valueProperty().bindBidirectional(Modele.getInstance().getPhiProperty());
        phis.valueProperty().addListener(((observableValue, number, t1) -> {
            vue.rotatePhi(t1.doubleValue());
            genericSliderListener("Phi (deg): ", phil, t1);
        }));

        thetal = new Label("Theta (deg): 0.0");
        setRowIndex(thetal, 5);
        thetas = new Slider(-180, 180, 0);
        setRowIndex(thetas, 6);
        thetas.valueProperty().bindBidirectional(Modele.getInstance().getThetaProperty());
        thetas.valueProperty().addListener(((observableValue, number, t1) -> {
            vue.rotateTheta(t1.doubleValue());
            genericSliderListener("Theta (deg): ", thetal, t1);
        }));

        alphal = new Label("Alpha (deg): 0.0");
        setRowIndex(alphal, 7);
        alphas = new Slider(-180, 180, 0);
        setRowIndex(alphas, 8);
        alphas.valueProperty().bindBidirectional(Modele.getInstance().getAlphaProperty());
        alphas.valueProperty().addListener(((observableValue, number, t1) -> {
            vue.rotateAlpha(t1.doubleValue());
            genericSliderListener("Alpha (deg): ", alphal, t1);
            if (!resetting) Modele.getInstance().getForcesMomentService.restart();
        }));

        betal = new Label("Beta (deg): 0.0");
        setRowIndex(betal, 9);
        betas = new Slider(-180, 180, 0);
        setRowIndex(betas, 10);
        betas.valueProperty().bindBidirectional(Modele.getInstance().getBetaProperty());
        betas.valueProperty().addListener(((observableValue, number, t1) -> {
            vue.rotateBeta(t1.doubleValue());
            genericSliderListener("Beta (deg): ", betal, t1);
            if (!resetting) Modele.getInstance().getForcesMomentService.restart();
        }));

        getChildren().addAll(rpl, psil, psis, phil, phis, thetal, thetas, alphal, alphas, betal, betas);
        setStyle("-fx-background-color: LIGHTGRAY; -fx-opacity:0.7;");
        for (Node node: getChildren()){
            setHgrow(node, Priority.ALWAYS);
            setVgrow(node, Priority.ALWAYS);
        }
    }

    public void reset(){
        resetting = true;
        Modele.getInstance().setPhi(0);
        Modele.getInstance().setPsi(0);
        Modele.getInstance().setTheta(0);
        Modele.getInstance().setAlpha(0);
        Modele.getInstance().setBeta(0);
        resetting = false;
    }
}
