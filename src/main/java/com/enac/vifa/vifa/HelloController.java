package com.enac.vifa.vifa;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    private int counter = 0;
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        if (counter==0){
            welcomeText.setText("Welcome to JavaFX Application!");
        }
        else {
            welcomeText.setText("Welcome to JavaFX Application!\n Button pressed "+ (counter+1) +" times.");
        }
        counter++;
    }
}