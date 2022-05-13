/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.enac.vifa.vifa.vues;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 *
 * @author hugocourtadon
 */
public class FormPane extends VBox implements Resettable{
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
        this.vue = vue;
        //Labels pour les textFields
        masse = new Label("Masse (en tonnes) : ");
        cg = new Label("Position du centre de gravité (écart en m par rapport à la position par défaut):");
        vp = new Label("Vitesse propre de l'avion (en km/h):");
        calage = new Label("Calage de l'aile (en degrés):");
        //Textfields
        
        getChildren().addAll(masse,cg,vp,calage);
        setStyle("-fx-background-color: LIGHTGRAY; -fx-opacity:0.7;");
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
