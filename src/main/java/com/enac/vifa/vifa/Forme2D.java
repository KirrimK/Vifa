package com.enac.vifa.vifa;

import java.util.ArrayList;

import javafx.geometry.Point3D;

public class Forme2D {
    private String nom;
    private ArrayList<Point3D> contour;

    public Forme2D(String nom) {
        this.nom = nom;
        this.contour=new ArrayList<Point3D>();
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "Forme2D ["+nom + "]";
    }
    public void addPoint (Point3D p){
        this.contour.add(p);
    }
}
