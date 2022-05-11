package com.enac.vifa.vifa.vues;

import com.enac.vifa.vifa.Modele;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

public class RepereControllerPane extends VBox {

    private Label psil;
    private Slider psis;

    private Label phil;
    private Slider phis;

    private Label thetal;
    private Slider thetas;

    private Label alphal;
    private Slider alphas;

    private Label betal;
    private Slider betas;

    private Label pl;
    private Slider ps;

    private Label ql;
    private Slider qs;

    private Label rl;
    private Slider rs;

    private Vue3D vue;

    public RepereControllerPane(Vue3D vue){
        //Box sliders repères
        this.vue = vue;
        Label rpl = new Label("Repères:");
        psil = new Label("Psi: 0");
        psis = new Slider(-183, 183, 0);
        psis.valueProperty().addListener(((observableValue, number, t1) -> {
            vue.rotatePsi(number.intValue());
            psil.setText("Psi: "+number.intValue());
            SimpleDoubleProperty psiprop = Modele.getInstance().getPsiProperty();
            synchronized (psiprop){ psiprop.set(Math.toRadians(number.doubleValue())); }
            //Modele.getInstance().getForcesMomentService.restart();
            //Modele.getInstance().descriptionService.restart();
        }));
        phil = new Label("Phi: 0");
        phis = new Slider(-183, 183, 0);
        phis.valueProperty().addListener(((observableValue, number, t1) -> {
            vue.rotatePhi(number.intValue());
            phil.setText("Phi: "+number.intValue());
            SimpleDoubleProperty phiprop = Modele.getInstance().getPhiProperty();
            synchronized (phiprop){ phiprop.set(Math.toRadians(number.doubleValue())); }
            //Modele.getInstance().getForcesMomentService.restart();
            //Modele.getInstance().descriptionService.restart();
        }));
        thetal = new Label("Theta: 0");
        thetas = new Slider(-183, 183, 0);
        thetas.valueProperty().addListener(((observableValue, number, t1) -> {
            vue.rotateTheta(number.intValue());
            thetal.setText("Theta: "+number.intValue());
            SimpleDoubleProperty thetaprop = Modele.getInstance().getThetaProperty();
            synchronized (thetaprop){ thetaprop.set(Math.toRadians(number.doubleValue())); }
            //Modele.getInstance().getForcesMomentService.restart();
            //Modele.getInstance().descriptionService.restart();
        }));

        alphal = new Label("Alpha: 0");
        alphas = new Slider(-183, 183, 0);
        alphas.valueProperty().addListener(((observableValue, number, t1) -> {
            vue.rotateAlpha(number.intValue());
            alphal.setText("Alpha: "+number.intValue());
            SimpleDoubleProperty alphaprop = Modele.getInstance().getAlphaProperty();
            synchronized (alphaprop){ alphaprop.set(Math.toRadians(number.doubleValue())); }
            Modele.getInstance().getForcesMomentService.restart();
            Modele.getInstance().descriptionService.restart();
        }));

        betal = new Label("Beta: 0");
        betas = new Slider(-183, 183, 0);
        betas.valueProperty().addListener(((observableValue, number, t1) -> {
            vue.rotateBeta(number.intValue());
            betal.setText("Beta: "+number.intValue());
            SimpleDoubleProperty betaprop = Modele.getInstance().getBetaProperty();
            synchronized (betaprop){ betaprop.set(Math.toRadians(number.doubleValue())); }
            Modele.getInstance().getForcesMomentService.restart();
            Modele.getInstance().descriptionService.restart();
        }));

        pl = new Label("P: 0");
        ps = new Slider(-Math.PI, Math.PI, 0);
        ps.valueProperty().addListener(((observableValue, number, t1) -> {
            Modele.getInstance().setP(number.doubleValue());
            pl.setText("P: "+number.doubleValue());
            SimpleDoubleProperty pprop = Modele.getInstance().getPProperty();
            synchronized (pprop){ pprop.set(Math.toRadians(number.doubleValue())); }
            Modele.getInstance().getForcesMomentService.restart();
            Modele.getInstance().descriptionService.restart();
        }));
        ql = new Label("Q: 0");
        qs = new Slider(-Math.PI, Math.PI, 0);
        qs.valueProperty().addListener(((observableValue, number, t1) -> {
            Modele.getInstance().setQ(number.doubleValue());
            ql.setText("Q: "+number.doubleValue());
            SimpleDoubleProperty qprop = Modele.getInstance().getQProperty();
            synchronized (qprop){ qprop.set(Math.toRadians(number.doubleValue())); }
            Modele.getInstance().getForcesMomentService.restart();
            Modele.getInstance().descriptionService.restart();
        }));
        rl = new Label("R: 0");
        rs = new Slider(-Math.PI, Math.PI, 0);
        rs.valueProperty().addListener(((observableValue, number, t1) -> {
            Modele.getInstance().setR(number.doubleValue());
            rl.setText("R: "+number.doubleValue());
            SimpleDoubleProperty rprop = Modele.getInstance().getRProperty();
            synchronized (rprop){ rprop.set(Math.toRadians(number.doubleValue())); }
            Modele.getInstance().getForcesMomentService.restart();
            Modele.getInstance().descriptionService.restart();
        }));

        getChildren().addAll(rpl, psil, psis, phil, phis, thetal, thetas, alphal, alphas, betal, betas, pl, ps, ql, qs, rl, rs);
        setStyle("-fx-background-color: LIGHTGRAY; -fx-opacity:0.7;");
    }

    public void reset(){
        phis.setValue(0);
        psis.setValue(0);
        thetas.setValue(0);
        alphas.setValue(0);
        betas.setValue(0);
        betas.adjustValue(0);
        ps.setValue(0);
        qs.setValue(0);
        rs.setValue(0);

        vue.rotatePsi(0);
        vue.rotatePhi(0);
        vue.rotateTheta(0);
        vue.rotateAlpha(0);
        vue.rotateBeta(0);
        Modele.getInstance().setP(0);
        Modele.getInstance().setQ(0);
        Modele.getInstance().setR(0);

        phil.setText("Phi: 0");
        psil.setText("Psi: 0");
        thetal.setText("Theta: 0");
        alphal.setText("Alpha: 0");
        betal.setText("Beta: 0");
        pl.setText("P: 0");
        ql.setText("Q: 0");
        rl.setText("R: 0");

        Modele.getInstance().getForcesMomentService.restart();
        Modele.getInstance().descriptionService.restart();
    }
}
