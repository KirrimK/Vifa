package com.enac.vifa.vifa.formes;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.MeshView;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import org.fxyz3d.shapes.primitives.ConeMesh;

import java.util.ArrayList;

public class FlecheArrondie3D extends Group {

    private ArrayList<Cylinder> aled;

    private double startAngle = 0;
    private double endAngle;

    private double rayon;

    private String nom;
    private Color couleur;

    private MeshView cone;

    private static final int APPROX_FACTOR = 2;
    private int DIV_NUMBER;

    /**
     *
     * @param nom Le nom de l'angle représenté par la flèche
     * @param rayon Le point d'où la flèche part
     * @param endAngle L'angle en degrés (>0 vers le "bas")
     * @param couleur Couleur de la flèche courbe
     */
    public FlecheArrondie3D (String nom, double rayon, double endAngle, Color couleur){
        super();
        this.aled = new ArrayList<Cylinder>();
        this.rayon = rayon;
        this.couleur = couleur;
        this.nom = nom;
        this.cone = new MeshView();
        setEndAngle(endAngle);
    }

    public double getEndAngle() {
        return endAngle;
    }

    public void setEndAngle(double endAngle) {
        this.endAngle = endAngle;
        DIV_NUMBER = (Math.abs((int)endAngle)) / APPROX_FACTOR;
        Material materiau = new PhongMaterial(couleur);
        getChildren().clear();
        for (int i = 1; i < DIV_NUMBER-1; i++) {
            Cylinder oskours = new Cylinder(1, 2*Math.toRadians(Math.abs(endAngle))/DIV_NUMBER*rayon);
            oskours.setMaterial(materiau);
            getChildren().add(oskours);
            aled.add(oskours);
            oskours.getTransforms().setAll(
                    new Rotate(i*endAngle/DIV_NUMBER, Rotate.Z_AXIS),
                    new Translate(rayon*Math.cos(i/DIV_NUMBER*Math.toRadians(endAngle)), rayon*Math.sin(i/DIV_NUMBER*Math.toRadians(endAngle)), 0));
        }
        ConeMesh jenaimarre = new ConeMesh(3, 2*Math.toRadians(Math.abs(endAngle))/DIV_NUMBER*rayon);
        cone.setMesh(jenaimarre.getMesh());
        cone.setMaterial(materiau);
        cone.getTransforms().setAll(
                new Translate(rayon*Math.cos(Math.toRadians(endAngle)), rayon*Math.sin(Math.toRadians(endAngle))),
                new Rotate(endAngle<0?(endAngle):(-180+endAngle), Rotate.Z_AXIS));
        if ((int) endAngle != 0){
            getChildren().add(cone);
        }
    }

    public double getRayon() {
        return rayon;
    }

    public String getNom() {
        return nom;
    }

    public Color getCouleur() {
        return couleur;
    }
}
