package com.enac.vifa.vifa.vues;

import com.enac.vifa.vifa.Modele;
import javafx.concurrent.Worker;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;

public class GouverneControllerPane extends GridPane {

    private final Label profInfo;
    private final Slider prof;
    private final Label dirInfo;
    private final Slider dir;
    private final Label ailInfo;
    private final Slider ailerons;

    public GouverneControllerPane(){
        super();
        profInfo = new Label("Profondeur: 0");
        setRowIndex(profInfo, 2);
        setColumnIndex(profInfo, 1);
        prof = new Slider(-93, 93, 0);
        prof.valueProperty().addListener(((observableValue, number, t1) -> {
            profInfo.setText("Profondeur: "+number.intValue());
            Modele mod = Modele.getInstance();
            synchronized(mod.getDmProperty()){
                mod.getDmProperty().set(Math.toRadians(number.doubleValue()));
            }
            mod.descriptionService.restart();
            mod.getForcesMomentService.restart();
        }));
        setRowIndex(prof, 0);
        setColumnIndex(prof, 0);
        dirInfo = new Label("Direction: 0");
        setRowIndex(dirInfo, 3);
        setColumnIndex(dirInfo, 1);
        dir = new Slider(-93, 93, 0);
        dir.valueProperty().addListener(((observableValue, number, t1) -> {
            dirInfo.setText("Direction: "+number.intValue());
            Modele mod = Modele.getInstance();
            synchronized(mod.getDnProperty()){
                mod.getDnProperty().set(Math.toRadians(number.doubleValue()));
            }
            mod.descriptionService.restart();
            mod.getForcesMomentService.restart();
        }));
        setRowIndex(dir, 1);
        setColumnIndex(dir, 1);
        ailInfo = new Label("Ailerons: 0");
        setRowIndex(ailInfo, 4);
        setColumnIndex(ailInfo, 1);
        ailerons = new Slider(-93, 93, 0);
        ailerons.valueProperty().addListener(((observableValue, number, t1) -> {
            ailInfo.setText("Ailerons: "+number.intValue());
            Modele mod = Modele.getInstance();
            synchronized(mod.getDlProperty()){
                mod.getDlProperty().set(Math.toRadians(number.doubleValue()));
            }
            mod.descriptionService.restart();
            mod.getForcesMomentService.restart();
        }));
        setRowIndex(ailerons, 0);
        setColumnIndex(ailerons, 2);
        prof.setOrientation(Orientation.VERTICAL);
        ailerons.setOrientation(Orientation.VERTICAL);
        getChildren().addAll(prof, dir, ailerons, profInfo, dirInfo, ailInfo);
        setStyle("-fx-background-color: LIGHTGRAY; -fx-opacity:0.7;");
    }

    public void reset(){
        prof.setValue(0);
        dir.setValue(0);
        ailerons.setValue(0);
        profInfo.setText("Profondeur: 0");
        dirInfo.setText("Direction: 0");
        ailInfo.setText("Ailerons: 0");
        Modele.getInstance().descriptionService.restart();
        Modele.getInstance().getForcesMomentService.restart();
    }
}
