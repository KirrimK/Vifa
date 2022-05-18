package com.enac.vifa.vifa.formes;

import com.enac.vifa.vifa.Configuration;

import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;

/**
 * Classe représentant le sol.
 * Sa hauteur et sa couleur sont réglables depuis le fichier de configuration.
 */
public class FormeDeSol extends Cylinder{
    private double hauteur;
    private Color couleur;

    public FormeDeSol() {
        Configuration c = Configuration.getInstance();
        this.hauteur = c.getHauteur();
        this.couleur = c.getCouleurSol();
        Material m = new PhongMaterial(couleur);
        this.setMaterial(m);
        this.setTranslateY(hauteur);
        this.setHeight(0.1);
        this.setRadius(2000);
        
    }
    
}
