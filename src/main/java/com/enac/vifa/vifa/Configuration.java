package com.enac.vifa.vifa;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.enac.vifa.vifa.vues.Mode;

import javafx.scene.paint.Color;

public class Configuration {
    private static Configuration conf;
    private Mode mode;
    private double zoomMax = 1000;
    private double zoomMin = 25;
    private double hauteur = 100;
    private String launchMessage = "";
    private int facteurApproximationArrondis = 2;
    private Color couleurSurfaceControle = Color.ORANGE;
    private Color couleurFuselage = Color.GREY;
    private Color couleurAiles = Color.WHITE;
    private Color couleurRepèreTerrestre = Color.SANDYBROWN;
    private Color couleurRepèreAvion = Color.WHITE;
    private Color couleurRepèreAero = Color.BLUE;
    private Color couleurPsiThetaPhi = Color.GRAY;
    private Color couleurAlphaBeta = Color.LIGHTBLUE;
    private Color couleurVitessesRotation = Color.DARKVIOLET;
    private Color couleurMoment = Color.GREEN;



    private Configuration(){
        File confFile = new File ("vifa22.conf");
        try {
            BufferedReader lecteur = new BufferedReader(new FileReader(confFile));
            String ligne;
            try {
                while ((ligne = lecteur.readLine()) != null){
                    String[] mots = ligne.split(" : ",2);
                    switch (mots[0]){
                        case "modeParDefaut":
                            switch (mots[1]){
                                case "attitude":
                                    mode = Mode.ATTITUDE;
                                    break;
                                case "aero":
                                    mode = Mode.AERO;
                                    break;
                                case "avion":
                                    mode = Mode.AVION;
                                    break;
                                default:
                                    break;
                            }
                            break;
                        case "zoomMin":
                            zoomMin = Double.parseDouble(mots[1]);
                            break;
                        case "zoomMax":
                            zoomMax = Double.parseDouble(mots[1]);
                            break;
                        case "hauteur":
                            hauteur = Double.parseDouble(mots[1]);
                            break;
                        case "launchMessage":
                            launchMessage = mots[1];
                            break;
                        case "facteurApproximationArrondis":
                            facteurApproximationArrondis =  Integer.parseInt(mots[1]);
                            break;
                        case "couleurSurfaceControle":
                            if (! mots[1].equals("DEFAULT")){
                                couleurSurfaceControle = couleurFromString(mots[1]);
                            }
                            break;
                        case "couleurFuselage" : 
                            if (! mots[1].equals("DEFAULT")){
                                couleurFuselage = couleurFromString(mots[1]);
                            }
                            break;
                        case "couleurAiles" :
                            if (! mots[1].equals("DEFAULT")){
                                couleurAiles = couleurFromString(mots[1]);
                            }
                            break;
                        case "couleurRepèreTerrestre":
                            if (! mots[1].equals("DEFAULT")){
                                couleurRepèreTerrestre = couleurFromString(mots[1]);
                            }
                            break;
                        case "couleurRepèreAvion" :
                            if (! mots[1].equals("DEFAULT")){
                                couleurRepèreAvion = couleurFromString(mots[1]);
                            }
                            break;
                        case "couleurRepèreAero":
                            if (! mots[1].equals("DEFAULT")){
                                couleurRepèreAero = couleurFromString(mots[1]);
                            }
                            break;
                        case "couleurPsiThetaPhi":
                            if (! mots[1].equals("DEFAULT")){
                                couleurPsiThetaPhi = couleurFromString(mots[1]);
                            }
                            break;
                        case "couleurAlphaBeta":
                            if (! mots[1].equals("DEFAULT")){
                                couleurAlphaBeta = couleurFromString(mots[1]);
                            }
                            break;
                        case "couleurVitessesRotation":
                            if (! mots[1].equals("DEFAULT")){
                                couleurVitessesRotation = couleurFromString(mots[1]);
                            }
                            break;
                        case "couleurMoment":
                            if (! mots[1].equals("DEFAULT")){
                                couleurMoment = couleurFromString(mots[1]);
                            }
                            break;
                        default:
                            System.out.println("Gibberish line : \n"+ligne);
                            break;
                    }
                }
            } catch (IOException e) {
                System.out.println(e+"\nFICHIER DE MERDE");
            }
        }
        catch(FileNotFoundException e) {
            System.out.println(e+"\nFICHIER DE CONFIGURATION PERDU");
        }
        
    }
    private Color couleurFromString (String chaine){
        String sansParanthese = (chaine.replace("(", "")).replace(")","");
        String[] numbers = sansParanthese.split(",");
        return new Color(Integer.parseInt(numbers[0]),Integer.parseInt(numbers[1]),Integer.parseInt(numbers[2]),1);
    }
    public static Configuration getInstance(){
        if (conf == null){
            conf = new Configuration();
        }
        return conf;
    }
    public Mode getMode() {
        return mode;
    }
    public double getZoomMax() {
        return zoomMax;
    }
    public double getZoomMin() {
        return zoomMin;
    }
    public double getHauteur(){
        return hauteur;
    }
    public String getLaunchMessage (){
        return launchMessage;
    }
    public int getFacteurApproximationArrondis(){
        return facteurApproximationArrondis;
    }
    public Color getCouleurSurfaceControle() {
        return couleurSurfaceControle;
    }
    public Color getCouleurFuselage() {
        return couleurFuselage;
    }
    public Color getCouleurAiles() {
        return couleurAiles;
    }
    public Color getCouleurRepèreTerrestre() {
        return couleurRepèreTerrestre;
    }
    public Color getCouleurRepèreAvion() {
        return couleurRepèreAvion;
    }
    public Color getCouleurRepèreAero() {
        return couleurRepèreAero;
    }
    public Color getCouleurPsiThetaPhi() {
        return couleurPsiThetaPhi;
    }
    public Color getCouleurAlphaBeta() {
        return couleurAlphaBeta;
    }
    public Color getCouleurVitessesRotation() {
        return couleurVitessesRotation;
    }
    public Color getCouleurMoment() {
        return couleurMoment;
    }
}
