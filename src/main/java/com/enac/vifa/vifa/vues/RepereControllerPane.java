package com.enac.vifa.vifa.vues;

import com.enac.vifa.vifa.Modele;
import javafx.scene.Node;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Priority;
import javafx.scene.control.TextField;
import javafx.util.converter.NumberStringConverter;

public class RepereControllerPane extends ControllerPane{

    private final Label psil;
    private final Slider psis;
    private TextField psils;

    private TextField phils;
    private final Label phil;
    private final Slider phis;

    private TextField thetals;

    private TextField alphals;
    private final Label thetal;
    private final Slider thetas;

    private final Label alphal;
    private final Slider alphas;

    private TextField betals;

    private final Label betal;
    private final Slider betas;

    public RepereControllerPane(Vue3D vue){
        //Box sliders repères
        super(vue);
        Label rpl = new Label("Repères:");

        setRowIndex(rpl, 0);
        psil = new Label("Psi (deg):");
        setRowIndex(psil, 1);
        psils = new TextField("0.0");
        psils.setMaxSize(50, 50);
        setColumnIndex(psils, 1);
        setRowIndex(psils, 1);
        setRowSpan(psils, 2);
        psis = new Slider(-180, 180, 0);
        setRowIndex(psis, 2);
        psis.valueProperty().bindBidirectional(Modele.getInstance().getPsiProperty());
        psis.valueProperty().addListener(((observableValue, number, t1) -> {
            vue.rotatePsi(t1.doubleValue());
            //genericSliderListener("Psi (deg): ", psil, t1);
        }));
        //Binding bidirectionnel slider-Textfield
        NumberStringConverter sPsi = new NumberStringConverter();
        Bindings.bindBidirectional(psils.textProperty(), psis.valueProperty(), sPsi);

        phil = new Label("Phi (deg):");
        setRowIndex(phil, 3);
        phils = new TextField("0.0");
        phils.setMaxSize(50, 50);
        setColumnIndex(phils, 1);
        setRowIndex(phils,3);
        setRowSpan(phils, 2);
        phis = new Slider(-180, 180, 0);
        setRowIndex(phis, 4);
        phis.valueProperty().bindBidirectional(Modele.getInstance().getPhiProperty());
        phis.valueProperty().addListener(((observableValue, number, t1) -> {
            vue.rotatePhi(t1.doubleValue());
            //genericSliderListener("Phi (deg): ", phil, t1);
        }));
        //Binding bidirectionnel slider-Textfield
        NumberStringConverter sPhi = new NumberStringConverter();
        Bindings.bindBidirectional(phils.textProperty(), phis.valueProperty(), sPhi);

        thetal = new Label("Theta (deg):");
        setRowIndex(thetal, 5);
        thetals = new TextField("0.0");
        thetals.setMaxSize(50, 50);
        setColumnIndex(thetals, 1);
        setRowIndex(thetals,5);
        setRowSpan(thetals, 2);
        thetas = new Slider(-180, 180, 0);
        setRowIndex(thetas, 6);
        thetas.valueProperty().bindBidirectional(Modele.getInstance().getThetaProperty());
        thetas.valueProperty().addListener(((observableValue, number, t1) -> {
            vue.rotateTheta(t1.doubleValue());
            //genericSliderListener("Theta (deg): ", thetal, t1);
        }));
        //Binding bidirectionnel slider-Textfield
        NumberStringConverter sTheta = new NumberStringConverter();
        Bindings.bindBidirectional(thetals.textProperty(),thetas.valueProperty(), sTheta);

        alphal = new Label("Alpha (deg):");
        setRowIndex(alphal, 7);
        alphals = new TextField("0.0");
        alphals.setMaxSize(50, 50);
        setColumnIndex(alphals, 1);
        setRowIndex(alphals,7);
        setRowSpan(alphals, 2);
        alphas = new Slider(-180, 180, 0);
        setRowIndex(alphas, 8);
        alphas.valueProperty().bindBidirectional(Modele.getInstance().getAlphaProperty());
        alphas.valueProperty().addListener(((observableValue, number, t1) -> {
            vue.rotateAlpha(t1.doubleValue());
            //genericSliderListener("Alpha (deg): ", alphal, t1);
            if (!resetting) Modele.getInstance().getForcesMomentService.restart();
        }));
        //Binding bidirectionnel slider-Textfield
        NumberStringConverter sAlpha = new NumberStringConverter();
        Bindings.bindBidirectional(alphals.textProperty(), alphas.valueProperty(), sAlpha);

        betal = new Label("Beta (deg):");
        setRowIndex(betal, 9);
        betals = new TextField("0.0");
        betals.setMaxSize(50, 50);
        setColumnIndex(betals, 1);
        setRowIndex(betals,9);
        setRowSpan(betals ,2);
        betas = new Slider(-180, 180, 0);
        setRowIndex(betas, 10);
        betas.valueProperty().bindBidirectional(Modele.getInstance().getBetaProperty());
        betas.valueProperty().addListener(((observableValue, number, t1) -> {
            vue.rotateBeta(t1.doubleValue());
            //genericSliderListener("Beta (deg): ", betal, t1);
            if (!resetting) Modele.getInstance().getForcesMomentService.restart();
        }));
        //Binding bidirectionnel slider-Textfield
        NumberStringConverter sBeta = new NumberStringConverter();
        Bindings.bindBidirectional(betals.textProperty(), betas.valueProperty(), sBeta);

        getChildren().addAll(rpl, psil, psis,psils, phil, phis,phils, thetal, thetas,thetals, alphal, alphas,alphals, betal, betas,betals);
        setStyle("-fx-background-color: LIGHTGRAY; -fx-opacity:0.7; -fx-background-radius: 5px;");
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
