package com.enac.vifa.vifa.vues;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class UI extends StackPane {

    public UI(Vue3D vue){
        super();
        getChildren().add(vue);

        RepereControllerPane repb = new RepereControllerPane(vue);
        PQRPane pqrb = new PQRPane(vue);

        VBox repsEtAngles = new VBox(5, repb, pqrb);
        repsEtAngles.setMaxSize(170, 200);
        setAlignment(repsEtAngles, Pos.CENTER_LEFT);
        setMargin(repsEtAngles, new Insets(5));

        FormPane form = new FormPane(vue);
        form.setMaxSize(330, 100);
        setAlignment(form, Pos.TOP_RIGHT);
        setMargin(form, new Insets(5));

        CameraInfoPane camInfo = new CameraInfoPane(vue);
        camInfo.setMaxSize(150, 150);
        setAlignment(camInfo, Pos.TOP_LEFT);
        setMargin(camInfo, new Insets(5));

        GouverneControllerPane gouvCtl = new GouverneControllerPane(vue);
        setMargin(gouvCtl, new Insets(5));
        ThrottlePane throttle = new ThrottlePane(vue);

        HBox cmdes = new HBox(5, throttle, gouvCtl);
        cmdes.setMaxSize(230, 200);
        setMargin(cmdes, new Insets(5));
        cmdes.setAlignment(Pos.CENTER_RIGHT);
        setAlignment(cmdes, Pos.BOTTOM_RIGHT);

        ArrayList<ControllerPane> resettables = new ArrayList<>();
        resettables.add(repb);
        resettables.add(pqrb);
        resettables.add(gouvCtl);
        resettables.add(form);
        resettables.add(throttle);

        ResetButton resetb = new ResetButton(resettables);
        setAlignment(resetb, Pos.CENTER_RIGHT);

        ModeChoice modeSelect = new ModeChoice(vue);
        setAlignment(modeSelect, Pos.BOTTOM_LEFT);

        HBox modeAndReset = new HBox(5, resetb, modeSelect);
        repsEtAngles.getChildren().add(modeAndReset);

        getChildren().add(repsEtAngles);
        getChildren().add(camInfo);
        getChildren().add(form);
        getChildren().add(cmdes);
    }
}