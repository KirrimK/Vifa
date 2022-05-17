package com.enac.vifa.vifa.vues;

import com.enac.vifa.vifa.Modele;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.util.converter.NumberStringConverter;

public class RepereControllerPane extends ControllerPane{

    private Label psil;
    private Slider psis;
    private TextField psils;

    private Label phil;
    private Slider phis;
    private TextField phils;

    private Label thetal;
    private Slider thetas;
    private TextField thetals;
    
    private Label alphal;
    private Slider alphas;
    private TextField alphals;

    private Label betal;
    private Slider betas;
    private TextField betals;
    
    public RepereControllerPane(Vue3D vue){
        //Box sliders repères
        super(vue);
        Label rpl = new Label("Repères:");

        setRowIndex(rpl, 0);
        psil = new Label("Psi (deg):");
        setRowIndex(psil, 1);
        psils = new TextField("0.0");
        setColumnIndex(psils, 1);
        setRowIndex(psils, 2);
        psis = new Slider(-180, 180, 0);
        setRowIndex(psis, 2);
        psis.valueProperty().bindBidirectional(Modele.getInstance().getPsiProperty());
        psis.valueProperty().addListener(((observableValue, number, t1) -> {
            vue.rotatePsi(t1.doubleValue());
            genericSliderListener("Psi (deg): ", psil, t1);
        }));
        //Binding bidirectionnel slider-Textfield
        NumberStringConverter sPsi = new NumberStringConverter();
        Bindings.bindBidirectional(psils.textProperty(), psis.valueProperty(), sPsi);

        phil = new Label("Phi (deg):");
        setRowIndex(phil, 3);
        phils = new TextField("0.0");
        setColumnIndex(phils, 1);
        setRowIndex(phils,4);
        phis = new Slider(-180, 180, 0);
        setRowIndex(phis, 4);
        phis.valueProperty().bindBidirectional(Modele.getInstance().getPhiProperty());
        phis.valueProperty().addListener(((observableValue, number, t1) -> {
            vue.rotatePhi(t1.doubleValue());
            genericSliderListener("Phi (deg): ", phil, t1);
        }));
        //Binding bidirectionnel slider-Textfield
        NumberStringConverter sPhi = new NumberStringConverter();
        Bindings.bindBidirectional(phils.textProperty(), phis.valueProperty(), sPhi);

        thetal = new Label("Theta (deg):");
        setRowIndex(thetal, 5);
        thetals = new TextField("0.0");
        setColumnIndex(thetals, 1);
        setRowIndex(thetals,6);
        thetas = new Slider(-180, 180, 0);
        setRowIndex(thetas, 6);
        thetas.valueProperty().bindBidirectional(Modele.getInstance().getThetaProperty());
        thetas.valueProperty().addListener(((observableValue, number, t1) -> {
            vue.rotateTheta(t1.doubleValue());
            genericSliderListener("Theta (deg): ", thetal, t1);
        }));
        //Binding bidirectionnel slider-Textfield
        NumberStringConverter sTheta = new NumberStringConverter();
        Bindings.bindBidirectional(thetals.textProperty(),thetas.valueProperty(), sTheta);

        alphal = new Label("Alpha (deg):");
        setRowIndex(alphal, 7);
        alphals = new TextField("0.0");
        setColumnIndex(alphals, 1);
        setRowIndex(alphals,8);
        alphas = new Slider(-180, 180, 0);
        setRowIndex(alphas, 8);
        alphas.valueProperty().bindBidirectional(Modele.getInstance().getAlphaProperty());
        alphas.valueProperty().addListener(((observableValue, number, t1) -> {
            vue.rotateAlpha(t1.doubleValue());
            genericSliderListener("Alpha (deg): ", alphal, t1);
            if (!resetting) Modele.getInstance().getForcesMomentService.restart();
        }));
        //Binding bidirectionnel slider-Textfield
        NumberStringConverter sAlpha = new NumberStringConverter();
        Bindings.bindBidirectional(alphals.textProperty(), alphas.valueProperty(), sAlpha);

        betal = new Label("Beta (deg):");
        setRowIndex(betal, 9);
        betals = new TextField("0.0");
        setColumnIndex(betals, 1);
        setRowIndex(betals,10);
        betas = new Slider(-180, 180, 0);
        setRowIndex(betas, 10);
        betas.valueProperty().bindBidirectional(Modele.getInstance().getBetaProperty());
        betas.valueProperty().addListener(((observableValue, number, t1) -> {
            vue.rotateBeta(t1.doubleValue());
            genericSliderListener("Beta (deg): ", betal, t1);
            if (!resetting) Modele.getInstance().getForcesMomentService.restart();
        }));
        //Binding bidirectionnel slider-Textfield
        NumberStringConverter sBeta = new NumberStringConverter();
        Bindings.bindBidirectional(betals.textProperty(), betas.valueProperty(), sBeta);

        getChildren().addAll(rpl, psil, psis,psils, phil, phis,phils, thetal, thetas,thetals, alphal, alphas,alphals, betal, betas,betals);
        setStyle("-fx-background-color: LIGHTGRAY; -fx-opacity:0.7;");
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
