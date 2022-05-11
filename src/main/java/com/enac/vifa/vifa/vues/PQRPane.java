package com.enac.vifa.vifa.vues;

import com.enac.vifa.vifa.Modele;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

public class PQRPane extends VBox implements Resettable {

    private Label pl;
    private Slider ps;

    private Label ql;
    private Slider qs;

    private Label rl;
    private Slider rs;

    private Vue3D vue;

    public PQRPane(Vue3D vue){
        this.vue = vue;
        Label rpl = new Label("Vitesses de rotation:");
        pl = new Label("P: 0");
        ps = new Slider(-Math.PI, Math.PI, 0);
        ps.valueProperty().addListener(((observableValue, number, t1) -> {
            Modele.getInstance().setP(number.doubleValue());
            pl.setText("P: "+(int)(100*number.doubleValue())/100.);
            SimpleDoubleProperty pprop = Modele.getInstance().getPProperty();
            synchronized (pprop){ pprop.set(Math.toRadians(number.doubleValue())); }
            Modele.getInstance().getForcesMomentService.restart();
            //Modele.getInstance().descriptionService.restart();
        }));
        ql = new Label("Q: 0");
        qs = new Slider(-Math.PI, Math.PI, 0);
        qs.valueProperty().addListener(((observableValue, number, t1) -> {
            Modele.getInstance().setQ(number.doubleValue());
            ql.setText("Q: "+(int)(100*number.doubleValue())/100.);
            SimpleDoubleProperty qprop = Modele.getInstance().getQProperty();
            synchronized (qprop){ qprop.set(Math.toRadians(number.doubleValue())); }
            Modele.getInstance().getForcesMomentService.restart();
            //Modele.getInstance().descriptionService.restart();
        }));
        rl = new Label("R: 0");
        rs = new Slider(-Math.PI, Math.PI, 0);
        rs.valueProperty().addListener(((observableValue, number, t1) -> {
            Modele.getInstance().setR(number.doubleValue());
            rl.setText("R: "+(int)(100*number.doubleValue())/100.);
            SimpleDoubleProperty rprop = Modele.getInstance().getRProperty();
            synchronized (rprop){ rprop.set(Math.toRadians(number.doubleValue())); }
            Modele.getInstance().getForcesMomentService.restart();
            //Modele.getInstance().descriptionService.restart();
        }));

        getChildren().addAll(rpl,pl, ps, ql, qs, rl, rs);
        setStyle("-fx-background-color: LIGHTGRAY; -fx-opacity:0.7;");
    }

    public void reset(){
        ps.setValue(0);
        qs.setValue(0);
        rs.setValue(0);

        Modele.getInstance().setP(0);
        Modele.getInstance().setQ(0);
        Modele.getInstance().setR(0);

        pl.setText("P: 0");
        ql.setText("Q: 0");
        rl.setText("R: 0");
    }
}
