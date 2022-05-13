package com.enac.vifa.vifa.vues;

import com.enac.vifa.vifa.Modele;
import javafx.scene.control.Button;

import java.util.ArrayList;

public class ResetButton extends Button {

    private ArrayList<ControllerPane> resettables;

    public ResetButton(ArrayList<ControllerPane> resettables){
        super();
        this.resettables = resettables;
        setStyle("-fx-background-color: LIGHTGRAY; -fx-opacity:0.7;");
        setText("RESET");
        setOnAction((actionEvent) -> {
            for (ControllerPane thing: resettables){
                thing.reset();
            }
            Modele.getInstance().descriptionService.restart();
            Modele.getInstance().getForcesMomentService.restart();
        });
    }
}
