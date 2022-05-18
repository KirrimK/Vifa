package com.enac.vifa.vifa.vues;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;


/**
 * Panneau abstrait.
 * Contient des méthode permetant un reset.
 */
public abstract class ControllerPane extends GridPane {


    protected boolean resetting = false;

    public ControllerPane(Vue3D vue) {
    }

    protected void genericSliderListener(String labelText, Label label, Number newValue){
        String text = labelText + newValue.doubleValue();
        label.setText(text.substring(0, Math.min(20, text.length())));
    }

    protected void reset(){

    }
}
