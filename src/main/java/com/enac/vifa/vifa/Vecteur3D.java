package com.enac.vifa.vifa;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.MeshView;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import org.fxyz3d.shapes.primitives.ConeMesh;

public class Vecteur3D extends Group {
    private static ConeMesh jenaimarre = new ConeMesh(64, 5, 10);

    private String nom;
    private Point3D origine, magnitude;
    private Color couleur;

    private Cylinder body;
    private MeshView cone;

    public Vecteur3D(String nom, Point3D origine, Point3D magnitude, Color couleur){
        super();
        this.couleur = couleur;
        this.nom = nom;
        Material materiau = new PhongMaterial(couleur);
        body = new Cylinder(3, magnitude.magnitude()-10);

        body.setMaterial(materiau);
        cone = new MeshView();
        cone.setMesh(jenaimarre.getMesh());
        cone.setMaterial(materiau);
        cone.getTransforms().setAll(new Rotate(180, Rotate.Z_AXIS));
        getChildren().add(body);
        getChildren().add(cone);
        setOrigineMagnitude(origine, magnitude);
        setOnMouseClicked((mouseEvent) -> {//TODO: temporaire
            Alert test = new Alert(Alert.AlertType.INFORMATION, nom);
            test.show();
        });
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Point3D getOrigine() {
        return origine;
    }

    public void setOrigineMagnitude(Point3D origine, Point3D magnitude) {
        this.origine = origine;
        this.magnitude = magnitude;
        body.setHeight(magnitude.magnitude()-10);
        body.setTranslateY(-5);

        cone.setTranslateY(magnitude.magnitude()/2);
        Point3D milieu = origine.midpoint(origine.add(magnitude));
        getTransforms().setAll(
                new Translate(milieu.getX(), milieu.getY(), milieu.getZ()),
                new Rotate(Math.toDegrees(Math.atan2(magnitude.getX(), magnitude.getZ())), Rotate.Y_AXIS),
                new Rotate(Math.toDegrees(Math.atan2( Math.sqrt(Math.pow(magnitude.getX(), 2)+Math.pow(magnitude.getZ(), 2)), magnitude.getY() ))/*45*/, Rotate.X_AXIS));
    }

    public Point3D getMagnitude() {
        return magnitude;
    }


    public Color getCouleur() {
        return couleur;
    }

    public void setCouleur(Color couleur) {
        this.couleur = couleur;
        Material materiau = new PhongMaterial(couleur);
        body.setMaterial(materiau);
        cone.setMaterial(materiau);
    }
}
