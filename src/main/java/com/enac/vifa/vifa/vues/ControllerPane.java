package com.enac.vifa.vifa.vues;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public abstract class ControllerPane extends GridPane {


    public ControllerPane(Vue3D vue) {
    }

    protected void genericSliderListener(String labelText, Label label, Number newValue){
        String text = labelText + newValue.doubleValue();
        label.setText(text.substring(0, Math.min(20, text.length())));
    }

    protected void reset(){

    }
}
