package com.enac.vifa.vifa;

import javafx.beans.property.SimpleDoubleProperty;
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

        Material terrMat = new PhongMaterial(Color.SANDYBROWN);
        Material avMat = new PhongMaterial(Color.WHITE);
        Material aerMat = new PhongMaterial(Color.BLUE);

        ConeMesh jenaimarre = new ConeMesh(64, 4, 10);

        Cylinder xarrcy = new Cylinder(2, 100);
        xarrcy.setMaterial(terrMat);
        xarrcy.getTransforms().setAll(new Rotate(90, Rotate.Z_AXIS));
        xarrcy.setTranslateX(200);

        MeshView xarrcn = new MeshView();
        xarrcn.setMesh(jenaimarre.getMesh());
        xarrcn.setMaterial(terrMat);
        xarrcn.getTransforms().setAll(new Rotate(90, Rotate.Z_AXIS));
        xarrcn.setTranslateX(260);

        Cylinder yarrcy = new Cylinder(2, 100);
        yarrcy.setMaterial(terrMat);
        yarrcy.getTransforms().setAll(new Rotate(90, Rotate.X_AXIS));
        yarrcy.setTranslateZ(200);

        MeshView yarrcn = new MeshView();
        yarrcn.setMesh(jenaimarre.getMesh());
        yarrcn.setMaterial(terrMat);
        yarrcn.getTransforms().setAll(new Rotate(-90, Rotate.X_AXIS));
        yarrcn.setTranslateZ(260);

        Cylinder zarrcy = new Cylinder(2, 100);
        zarrcy.setMaterial(terrMat);
        zarrcy.setTranslateY(200);

        MeshView zarrcn = new MeshView();
        zarrcn.setMesh(jenaimarre.getMesh());
        zarrcn.setMaterial(terrMat);
        zarrcn.getTransforms().setAll(new Rotate(180, Rotate.X_AXIS));
        zarrcn.setTranslateY(260);

        repereTerrestre.getChildren().add(xarrcy);
        repereTerrestre.getChildren().add(xarrcn);
        repereTerrestre.getChildren().add(yarrcy);
        repereTerrestre.getChildren().add(yarrcn);
        repereTerrestre.getChildren().add(zarrcy);
        repereTerrestre.getChildren().add(zarrcn);

        repereAvion = new Group();

        Cylinder xarrcy_av = new Cylinder(2, 100);
        xarrcy_av.setMaterial(avMat);
        xarrcy_av.getTransforms().setAll(new Rotate(90, Rotate.Z_AXIS));
        xarrcy_av.setTranslateX(300);

        MeshView xarrcn_av = new MeshView();
        xarrcn_av.setMesh(jenaimarre.getMesh());
        xarrcn_av.setMaterial(avMat);
        xarrcn_av.getTransforms().setAll(new Rotate(90, Rotate.Z_AXIS));
        xarrcn_av.setTranslateX(360);

        Cylinder yarrcy_av = new Cylinder(2, 100);
        yarrcy_av.setMaterial(avMat);
        yarrcy_av.getTransforms().setAll(new Rotate(90, Rotate.X_AXIS));
        yarrcy_av.setTranslateZ(300);

        MeshView yarrcn_av = new MeshView();
        yarrcn_av.setMesh(jenaimarre.getMesh());
        yarrcn_av.setMaterial(avMat);
        yarrcn_av.getTransforms().setAll(new Rotate(-90, Rotate.X_AXIS));
        yarrcn_av.setTranslateZ(360);

        Cylinder zarrcy_av = new Cylinder(2, 100);
        zarrcy_av.setMaterial(avMat);
        zarrcy_av.setTranslateY(300);

        MeshView zarrcn_av = new MeshView();
        zarrcn_av.setMesh(jenaimarre.getMesh());
        zarrcn_av.setMaterial(avMat);
        zarrcn_av.getTransforms().setAll(new Rotate(180, Rotate.X_AXIS));
        zarrcn_av.setTranslateY(360);

        repereAvion.getChildren().add(xarrcy_av);
        repereAvion.getChildren().add(xarrcn_av);
        repereAvion.getChildren().add(yarrcy_av);
        repereAvion.getChildren().add(yarrcn_av);
        repereAvion.getChildren().add(zarrcy_av);
        repereAvion.getChildren().add(zarrcn_av);

        repereTerrestre.getChildren().add(repereAvion);
        repereAero = new Group();

        Cylinder xarrcy_ar = new Cylinder(2, 100);
        xarrcy_ar.setMaterial(aerMat);
        xarrcy_ar.getTransforms().setAll(new Rotate(90, Rotate.Z_AXIS));
        xarrcy_ar.setTranslateX(400);

        MeshView xarrcn_ar = new MeshView();
        xarrcn_ar.setMesh(jenaimarre.getMesh());
        xarrcn_ar.setMaterial(aerMat);
        xarrcn_ar.getTransforms().setAll(new Rotate(90, Rotate.Z_AXIS));
        xarrcn_ar.setTranslateX(460);

        Cylinder yarrcy_ar = new Cylinder(2, 100);
        yarrcy_ar.setMaterial(aerMat);
        yarrcy_ar.getTransforms().setAll(new Rotate(90, Rotate.X_AXIS));
        yarrcy_ar.setTranslateZ(400);

        MeshView yarrcn_ar = new MeshView();
        yarrcn_ar.setMesh(jenaimarre.getMesh());
        yarrcn_ar.setMaterial(aerMat);
        yarrcn_ar.getTransforms().setAll(new Rotate(-90, Rotate.X_AXIS));
        yarrcn_ar.setTranslateZ(460);

        Cylinder zarrcy_ar = new Cylinder(2, 100);
        zarrcy_ar.setMaterial(aerMat);
        zarrcy_ar.setTranslateY(400);

        MeshView zarrcn_ar = new MeshView();
        zarrcn_ar.setMesh(jenaimarre.getMesh());
        zarrcn_ar.setMaterial(aerMat);
        zarrcn_ar.getTransforms().setAll(new Rotate(180, Rotate.X_AXIS));
        zarrcn_ar.setTranslateY(460);

        repereAero.getChildren().add(xarrcy_ar);
        repereAero.getChildren().add(xarrcn_ar);
        repereAero.getChildren().add(yarrcy_ar);
        repereAero.getChildren().add(yarrcn_ar);
        repereAero.getChildren().add(zarrcy_ar);
        repereAero.getChildren().add(zarrcn_ar);

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
            if (mouseEvent.getButton() == MouseButton.SECONDARY){
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
}
