package com.enac.vifa.vifa;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point3D;
import javafx.scene.*;

import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.MeshView;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import org.fxyz3d.shapes.primitives.ConeMesh;

import static java.lang.Double.max;
import static java.lang.Double.min;

public class Vue3D extends SubScene {

    private PerspectiveCamera camera = new PerspectiveCamera(true);
    private Group repereTerrestre;
    private Group repereAvion;
    private Group repereAero;

    //camera properties
    private SimpleDoubleProperty xrotprop = new SimpleDoubleProperty(-30);
    private SimpleDoubleProperty yrotprop = new SimpleDoubleProperty(-90-45);
    private SimpleDoubleProperty zoomprop = new SimpleDoubleProperty(2000);

    private double ZOOM_MIN_VALUE = 25;

    //mouse event vars
    private double startx;
    private double starty;

    //TODO: faire la même pour zoom, avoir constantes paramétrables
    private double limit(double d){
        if (d < -90){
            return -90;
        } else if (d > 90){
            return 90;
        } else {
            return d;
        }
    }

    public Vue3D(Scene mainScene, Group repereTerrestreVide){
        super(repereTerrestreVide, 100.0, 100.0, true, SceneAntialiasing.BALANCED);
        this.heightProperty().bind(mainScene.heightProperty());
        this.widthProperty().bind(mainScene.widthProperty());
        setFill(Color.GRAY);

        this.repereTerrestre = repereTerrestreVide;

        Color terrColor = Color.SANDYBROWN;
        Color avColor = Color.WHITE;
        Color aerColor = Color.BLUE;

        Vecteur3D trx = new Vecteur3D("x terrestre", new Point3D(300, 0, 0), new Point3D(100, 0, 0), terrColor);
        Vecteur3D trz = new Vecteur3D("z terrestre", new Point3D(0, 300, 0), new Point3D(0, 100, 0), terrColor);
        Vecteur3D try_ = new Vecteur3D("y terrestre", new Point3D(0, 0, 300), new Point3D(0, 0, 100), terrColor);

        repereTerrestre.getChildren().add(trx);
        repereTerrestre.getChildren().add(try_);
        repereTerrestre.getChildren().add(trz);

        repereAvion = new Group();

        Vecteur3D avx = new Vecteur3D("x avion", new Point3D(400, 0, 0), new Point3D(100, 0, 0), avColor);
        Vecteur3D avz = new Vecteur3D("z avion", new Point3D(0, 400, 0), new Point3D(0, 100, 0), avColor);
        Vecteur3D avy = new Vecteur3D("y avion", new Point3D(0, 0, 400), new Point3D(0, 0, 100), avColor);

        repereAvion.getChildren().add(avx);
        repereAvion.getChildren().add(avy);
        repereAvion.getChildren().add(avz);

        repereTerrestre.getChildren().add(repereAvion);
        repereAero = new Group();

        Vecteur3D aerx = new Vecteur3D("x aéro", new Point3D(500, 0, 0), new Point3D(100, 0, 0), aerColor);
        Vecteur3D aerz = new Vecteur3D("z aéro", new Point3D(0, 500, 0), new Point3D(0, 100, 0), aerColor);
        Vecteur3D aery = new Vecteur3D("y aéro", new Point3D(0, 0, 500), new Point3D(0, 0, 100), aerColor);

        repereAero.getChildren().add(aerx);
        repereAero.getChildren().add(aery);
        repereAero.getChildren().add(aerz);

        repereAvion.getChildren().add(repereAero);

        camera.setFarClip(3000.0f);

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
            if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                startx = mouseEvent.getX();
                starty = mouseEvent.getY();
            }
        }));

        setOnMouseDragged((mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.SECONDARY){
                yrotprop.set(yrotprop.get() + (mouseEvent.getX() - startx));
                xrotprop.set(limit(xrotprop.get() - (mouseEvent.getY() - starty)));
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

    public void rotateCamera(double xrot, double yrot, double zoom){
        xrotprop.set(limit(xrot));
        yrotprop.set(yrot);
        zoomprop.set(zoom);
    }

    public void rotateRepereAvion(double psi, double phi, double theta){
        repereAvion.getTransforms().setAll(
                new Rotate(psi, Rotate.Y_AXIS),
                new Rotate(-phi, Rotate.Z_AXIS),
                new Rotate(theta, Rotate.X_AXIS));
    }

    public void rotatePsi(double psi){
        repereAvion.getTransforms().set(0, new Rotate(psi, Rotate.Y_AXIS));
    }

    public void rotatePhi(double phi){
        repereAvion.getTransforms().set(1, new Rotate(-phi, Rotate.Z_AXIS));
    }

    public void rotateTheta(double theta){
        repereAvion.getTransforms().set(0, new Rotate(theta, Rotate.X_AXIS));
    }

    public void rotateRepereAero(double alpha, double beta){
        //vérifier les signes
        repereAero.getTransforms().setAll(
                new Rotate(beta, Rotate.Y_AXIS),
                new Rotate(alpha, Rotate.Z_AXIS));
    }

    public void rotateAlpha(double alpha){
        repereAero.getTransforms().set(1, new Rotate(alpha, Rotate.X_AXIS));
    }

    public void rotateBeta(double beta){
        repereAero.getTransforms().set(0, new Rotate(beta, Rotate.Y_AXIS));
    }

    public double getXrotprop() {
        return xrotprop.get();
    }

    public SimpleDoubleProperty xrotpropProperty() {
        return xrotprop;
    }

    public double getYrotprop() {
        return yrotprop.get();
    }

    public SimpleDoubleProperty yrotpropProperty() {
        return yrotprop;
    }

    public double getZoomprop() {
        return zoomprop.get();
    }

    public SimpleDoubleProperty zoompropProperty() {
        return zoomprop;
    }
}
