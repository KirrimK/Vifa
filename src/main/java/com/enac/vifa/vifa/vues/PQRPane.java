package com.enac.vifa.vifa.vues;

import com.enac.vifa.vifa.Modele;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

public class PQRPane extends ControllerPane {

    private Label pl;
    private Slider ps;

    private Label ql;
    private Slider qs;

    private Label rl;
    private Slider rs;


    public PQRPane(Vue3D vue) {
        super(vue);
        Label rpl = new Label("Vitesses de rotation:");

        pl = new Label("P (rad/s): 0.0");
        setRowIndex(pl, 1);
        ps = new Slider(-Math.PI, Math.PI, 0);
        setRowIndex(ps, 2);
        ps.valueProperty().bindBidirectional(Modele.getInstance().getPProperty());
        ps.valueProperty().addListener(((observableValue, number, t1) -> {
            genericSliderListener("P (rad/s): ", pl, t1);
            vue.updateP(t1.doubleValue());
            if (!resetting) Modele.getInstance().getForcesMomentService.restart();
        }));

        ql = new Label("Q (rad/s): 0.0");
        setRowIndex(ql, 3);
        qs = new Slider(-Math.PI, Math.PI, 0);
        setRowIndex(qs, 4);
        qs.valueProperty().bindBidirectional(Modele.getInstance().getQProperty());
        qs.valueProperty().addListener(((observableValue, number, t1) -> {
            genericSliderListener("Q (rad/s): ", ql, t1);
            vue.updateQ(t1.doubleValue());
            if (!resetting) Modele.getInstance().getForcesMomentService.restart();
        }));

        rl = new Label("R (rad/s): 0.0");
        setRowIndex(rl, 5);
        rs = new Slider(-Math.PI, Math.PI, 0);
        setRowIndex(rs, 6);
        rs.valueProperty().bindBidirectional(Modele.getInstance().getRProperty());
        rs.valueProperty().addListener(((observableValue, number, t1) -> {
            genericSliderListener("R (rad/s): ", rl, t1);
            vue.updateR(t1.doubleValue());
            if (!resetting) Modele.getInstance().getForcesMomentService.restart();
        }));

        getChildren().addAll(rpl,pl, ps, ql, qs, rl, rs);
        setStyle("-fx-background-color: LIGHTGRAY; -fx-opacity:0.7;");
    }

    public void reset(){
        resetting = true;
        Modele.getInstance().setP(0);
        Modele.getInstance().setQ(0);
        Modele.getInstance().setR(0);
        resetting = false;
    }
}
