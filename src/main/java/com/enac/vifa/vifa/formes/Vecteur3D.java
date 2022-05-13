package com.enac.vifa.vifa.formes;

import javafx.geometry.Point3D;
import javafx.scene.Group;
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
    private ConeMesh jenaimarre;

    private String nom;
    private Point3D origine, magnitude;
    private Color couleur;

    private Cylinder body;
    private MeshView cone;

    private boolean seen;

    /**
     *
     * @param nom Le nom de la force ou de l'axe représenté
     * @param origine Le point d'origine du vecteur
     * @param magnitude La magnitude du vecteur
     * @param couleur La couleur du vecteur
     */
    public Vecteur3D(String nom, Point3D origine, Point3D magnitude, Color couleur){
        super();
        this.couleur = couleur;
        this.nom = nom;
        Material materiau = new PhongMaterial(couleur);
        jenaimarre = new ConeMesh(64, 1, (magnitude.magnitude()<1)?0.25:1);
        body = new Cylinder(0.4, magnitude.magnitude()-jenaimarre.getHeight());

        body.setMaterial(materiau);
        cone = new MeshView();
        cone.setMesh(jenaimarre.getMesh());
        cone.setMaterial(materiau);
        cone.getTransforms().setAll(new Rotate(180, Rotate.Z_AXIS));
        getChildren().add(body);
        getChildren().add(cone);
        seen = true;
        setOrigineMagnitude(origine, magnitude);
        Tooltip tooltip = new Tooltip(nom);
        Tooltip.install(this, tooltip);
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
    }

    public void refreshView(){
        body.setHeight(magnitude.magnitude()-jenaimarre.getHeight());
        body.setTranslateY(-jenaimarre.getHeight()/2);

        jenaimarre.setHeight((magnitude.magnitude()<1)?0.25:1);
        cone.setMesh(jenaimarre.getMesh());
        cone.setTranslateY(magnitude.magnitude()/2);
        if (Math.abs(magnitude.magnitude()) < 0.0001 && seen) {
            getChildren().clear();
            seen = false;
        } else if (Math.abs(magnitude.magnitude()) >= 0.0001 && (!seen)){
            getChildren().addAll(body, cone);
            seen = true;
        }
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
