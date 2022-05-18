package com.enac.vifa.vifa.vues;

import com.enac.vifa.vifa.Modele;
import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.util.converter.NumberStringConverter;

public class PQRPane extends ControllerPane {

    private final Label pl;
    private final Slider ps;
    private final TextField pls;

    private final Label ql;
    private final Slider qs;
    private final TextField qls;

    private final Label rl;
    private final Slider rs;
    private final TextField rls;


    public PQRPane(Vue3D vue) {
        super(vue);
        Label rpl = new Label("Vitesses de rotation:");

        pl = new Label("P (rad/s):");
        setRowIndex(pl, 1);
        pls = new TextField("0.0");
        pls.setMaxSize(50, 50);
        setRowIndex(pls, 2);
        setColumnIndex(pls, 1);
        ps = new Slider(-Math.PI, Math.PI, 0);
        setRowIndex(ps, 2);
        ps.valueProperty().bindBidirectional(Modele.getInstance().getPProperty());
        ps.valueProperty().addListener(((observableValue, number, t1) -> {
            genericSliderListener("P (rad/s): ", pl, t1);
            vue.updateP(t1.doubleValue());
            if (!resetting) Modele.getInstance().getForcesMomentService.restart();
        }));
        //Binding bidirectionnel slider-Textfield
        NumberStringConverter sPl = new NumberStringConverter();
        Bindings.bindBidirectional(pls.textProperty(), ps.valueProperty(), sPl);

        ql = new Label("Q (rad/s):");
        setRowIndex(ql, 3);
        qls = new TextField("0.0");
        qls.setMaxSize(50, 50);
        setRowIndex(qls, 4);
        setColumnIndex(qls, 1);
        qs = new Slider(-Math.PI, Math.PI, 0);
        setRowIndex(qs, 4);
        qs.valueProperty().bindBidirectional(Modele.getInstance().getQProperty());
        qs.valueProperty().addListener(((observableValue, number, t1) -> {
            genericSliderListener("Q (rad/s): ", ql, t1);
            vue.updateQ(t1.doubleValue());
            if (!resetting) Modele.getInstance().getForcesMomentService.restart();
        }));
        //Binding bidirectionnel slider-Textfield
        NumberStringConverter sQl = new NumberStringConverter();
        Bindings.bindBidirectional(qls.textProperty(), qs.valueProperty(), sQl);

        rl = new Label("R (rad/s):");
        setRowIndex(rl, 5);
        rls = new TextField("0.0");
        rls.setMaxSize(50, 50);
        setRowIndex(rls, 6);
        setColumnIndex(rls, 1);
        rs = new Slider(-Math.PI, Math.PI, 0);
        setRowIndex(rs, 6);
        rs.valueProperty().bindBidirectional(Modele.getInstance().getRProperty());
        rs.valueProperty().addListener(((observableValue, number, t1) -> {
            genericSliderListener("R (rad/s): ", rl, t1);
            vue.updateR(t1.doubleValue());
            if (!resetting) Modele.getInstance().getForcesMomentService.restart();
        }));
        //Binding bidirectionnel slider-Textfield
        NumberStringConverter sRl = new NumberStringConverter();
        Bindings.bindBidirectional(rls.textProperty(), rs.valueProperty(), sRl);

        getChildren().addAll(rpl,pl,pls, ps, ql,qls, qs, rl,rls, rs);
        setStyle("-fx-background-color: LIGHTGRAY; -fx-opacity:0.7;");

        for (Node node: getChildren()){
            setHgrow(node, Priority.ALWAYS);
            setVgrow(node, Priority.ALWAYS);
        }
    }

    public void reset(){
        resetting = true;
        Modele.getInstance().setP(0);
        Modele.getInstance().setQ(0);
        Modele.getInstance().setR(0);
        resetting = false;
    }
}
