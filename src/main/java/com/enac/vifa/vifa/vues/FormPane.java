/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.enac.vifa.vifa.vues;

import com.enac.vifa.vifa.Modele;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author hugocourtadon
 */
public class FormPane extends ControllerPane {
    private Label masse;
   
    private TextField masses;
    
    private Label cg;
    private TextField cgs;
    
    private Label vp;
    private TextField vps;
    
    private Label calage;
    private TextField calages;
    
    private Vue3D vue;
    
    public FormPane(Vue3D vue){
        //Box sliders repères
        super(vue);
        
        Modele model = Modele.getInstance();
        //Masse
        masse = new Label("Masse (en tonnes) : ");
        setRowIndex(masse, 0);
        setColumnIndex(masse, 0);
        masses = new TextField(Double.toString(model.getMass()));
        setRowIndex(masses, 0);
        setColumnIndex(masses, 1);
        
        //Position du centre de gravité de l'avion
        cg = new Label("Position du centre de gravité (écart en m par rapport à la position par défaut):");
        setRowIndex(cg, 1);
        setColumnIndex(cg, 0);
        cgs = new TextField(Double.toString(model.getxCentrage()));
        setRowIndex(cgs, 1);
        setColumnIndex(cgs, 1);
        
        //Vitesse propre
        vp = new Label("Vitesse propre de l'avion (en Noeud):");
        setRowIndex(vp, 2);
        setColumnIndex(vp, 0);
        vps = new TextField(Double.toString(model.getvAir()));
        setRowIndex(vps, 2);
        setColumnIndex(vps, 1);
        
        //Calage de l'aile
        calage = new Label("Calage de l'aile (en degrés):");
        setRowIndex(calage, 3);
        setColumnIndex(calage, 0);
        calages = new TextField(Double.toString(model.getA0()));
        setRowIndex(calages, 3);
        setColumnIndex(calages, 1);
       
        
        getChildren().addAll(masse,masses,cg,cgs,vp,vps,calage,calages);
        setStyle("-fx-background-color: LIGHTGRAY; -fx-opacity:0.7;");
    }

    @Override
    public void reset() {

    }
}
