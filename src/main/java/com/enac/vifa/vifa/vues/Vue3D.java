package com.enac.vifa.vifa.vues;

import com.enac.vifa.vifa.Modele;
import com.enac.vifa.vifa.formes.FlecheArrondie3D;
import com.enac.vifa.vifa.formes.Vecteur3D;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point3D;
import javafx.scene.*;

import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

import static com.enac.vifa.vifa.vues.Mode.ATTITUDE;
import static java.lang.Double.max;

public class Vue3D extends SubScene {

    private PerspectiveCamera camera = new PerspectiveCamera(true);
    private Group repereTerrestre;
    private Group repereAvion;
    private Group groupeAvion;
    private Group groupe2D;
    private Group groupeForces;
    private Group repereAeroPart;
    private Group repereAero;

    private FlecheArrondie3D aphi;
    private FlecheArrondie3D apsi;
    private FlecheArrondie3D atheta;

    private FlecheArrondie3D aalpha;
    private FlecheArrondie3D abeta;

    //camera properties
    private double xrotdef = -30;
    private SimpleDoubleProperty xrotprop = new SimpleDoubleProperty(xrotdef);
    private double yrotdef = -135;
    private SimpleDoubleProperty yrotprop = new SimpleDoubleProperty(yrotdef);
    private double zoomdef = 500;
    private SimpleDoubleProperty zoomprop = new SimpleDoubleProperty(zoomdef);

    private double ZOOM_MIN_VALUE = 25;

    //mode properties
    private Mode mode;

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
        this.mode = ATTITUDE;
        this.heightProperty().bind(mainScene.heightProperty());
        this.widthProperty().bind(mainScene.widthProperty());
        setFill(Color.DARKGREY);

        this.repereTerrestre = repereTerrestreVide;
        repereTerrestre.getChildren().add(new AmbientLight(Color.WHITESMOKE));

        Color terrColor = Color.SANDYBROWN;
        Color avColor = Color.WHITE;
        Color aerColor = Color.BLUE;

        //repère terrestre
        Vecteur3D trx = new Vecteur3D("x terrestre", new Point3D(50, 0, 0), new Point3D(25, 0, 0), terrColor);
        trx.refreshView();
        Vecteur3D trz = new Vecteur3D("z terrestre", new Point3D(0, 50, 0), new Point3D(0, 25, 0), terrColor);
        trz.refreshView();
        Vecteur3D try_ = new Vecteur3D("y terrestre", new Point3D(0, 0, -50), new Point3D(0, 0, -25), terrColor);
        try_.refreshView();

        repereTerrestre.getChildren().add(trx);
        repereTerrestre.getChildren().add(try_);
        repereTerrestre.getChildren().add(trz);

        //angles de rotation du repère terrestre
        apsi = new FlecheArrondie3D("psi", 75, 0, Color.GRAY);
        apsi.getTransforms().setAll(
                new Rotate(-90, Rotate.X_AXIS)
        );
        aphi = new FlecheArrondie3D("phi", 75, 0, Color.GRAY);
        aphi.getTransforms().setAll(
                new Rotate(0, Rotate.Y_AXIS)
        );
        atheta = new FlecheArrondie3D("theta", 75, 0, Color.GRAY);
        atheta.getTransforms().setAll(
                new Rotate(-90, Rotate.Y_AXIS),
                new Rotate(0, Rotate.X_AXIS)
        );

        repereTerrestre.getChildren().add(aphi);
        repereTerrestre.getChildren().add(apsi);
        repereTerrestre.getChildren().add(atheta);

        repereAvion = new Group();

        Group groupeAvionTemp = new Group();
        groupeAvionTemp.getTransforms().addAll(new Rotate(90, Rotate.X_AXIS), new Rotate(180, Rotate.Z_AXIS));

        groupeAvion = new Group();
        groupeAvionTemp.getChildren().add(groupeAvion);
        groupeAvion.getTransforms().add(new Translate(0, 0, 0));

        groupe2D = new Group();
        groupeAvion.getChildren().add(groupe2D);

        groupeForces = new Group();
        groupeForces.getTransforms().add(new Translate(0, 0, 0));

        repereAvion.getChildren().add(groupeAvionTemp);
        repereAvion.getChildren().add(groupeForces);

        Vecteur3D avx = new Vecteur3D("x avion", new Point3D(75, 0, 0), new Point3D(25, 0, 0), avColor);
        avx.refreshView();
        Vecteur3D avz = new Vecteur3D("z avion", new Point3D(0, 75, 0), new Point3D(0, 25, 0), avColor);
        avz.refreshView();
        Vecteur3D avy = new Vecteur3D("y avion", new Point3D(0, 0, -75), new Point3D(0, 0, -25), avColor);
        avy.refreshView();

        repereAvion.getChildren().add(avx);
        repereAvion.getChildren().add(avy);
        repereAvion.getChildren().add(avz);

        aalpha = new FlecheArrondie3D("alpha", 100, 0, Color.LIGHTBLUE);

        abeta = new FlecheArrondie3D("beta", 100, 0, Color.LIGHTBLUE);
        abeta.getTransforms().setAll(
                new Rotate(90, Rotate.X_AXIS)
        );

        repereTerrestre.getChildren().add(repereAvion);

        repereAvion.getChildren().add(aalpha);

        repereAeroPart = new Group();
        repereAero = new Group();

        repereAeroPart.getChildren().add(abeta);
        repereAeroPart.getChildren().add(repereAero);

        Vecteur3D aerx = new Vecteur3D("x aéro", new Point3D(100, 0, 0), new Point3D(25, 0, 0), aerColor);
        aerx.refreshView();
        Vecteur3D aerz = new Vecteur3D("z aéro", new Point3D(0, 100, 0), new Point3D(0, 25, 0), aerColor);
        aerz.refreshView();
        Vecteur3D aery = new Vecteur3D("y aéro", new Point3D(0, 0, -100), new Point3D(0, 0, -25), aerColor);
        aery.refreshView();

        repereAero.getChildren().add(aerx);
        repereAero.getChildren().add(aery);
        repereAero.getChildren().add(aerz);

        repereAvion.getChildren().add(repereAeroPart);

        camera.setFarClip(5000.0f);

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
                startx = mouseEvent.getX();
                starty = mouseEvent.getY();
        }));

        setOnMouseDragged((mouseEvent -> {
            double x = mouseEvent.getX();
            double y = mouseEvent.getY();
            if (mouseEvent.getButton() == MouseButton.PRIMARY){
                switch (mode){

                    case ATTITUDE -> {

                    }
                    case AVION -> {


                    }
                    case AERO -> {
                        double diffx = x - startx;
                        double diffy = y - starty;
                        SimpleDoubleProperty alphaprop = Modele.getInstance().getAlphaProperty();
                        SimpleDoubleProperty betaprop = Modele.getInstance().getBetaProperty();
                        alphaprop.set(alphaprop.get()+diffy);
                        betaprop.set(betaprop.get()+diffx);
                    }
                }
            }
            else if (mouseEvent.getButton() == MouseButton.SECONDARY){
                yrotprop.set(yrotprop.get() + (x - startx));
                xrotprop.set(limit(xrotprop.get() - (y - starty)));
            }
            startx = x;
            starty = y;
        }));

        rotateRepereAvion(0, 0, 0);
        rotateRepereAero(0, 0);
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public Group getGroupe2D() {
        return groupe2D;
    }

    public void setGroupe2D(Group groupe2D) {
        this.groupe2D = groupe2D;
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

    public Group getGroupeAvion() {
        return groupeAvion;
    }

    public void setGroupeAvion(Group groupeAvion) {
        this.groupeAvion = groupeAvion;
    }

    public Group getGroupeForces() {
        return groupeForces;
    }

    public void setGroupeForces(Group groupeForces) {
        this.groupeForces = groupeForces;
    }

    public void cameraDefault(){
        rotateCamera(xrotdef, yrotdef, zoomdef);
    }

    public void rotateRepereAvion(double psi, double phi, double theta){
        repereAvion.getTransforms().setAll(
                new Rotate(psi, Rotate.Y_AXIS),
                new Rotate(-phi, Rotate.Z_AXIS),
                new Rotate(theta, Rotate.X_AXIS));
        apsi.setEndAngle(-psi);
        aphi.setEndAngle(phi);
        atheta.setEndAngle(-theta);
        aphi.getTransforms().set(0, new Rotate(psi, Rotate.Y_AXIS));
        atheta.getTransforms().set(0, new Rotate(-90+psi, Rotate.Y_AXIS));
        atheta.getTransforms().set(1, new Rotate(-phi, Rotate.X_AXIS));
    }

    public void rotatePsi(double psi){
        repereAvion.getTransforms().set(0, new Rotate(psi, Rotate.Y_AXIS));
        apsi.setEndAngle(psi);
        aphi.getTransforms().set(0, new Rotate(psi, Rotate.Y_AXIS));
        atheta.getTransforms().set(0, new Rotate(-90+psi, Rotate.Y_AXIS));
    }

    public void rotatePhi(double phi){
        repereAvion.getTransforms().set(1, new Rotate(-phi, Rotate.Z_AXIS));
        aphi.setEndAngle(-phi);
        atheta.getTransforms().set(1, new Rotate(-phi, Rotate.X_AXIS));
    }

    public void rotateTheta(double theta){
        repereAvion.getTransforms().set(2, new Rotate(theta, Rotate.X_AXIS));
        atheta.setEndAngle(-theta);
    }

    public void rotateRepereAero(double alpha, double beta){
        //vérifier les signes
        repereAero.getTransforms().setAll(new Rotate(-beta, Rotate.Y_AXIS));
        repereAeroPart.getTransforms().setAll(new Rotate(alpha, Rotate.Z_AXIS));
    }

    public void rotateAlpha(double alpha){
        repereAeroPart.getTransforms().set(0, new Rotate(alpha, Rotate.Z_AXIS));
        aalpha.setEndAngle(alpha);
    }

    public void rotateBeta(double beta){
        repereAero.getTransforms().set(0, new Rotate(-beta, Rotate.Y_AXIS));
        abeta.setEndAngle(beta);
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
