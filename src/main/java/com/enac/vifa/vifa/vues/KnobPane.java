package com.enac.vifa.vifa.vues;

import com.enac.vifa.vifa.Modele;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;

public class KnobPane extends ControllerPane{
    private final Label pl;
    private final Label phil;
    private final Label gap;

    SimpleDoubleProperty pProp;
    SimpleDoubleProperty phiProp;

    public double getPProp() {
        return pProp.get();
    }

    public void setPProp(double pProp) {
        this.pProp.set(pProp);
    }

    public double getPhiProp() {
        return phiProp.get();
    }

    public void setPhiProp(double phiProp) {
        this.phiProp.set(phiProp);
    }
    
    public KnobPane(Vue3D vue) {
        super(vue);
        pl = new Label("Vitesse de rotation p (m/s):");
        phil = new Label("Phi (deg):");
        gap = new Label ("");
        Knob pKnob = new Knob(-Math.PI, Math.PI, 0, 50);
        Knob phiKnob = new Knob(-180, 180, 0, 50);

        pKnob.valueProperty().bindBidirectional(Modele.getInstance().getPProperty());
        phiKnob.valueProperty().bindBidirectional(Modele.getInstance().getPhiProperty());
        
        setRowIndex(pl, 0);
        setColumnIndex(pl, 0);
        setRowIndex(phil, 2);
        setColumnIndex(phil, 0);
        setRowIndex(gap, 1);
        setColumnIndex(gap, 1);
        setRowIndex(pKnob, 0);
        setColumnIndex(pKnob, 1);
        setRowIndex(phiKnob, 2);
        setColumnIndex(phiKnob, 1);
        getChildren().addAll(pl,phil,gap,pKnob,phiKnob);
        this.setAlignment(Pos.CENTER_LEFT);
        setStyle("-fx-background-color: LIGHTGRAY; -fx-opacity:0.7; -fx-background-radius: 5px;");
    }
    
}
