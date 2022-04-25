/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.enac.vifa.vifa;

import java.util.ArrayList;
import javafx.geometry.Point3D;

/**
 *
 * @author hugocourtadon
 */
public class Forme3D {
    private String nom;
    private  ArrayList<Point3D> contour;
    private ArrayList<int[]> faces;

public Forme3D(String nom) {
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
        return "Forme3D ["+nom + "]";
    }
    public void addPoint (Point3D p){
        this.contour.add(p);
    }
    public void addFace (int[] f){
        this.faces.add(f);
    }
}