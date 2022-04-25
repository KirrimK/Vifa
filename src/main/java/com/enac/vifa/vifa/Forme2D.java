package com.enac.vifa.vifa;

import java.util.ArrayList;

import javafx.geometry.Point3D;

public class Forme2D {
    private String nom;
    private ArrayList<Point3D> contour;
    private ArrayList<int[]> faces;

    public Forme2D(String nom) {
        this.nom = nom;
        this.contour=new ArrayList<Point3D>();
        this.faces=new ArrayList<int[]>();
    }

    public String getNom() {
        return nom;
    }

    public ArrayList<Point3D> getContour() {
        return contour;
    }

    public ArrayList<int[]> getFaces() {
        return faces;
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
    public void addFace (int[] f){
        this.faces.add(f);
    }
}