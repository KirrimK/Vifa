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

    //camera properties
    private SimpleDoubleProperty xrotprop = new SimpleDoubleProperty(0);
    private SimpleDoubleProperty yrotprop = new SimpleDoubleProperty(0);
    private SimpleDoubleProperty zoomprop = new SimpleDoubleProperty(25);

    private double ZOOM_MIN_VALUE = 25;

    //mouse event vars
    private double startx;
    private double starty;

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

        camera.getTransforms().setAll (
                new Rotate(yrotprop.get(), Rotate.Y_AXIS),
                new Rotate(xrotprop.get(), Rotate.X_AXIS),
                new Translate(0, 0, -zoomprop.get()));

        xrotprop.addListener(((observableValue, number, t1) -> {
            camera.getTransforms().set(1, new Rotate(xrotprop.get(), Rotate.X_AXIS));
        }));

        yrotprop.addListener(((observableValue, number, t1) -> {
            camera.getTransforms().set(0, new Rotate(yrotprop.get(), Rotate.Y_AXIS));
        }));

        zoomprop.addListener(((observableValue, number, t1) -> {
            camera.getTransforms().set(2, new Translate(0, 0, -zoomprop.get()));
        }));

        setOnScroll((event) -> {
            zoomprop.set(max(-event.getDeltaY()/2 + zoomprop.get(), ZOOM_MIN_VALUE));
        });

        setOnMousePressed((mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.SECONDARY){
                startx = mouseEvent.getX();
                starty = mouseEvent.getY();
            }
        }));

        setOnMouseDragged((mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.SECONDARY){
                yrotprop.set(yrotprop.get() + (mouseEvent.getX() - startx));
                xrotprop.set(xrotprop.get() - (mouseEvent.getY() - starty));
                startx = mouseEvent.getX();
                starty = mouseEvent.getY();
            }
        }));
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
