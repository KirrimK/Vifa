package com.enac.vifa.vifa;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.*;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

import static java.lang.Double.max;

public class Vue3D extends SubScene {

    private PerspectiveCamera camera = new PerspectiveCamera(true);
    private Group repereTerrestre;
    private Group repereAvion;
    private Group repereAero;


    public Vue3D(Scene mainScene, Group repereTerrestre){
        super(repereTerrestre, 100.0, 100.0, true, SceneAntialiasing.BALANCED);
        this.heightProperty().bind(mainScene.heightProperty());
        this.widthProperty().bind(mainScene.widthProperty());
        setFill(Color.GRAY);

        this.repereTerrestre = repereTerrestre;
        repereAvion = new Group();
        repereTerrestre.getChildren().add(repereAvion);
        repereAero = new Group();
        repereAvion.getChildren().add(repereAero);

        camera.setFarClip(1000.0f);

        setCamera(camera);


    }

    public Group getRepereTerrestre() {
        return repereTerrestre;
    }

    public Group getRepereAvion() {
        return repereAvion;
    }

    public Group getRepereAero() {
        return repereAero;
    }

    public void rotateRepereAvion(double psi, double phi, double theta){

    }

    public void rotateRepereAero(double alpha, double beta){

    }
}
