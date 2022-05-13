package com.enac.vifa.vifa;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.enac.vifa.vifa.vues.Mode;

public class Configuration {
    private static Configuration conf;
    private Mode mode;
    private double zoomMax = 1000;
    private double zoomMin = 25;
    private double hauteur = 100;



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
}
