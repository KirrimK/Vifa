package com.enac.vifa.vifa.vues;

import com.enac.vifa.vifa.Modele;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

public class GouverneControllerPane extends ControllerPane {

    private final Label profInfo;
    private final Slider prof;
    private final Label dirInfo;
    private final Slider dir;
    private final Label ailInfo;
    private final Slider ailerons;

    public GouverneControllerPane(Vue3D vue){
        super(vue);
        profInfo = new Label("Profondeur (deg): 0.0");
        setRowIndex(profInfo, 2);
        setColumnIndex(profInfo, 1);
        prof = new Slider(-90, 90, 0);
        prof.valueProperty().bindBidirectional(Modele.getInstance().getDmProperty());
        prof.valueProperty().addListener(((observableValue, number, t1) -> {
            genericSliderListener("Profondeur (deg): ", profInfo, t1);
            if (!resetting){
                Modele.getInstance().descriptionService.restart();
                Modele.getInstance().getForcesMomentService.restart();
            }
        }));
        setRowIndex(prof, 0);
        setColumnIndex(prof, 0);

        dirInfo = new Label("Direction (deg): 0.0");
        setRowIndex(dirInfo, 3);
        setColumnIndex(dirInfo, 1);
        dir = new Slider(-90, 90, 0);
        dir.valueProperty().bindBidirectional(Modele.getInstance().getDnProperty());
        dir.valueProperty().addListener(((observableValue, number, t1) -> {
            genericSliderListener("Direction (deg): ", dirInfo, t1);
            if (!resetting){
                Modele.getInstance().descriptionService.restart();
                Modele.getInstance().getForcesMomentService.restart();
            }
        }));
        setRowIndex(dir, 1);
        setColumnIndex(dir, 1);
        ailInfo = new Label("Ailerons (deg): 0.0");
        setRowIndex(ailInfo, 4);
        setColumnIndex(ailInfo, 1);
        ailerons = new Slider(-90, 90, 0);
        ailerons.valueProperty().bindBidirectional(Modele.getInstance().getDlProperty());
        ailerons.valueProperty().addListener(((observableValue, number, t1) -> {
            genericSliderListener("Ailerons (deg): ", ailInfo, t1);
            if (!resetting){
                Modele.getInstance().descriptionService.restart();
                Modele.getInstance().getForcesMomentService.restart();
            }
        }));
        setRowIndex(ailerons, 0);
        setColumnIndex(ailerons, 2);
        prof.setOrientation(Orientation.VERTICAL);
        ailerons.setOrientation(Orientation.VERTICAL);
        getChildren().addAll(prof, dir, ailerons, profInfo, dirInfo, ailInfo);
        setStyle("-fx-background-color: LIGHTGRAY; -fx-opacity:0.7;");
    }

    public void reset(){
        resetting = true;
        Modele.getInstance().setDl(0);
        Modele.getInstance().setDm(0);
        Modele.getInstance().setDn(0);
        resetting = false;
    }
}
