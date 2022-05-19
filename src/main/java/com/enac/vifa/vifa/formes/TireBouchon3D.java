package com.enac.vifa.vifa.formes;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import org.fxyz3d.shapes.primitives.ConeMesh;

public class TireBouchon3D extends FlecheArrondie3D {

    private double xIncrPerDegree = 0.02;

    public TireBouchon3D (String nom, double rayon, double endAngle, Color couleur){
        super(nom, rayon, endAngle, couleur);
        setEndAngle(endAngle);
    }

    /**
     * Ajuste l'angle et la longueur du tire-bouchon 3D.
     * @param endAngle la nouvelle valeur en degrés
     */
    @Override
    public void setEndAngle(double endAngle){
        this.endAngle = endAngle;
        DIV_NUMBER = (Math.abs((int)endAngle)) / APPROX_FACTOR;
        Material materiau = new PhongMaterial(couleur);
        getChildren().clear();
        for (int i = 0; i < DIV_NUMBER-1; i++){
            Cylinder morceau = new Cylinder(0.2, 2*Math.toRadians(Math.abs(endAngle))/DIV_NUMBER*rayon);
            morceau.setMaterial(materiau);
            getChildren().add(morceau);
            morceau.getTransforms().setAll(
                new Rotate(i*endAngle/DIV_NUMBER, Rotate.X_AXIS),
                new Rotate(5, Rotate.Z_AXIS),
                new Translate(i*xIncrPerDegree, rayon*Math.sin(i/DIV_NUMBER*Math.toRadians(endAngle)), rayon*Math.cos(i/DIV_NUMBER*Math.toRadians(endAngle)))
            );
        }
        ConeMesh jenaimarre = new ConeMesh(1, 1);

        Group coneToRotate = new Group();
        getChildren().add(coneToRotate);
        cone.setMesh(jenaimarre.getMesh());
        cone.setMaterial(materiau);
        coneToRotate.getTransforms().setAll(
            new Rotate(endAngle, Rotate.X_AXIS),
            new Rotate(5, Rotate.Z_AXIS),
            new Translate(DIV_NUMBER*xIncrPerDegree, 0, rayon)
        );
        if ((int) endAngle != 0){
            coneToRotate.getChildren().add(cone);
            cone.getTransforms().setAll(new Rotate((endAngle<0)?180:0, Rotate.Z_AXIS));
        }
    }
}
