package com.enac.vifa.vifa.vues;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;


/**
 * Panneau abstrait.
 * Contient des méthode permetant un reset.
 */
public abstract class ControllerPane extends GridPane {


    protected boolean resetting = false;

    public ControllerPane(Vue3D vue) {
        setPadding(new Insets(4, 4, 4, 4));
    }

    protected void genericSliderListener(String labelText, Label label, Number newValue){
        String text = labelText + newValue.doubleValue();
        label.setText(text.substring(0, Math.min(20, text.length())));
    }

    protected void reset(){

    }
}
