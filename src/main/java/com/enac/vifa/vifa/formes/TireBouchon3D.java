package com.enac.vifa.vifa.formes;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.MeshView;

import java.util.ArrayList;

public class TireBouchon3D extends Group {

    private ArrayList<Cylinder> cylindres;

    private double endAngle = 0;

    private double zIncrPerDegree = 0.01;

    private double rayon;

    private String nom;
    private Color couleur;

    private MeshView cone;

    private int DIV_NUMBER;

    public TireBouchon3D (String nom, double rayon, double endAngle, Color couleur){
        super();
        this.cylindres = new ArrayList<Cylinder>();
    }
}
