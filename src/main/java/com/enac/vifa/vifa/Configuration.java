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
    private double zoomDefault = 300;
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
    private Color couleurFond = Color.BLACK;
    private Color couleurSol = new Color(95.0/255,91.0/255,53.0/255,1);
    private double vitesseRotationToDegres =2;
    private double momentToDegre = 0.000005;

    private double vectorScaling = 40000;

    private double repereScaling = 10;

    private double reducSensibilite = 1;

    /**
     * Constructeur de la classe configuration.
     * /!\ Configuration est un singleton. Utilisez getInstance pour obtenir l'objet.
     * 
     */
    private Configuration(){
        File confFile = new File ("vifa22.conf");
        try {
            BufferedReader lecteur = new BufferedReader(new FileReader(confFile));
            String ligne;
            try {
                while ((ligne = lecteur.readLine()) != null){
                    String[] mots = ligne.split(" : ",2);
                    if (ligne.charAt(0) != '#'){
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
                            case "zoomDefault":
                                zoomDefault = Double.parseDouble(mots[1]);
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
                            case "couleurSol":
                                if (! mots[1].equals("DEFAULT")){
                                    couleurSol = couleurFromString(mots[1]);
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
                            case "couleurRepereTerrestre":
                                if (! mots[1].equals("DEFAULT")){
                                    couleurRepèreTerrestre = couleurFromString(mots[1]);
                                }
                                break;
                            case "couleurRepereAvion" :
                                if (! mots[1].equals("DEFAULT")){
                                    couleurRepèreAvion = couleurFromString(mots[1]);
                                }
                                break;
                            case "couleurRepereAero":
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
                            case "couleurFond" :
                                if (! mots[1].equals("DEFAULT")){
                                    couleurFond = couleurFromString(mots[1]);
                                }
                                break;
                            case "vitesseRotationToDegres":
                                vitesseRotationToDegres = Double.parseDouble(mots[1]);
                                break;
                            case "momentToDegre":
                                momentToDegre = Double.parseDouble(mots[1]);
                                break;
                            case "vectorScaling":
                                vectorScaling = Double.parseDouble(mots[1]);
                                break;
                            case "repereScaling":
                                repereScaling = Double.parseDouble(mots[1]);
                                break;
                            case "reducSensibilite":
                                reducSensibilite = Double.parseDouble(mots[1]);
                            default:
                                System.out.println("Gibberish line : \n"+ligne);
                                break;
                        }
                    }
                }
            } 
            catch (IOException e) {
                System.out.println(e+"\nFICHIER DE MERDE");
            }
        }
        catch(FileNotFoundException e) {
            System.out.println(e+"\nFICHIER DE CONFIGURATION PERDU");
        }
        
    }

    /**
     * Convertit une chaîne de caractères au format (R,G,B) en couleur; avec R,G et B des entiers compris entre 0 et 255
     * @param chaine 
     * @return (Color) La couleur décrite par le code RGB
     */
    private Color couleurFromString (String chaine){
        String sansParanthese = (chaine.replace("(", "")).replace(")","");
        String[] numbers = sansParanthese.split(",");
        return new Color(Double.parseDouble(numbers[0])/255,Double.parseDouble(numbers[1])/255,Double.parseDouble(numbers[2])/255,1);
    }

    /**
     * Méthode permettant d'accèder à l'objet Configuration.
     */
    public static Configuration getInstance(){
        if (conf == null){
            conf = new Configuration();
        }
        return conf;
    }

    //GETTERS
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
    public Color getCouleurFond(){
        return couleurFond;
    }
    public Color getCouleurSol(){
        return couleurSol;
    }
    public double getVitesseRotationToDegres() {
        return vitesseRotationToDegres;
    }
    public double getMomentToDegre() {
        return momentToDegre;
    }
    public double getZoomDefault(){
        return zoomDefault;
    }

    public double getVectorScaling(){
        return vectorScaling;
    }

    public double getRepereScaling(){
        return repereScaling;
    }

    public double getReducSensibilite() {
        return reducSensibilite;
    }
}
