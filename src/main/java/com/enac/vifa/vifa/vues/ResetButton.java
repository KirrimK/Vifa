package com.enac.vifa.vifa.vues;

import com.enac.vifa.vifa.Modele;
import javafx.scene.control.Button;

import java.util.ArrayList;

public class ResetButton extends Button {

    private ArrayList<? extends Resettable> resettables;

    public ResetButton(ArrayList<? extends Resettable> resettables){
        super();
        this.resettables = resettables;
        setStyle("-fx-background-color: LIGHTGRAY; -fx-opacity:0.7;");
        setText("RESET");
        setOnAction((actionEvent) -> {
            for (Resettable thing: resettables){
                thing.reset();
            }
            Modele.getInstance().getForcesMomentService.restart();
            Modele.getInstance().descriptionService.restart();
        });
    }
}
